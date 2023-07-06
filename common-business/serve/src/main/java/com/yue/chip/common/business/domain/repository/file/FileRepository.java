package com.yue.chip.common.business.domain.repository.file;

import com.yue.chip.common.business.domain.aggregates.file.File;
import com.yue.chip.common.business.infrastructure.po.file.FilePo;
import jakarta.validation.constraints.NotNull;

import java.util.Optional;

/**
 * @author Mr.Liu
 * @date 2023/6/13 下午4:33
 */
public interface FileRepository {

    /**
     *
     * @param file
     * @return
     */
    public File add(@NotNull FilePo file);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    public Optional<File> find(@NotNull Long id);
}
