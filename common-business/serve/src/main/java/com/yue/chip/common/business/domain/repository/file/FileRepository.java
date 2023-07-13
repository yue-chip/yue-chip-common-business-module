package com.yue.chip.common.business.domain.repository.file;

import com.yue.chip.common.business.domain.aggregates.file.File;
import com.yue.chip.common.business.infrastructure.po.file.FilePo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;
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

    /**
     * 根据表名和id查询关联的文件
     *
     * @param tableId
     * @param fileFieldName
     * @param tableName
     * @return
     */
    public List<File> find(@NotNull Long tableId,@NotBlank String fileFieldName,@NotBlank String tableName);

    /**
     * 保存表与文件的关联关系
     *
     * @param tableId
     * @param fileFieldName
     * @param tableName
     * @param fileIds
     */
    public void save(@NotNull Long tableId,@NotBlank String fileFieldName,@NotBlank String tableName, @NotNull @Size(min = 1) List<Long> fileIds);


}
