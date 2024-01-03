package com.yue.chip.common.business.infrastructure.po.file;

import com.yue.chip.core.persistence.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Comment;

/**
 * @author Mr.Liu
 * @date 2023/7/12 下午4:41
 */
@EqualsAndHashCode(callSuper=true)
@Entity
@Table(name = "t_file_relational",indexes = {@Index(columnList = "tableId,fileFieldName,tableName")})
@SuperBuilder
@NoArgsConstructor
//@Comment("文件与其它表中间表")
@Data
public class FileRelationalPo extends BaseEntity {

    private String tableName;

    private String fileFieldName;

    private Long tableId;

    private Long fileId;

    @Column(updatable = false,columnDefinition = "varchar(255) NULL DEFAULT '' COMMENT '关联表的表名-不能为空'")
    public String getTableName() {
        return tableName;
    }
    @Column(updatable = false,columnDefinition = "bigint DEFAULT -9223372036854775808 COMMENT '关联表的id-不能为空'")
    public Long getTableId() {
        return tableId;
    }
    @Column(updatable = false,columnDefinition = "bigint DEFAULT -9223372036854775808 COMMENT '文件id-不能为空'")
    public Long getFileId() {
        return fileId;
    }
    @Column(updatable = false,columnDefinition = "varchar(255) NULL DEFAULT '' COMMENT '关联表的文件字段名-不能为空(如:头像,照片,合同……)被关联表中实际不存在该字段'")
    public String getFileFieldName() {
        return fileFieldName;
    }
}
