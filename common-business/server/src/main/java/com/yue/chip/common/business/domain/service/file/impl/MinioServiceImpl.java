package com.yue.chip.common.business.domain.service.file.impl;

import com.yue.chip.common.business.domain.aggregates.file.File;
import com.yue.chip.common.business.domain.service.file.FileService;
import com.yue.chip.configuration.properties.MinioProperties;
import com.yue.chip.exception.BusinessException;
import com.yue.chip.utils.RedisUtils;
import io.minio.*;
import org.apache.commons.codec.digest.DigestUtils;
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
public class MinioServiceImpl implements FileService {

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
        String md5 = DigestUtils.md5Hex(file.getInputStream());
        InputStream inputStream = file.getInputStream();

        PutObjectArgs putObjectArgs = PutObjectArgs.builder()
                .bucket(MinioProperties.PUBLIC_BUCKET)
                .object(minioProperties.getPath() + "/" + fileStoreName)
                .stream(inputStream, file.getSize(), -1)
                .contentType(file.getContentType())
                .build();

        ObjectWriteResponse response = minioClient.putObject(putObjectArgs);
        if (Objects.nonNull(response) && StringUtils.hasText(response.etag())) {
            return Optional.of(buildFileInfo(file.getSize(), file.getOriginalFilename(), fileStoreName, md5));
        }
        return Optional.empty();
    }

    /**
     * 构建文件信息
     */
    private File buildFileInfo(long fileSize, String originalFilename, String fileStoreName, String md5) {
        return File.builder()
                .fileName(fileStoreName)
                .size(fileSize)
                .originalFileName(originalFilename)
                .url("/" + MinioProperties.PUBLIC_BUCKET + "/" + minioProperties.getPath() + "/" + fileStoreName)
                .md5(md5)
                .build();
    }

}
