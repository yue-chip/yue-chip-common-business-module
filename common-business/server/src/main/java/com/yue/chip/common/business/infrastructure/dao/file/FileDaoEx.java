package com.yue.chip.common.business.infrastructure.dao.file;

import com.yue.chip.common.business.infrastructure.po.file.FilePo;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import java.util.List;

/**
 * @author Mr.Liu
 * @date 2023/7/12 下午4:50
 */
public interface FileDaoEx {

    /**
     * 根据表名和id查询关联的文件
     *
     * @param tableId
     * @param fileFieldName
     * @param tableName
     * @return
     */
    public List<FilePo> find(@NotNull Long tableId,@NotBlank String fileFieldName, @NotBlank String tableName);
}
