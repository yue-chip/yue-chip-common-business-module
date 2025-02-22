package com.yue.chip.common.business.domain.service.file.impl;

import com.yue.chip.common.business.domain.aggregates.file.File;
import com.yue.chip.common.business.domain.service.file.FileService;
import com.yue.chip.configuration.properties.MinioProperties;
import io.minio.MinioClient;
import io.minio.ObjectWriteResponse;
import io.minio.PutObjectArgs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * @author Mr.Liu
 * @date 2023/7/5 下午4:32
 */
@Service
@ConditionalOnProperty(prefix = "file",name = "store.type",havingValue = "minio")
@ConditionalOnClass({MinioClient.class})
public class MinioServiceImpl implements FileService {
    @Autowired
    private MinioClient minioClient;

    @Autowired
    private MinioProperties minioProperties;

    @Override
    public Optional<File> upload(MultipartFile file) throws Exception{
        String fileStoreName = UUID.randomUUID().toString()+file.getOriginalFilename();
        InputStream inputStream = file.getInputStream();
        PutObjectArgs putObjectArgs = PutObjectArgs.builder().bucket(MinioProperties.PUBLIC_BUCKET).object(minioProperties.getPath()+"/"+fileStoreName).stream(
                        inputStream, inputStream.available(), -1)
                .build();
        ObjectWriteResponse objectWriteResponse = minioClient.putObject(putObjectArgs);
        if (Objects.nonNull(objectWriteResponse) && StringUtils.hasText(objectWriteResponse.etag())) {
            return Optional.ofNullable(
                    File.builder()
                    .fileName(fileStoreName)
                    .size(file.getSize())
                    .originalFileName(file.getOriginalFilename())
                    .url("/" + MinioProperties.PUBLIC_BUCKET + "/" + minioProperties.getPath() + "/" + fileStoreName)
                    .build()
            );
        }
        return Optional.empty();
    }
}
