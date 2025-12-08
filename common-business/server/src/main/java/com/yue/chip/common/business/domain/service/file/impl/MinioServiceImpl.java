package com.yue.chip.common.business.domain.service.file.impl;

import com.yue.chip.common.business.domain.aggregates.file.File;
import com.yue.chip.common.business.domain.service.file.FileService;
import com.yue.chip.common.business.domain.service.file.LargeFileService;
import com.yue.chip.configuration.properties.MinioProperties;
import com.yue.chip.exception.BusinessException;
import com.yue.chip.utils.RedisUtils;
import io.minio.*;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 文件上传服务实现类
 *
 * @author Mr.Liu
 * @date 2023/7/5 下午4:32
 */
@Slf4j
@Service
@ConditionalOnProperty(prefix = "file", name = "store.type", havingValue = "minio")
@ConditionalOnClass({MinioClient.class})
public class MinioServiceImpl implements FileService, LargeFileService {

    @Autowired
    private MinioClient minioClient;

    @Autowired
    private MinioProperties minioProperties;

    @Resource
    private RedisUtils redisUtils;

    // 分块文件大小：5MB
    private static final long CHUNK_SIZE = 5 * 1024 * 1024;

    // 上传任务超时时间：24小时
    private static final long UPLOAD_TIMEOUT = 24 * 60 * 60 * 1000;

    // Redis中上传状态的Key前缀
    private static final String UPLOAD_KEY = "minio:upload:id:";

    @Override
    public Optional<File> upload(MultipartFile file) throws Exception {
        String fileStoreName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        InputStream inputStream = file.getInputStream();

        PutObjectArgs putObjectArgs = PutObjectArgs.builder()
                .bucket(MinioProperties.PUBLIC_BUCKET)
                .object(minioProperties.getPath() + "/" + fileStoreName)
                .stream(inputStream, file.getSize(), -1)
                .contentType(file.getContentType())
                .build();

        ObjectWriteResponse response = minioClient.putObject(putObjectArgs);
        if (Objects.nonNull(response) && StringUtils.hasText(response.etag())) {
            return Optional.of(buildFileInfo(file.getSize(), file.getOriginalFilename(), fileStoreName));
        }
        return Optional.empty();
    }

    /**
     * 构建文件信息
     */
    private File buildFileInfo(long fileSize, String originalFilename, String fileStoreName) {
        return File.builder()
                .fileName(fileStoreName)
                .size(fileSize)
                .originalFileName(originalFilename)
                .url("/" + MinioProperties.PUBLIC_BUCKET + "/" + minioProperties.getPath() + "/" + fileStoreName)
                .build();
    }

    @Override
    public Map<String, Object> initUpload(String fileName, long fileSize) {
        // 1. 生成唯一上传ID，用于标识本次上传任务
        String uploadId = UUID.randomUUID().toString();
        // 2. 根据预设的分块大小，计算总分块数
        int totalChunks = (int) Math.ceil((double) fileSize / CHUNK_SIZE);
        // 3. 创建上传状态对象并存入Redis，设置过期时间以防僵尸任务
        Map<String, Object> statusMap = new HashMap<>();
        statusMap.put("uploadId", uploadId);
        statusMap.put("fileName", uploadId.concat("_").concat(fileName));
        statusMap.put("fileSize", fileSize);
        statusMap.put("totalChunks", totalChunks);
        redisUtils.set(UPLOAD_KEY.concat(uploadId), statusMap, UPLOAD_TIMEOUT);
        // 4. 返回上传状态对象
        return statusMap;
    }

    @Override
    public void uploadChunk(String uploadId, int chunkNumber, MultipartFile chunk) {
        // 1. 为当前分块生成在MinIO中的唯一对象名称
        // 格式如：chunks/{uploadId}/{chunkNumber}
        String objectName = chunkObjectName(uploadId, chunkNumber);

        try (InputStream inputStream = chunk.getInputStream()) {
            // 2. 调用MinIO SDK上传分块
            PutObjectArgs args = PutObjectArgs.builder()
                    .bucket(MinioProperties.PUBLIC_BUCKET)
                    .object(objectName)
                    .stream(inputStream, chunk.getSize(), -1)
                    .contentType(chunk.getContentType())
                    .build();
            minioClient.putObject(args);
        } catch (Exception e) {
            log.error("上传分块 {} 失败: {}", chunkNumber, e.getMessage());
            throw new BusinessException("上传分块失败", e); // 抛出自定义异常，由全局异常处理器处理
        }

        // 3. 上传成功后，在Redis中标记该分块已完成
        markChunkUploaded(uploadId, chunkNumber);
    }

    @Override
    public File mergeFile(String uploadId) {
        // 1. 从Redis获取上传状态，并检查所有分块是否已上传完成
        Map<String, Object> uploadStatusMap = getUploadStatus(uploadId);
        if (!(boolean) uploadStatusMap.get("isComplete")) {
            throw new BusinessException("上传任务未完成");
        }

        // 2. (业务逻辑)准备文件记录
        String originalFilename = (String) uploadStatusMap.get("fileName");
        Integer totalChunks = (Integer) uploadStatusMap.get("totalChunks");
        Long fileSize = (Long) uploadStatusMap.get("fileSize");

        try {
            String finalObjectName = minioProperties.getPath() + "/" + originalFilename; // 生成最终在MinIO中存储的文件名

            // 3. 核心：合并分块
            // 构建一个源分块列表
            List<ComposeSource> sources = IntStream.range(0, totalChunks)
                    .mapToObj(i -> ComposeSource.builder()
                            .bucket(MinioProperties.PUBLIC_BUCKET)
                            .object(chunkObjectName(uploadId, i)) // 指向每个分块
                            .build())
                    .collect(Collectors.toList());

            // 调用MinIO的composeObject API合并文件
            // 此操作在MinIO服务端进行，高效且不耗费应用服务器资源
            ObjectWriteResponse response = minioClient.composeObject(
                    ComposeObjectArgs.builder()
                            .bucket(MinioProperties.PUBLIC_BUCKET)
                            .object(finalObjectName)
                            .sources(sources)
                            .build()
            );

            // 4. 合并成功后，清理临时分块文件
            for (int i = 0; i < totalChunks; i++) {
                minioClient.removeObject(
                        RemoveObjectArgs.builder()
                                .bucket(MinioProperties.PUBLIC_BUCKET)
                                .object(chunkObjectName(uploadId, i))
                                .build()
                );
            }

            if (Objects.nonNull(response) && StringUtils.hasText(response.etag())) {
                return buildFileInfo(fileSize, originalFilename, finalObjectName);
            }

        } catch (Exception e) {
            log.error("文件合并失败：{}", e.getMessage());
            throw new BusinessException("文件合并失败");
        } finally {
            // 5. 清理Redis状态
            String uploadKey = UPLOAD_KEY.concat(uploadId);
            redisUtils.del(uploadKey);
            redisUtils.del(uploadKey.concat(":chunks"));
        }
        return null;
    }

    @Override
    public Map<String, Object> getUploadStatus(String uploadId) {
        // 从Redis获取基础状态
        String uploadIdKey = UPLOAD_KEY.concat(uploadId);
        Object statusObj = redisUtils.get(uploadIdKey);
        if (Objects.isNull(statusObj)) {
            throw new BusinessException("上传任务不存在");
        }
        Map<String, Object> statusMap = (Map<String, Object>) statusObj;
        // 从Redis Set中获取已上传的分块编号，并设置到状态对象中
        Set<Object> uploadedChunks = redisUtils.sGet(uploadIdKey + ":chunks");
        boolean isComplete = false;
        Set<Integer> uploadedChunksNum = null;
        if (Objects.nonNull(uploadedChunks)) {
            uploadedChunksNum = uploadedChunks.stream()
                    .map(o -> Integer.parseInt(o.toString())) // 注意类型转换
                    .collect(Collectors.toSet());
            Integer totalChunks = (Integer) statusMap.get("totalChunks");
            isComplete = Objects.equals(totalChunks, uploadedChunksNum.size());
        }
        statusMap.put("uploadedChunks", uploadedChunksNum);
        statusMap.put("isComplete", isComplete);
        return statusMap;
    }

    /**
     * 为当前分块生成在MinIO中的唯一对象名称
     *
     * @param uploadId    上传任务ID
     * @param chunkNumber 分块编号
     */
    private String chunkObjectName(String uploadId, int chunkNumber) {
        return "chunks/" + uploadId + "/" + chunkNumber;
    }

    /**
     * 在Redis中标记该分块已完成
     *
     * @param uploadId    上传任务ID
     * @param chunkNumber 分块编号
     */
    private void markChunkUploaded(String uploadId, int chunkNumber) {
        // 使用Set结构存储已上传的分块编号
        redisUtils.sSet(UPLOAD_KEY.concat(uploadId).concat(":chunks"), chunkNumber);
    }
}
