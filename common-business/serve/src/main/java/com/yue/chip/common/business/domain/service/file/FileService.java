package com.yue.chip.common.business.domain.service.file;

import com.yue.chip.common.business.domain.aggregates.file.File;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

/**
 * @author Mr.Liu
 * @date 2023/6/13 下午4:51
 */
public interface FileService {

    public static final String URL_PREFIX = "/file";

    /**
     * @param file
     * @return
     * @throws Exception
     */
    public Optional<File> upload(MultipartFile file) throws Exception;
}
