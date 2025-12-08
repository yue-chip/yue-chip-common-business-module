package com.yue.chip.common.business.domain.service.file;

import com.yue.chip.common.business.domain.aggregates.file.File;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

/**
 * 文件上传服务
 *
 * @author Mr.Liu
 * @date 2023/6/13 下午4:51
 */
public interface FileService {

    String URL_PREFIX = "/file";

    /**
     * @param file 文件上传
     * @return 文件信息
     * @throws Exception 抛出异常
     */
    Optional<File> upload(MultipartFile file) throws Exception;
}
