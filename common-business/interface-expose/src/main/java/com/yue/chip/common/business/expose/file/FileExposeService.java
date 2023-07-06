package com.yue.chip.common.business.expose.file;

import com.yue.chip.common.business.definition.file.FileDefinition;
import jakarta.validation.constraints.NotNull;

import java.util.Optional;

/**
 * @author Mr.Liu
 * @date 2023/7/6 下午3:32
 */
public interface FileExposeService {

    /**
     * 根据file id 获取文件
     * @param fileId
     * @return
     */
    public Optional<FileDefinition> find(@NotNull Long fileId);

    /**
     * 根据file id 获取文件URL
     * @param fileId
     * @return
     */
    public String getUrl(@NotNull Long fileId);
}
