package com.yue.chip.common.business.infrastructure.dao.file;

import com.yue.chip.common.business.infrastructure.po.file.FileRelationalPo;
import com.yue.chip.core.persistence.curd.BaseDao;
import jakarta.validation.constraints.NotBlank;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.stylesheets.LinkStyle;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author Mr.Liu
 * @date 2023/7/12 下午4:44
 */
public interface FileRelationalDao extends BaseDao<FileRelationalPo> {

    /**
     * 删除关联文件
     * @param tableId
     * @param tableName
     * @param fileFieldName
     * @return
     */
    @Transactional(rollbackFor = {Exception.class})
    public int deleteByTableIdAndTableNameAndFileFieldName(@NotNull Long tableId,@NotBlank String tableName,@NotBlank String fileFieldName);

}
