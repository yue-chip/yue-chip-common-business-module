package com.yue.chip.common.business.infrastructure.po.file;

import com.yue.chip.core.persistence.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
//import org.hibernate.annotations.Comment;

/**
 * @author Mr.Liu
 * @date 2023/7/12 下午4:41
 */
@EqualsAndHashCode(callSuper=true)
@Entity
@Table(name = "T_FILE_RELATIONAL",indexes = {@Index(columnList = "TABLE_ID,FILE_FIELD_NAME,TABLE_NAME")})
@SuperBuilder
@NoArgsConstructor
//@Comment("文件与其它表中间表")
@Data
public class FileRelationalPo extends BaseEntity {

    private String tableName;

    private String fileFieldName;

    private Long tableId;

    private Long fileId;

    @Column(updatable = false,name = "TABLE_NAME")
    public String getTableName() {
        return tableName;
    }
    @Column(updatable = false,name = "TABLE_ID")
    public Long getTableId() {
        return tableId;
    }
    @Column(updatable = false,name = "FILE_ID")
    public Long getFileId() {
        return fileId;
    }
    @Column(updatable = false,name = "FILE_FIELD_NAME")
    public String getFileFieldName() {
        return fileFieldName;
    }
}
