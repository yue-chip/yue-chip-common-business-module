package com.yue.chip.common.business.infrastructure.po.file;

import com.yue.chip.core.persistence.entity.BaseEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
//import org.hibernate.annotations.Comment;

/**
 * @author Mr.Liu
 * @date 2023/7/12 下午4:41
 */
@EqualsAndHashCode(callSuper=true)
@Entity
@Table(name = "t_file_relational",indexes = {@Index(columnList = "table_id,file_field_name,table_name")})
@SuperBuilder
@NoArgsConstructor
//@Comment("文件与其它表中间表")
@Data
public class FileRelationalPo extends BaseEntity {

    private String tableName;

    private String fileFieldName;

    private Long tableId;

    private Long fileId;

    @Column(updatable = false,name = "table_name")
    public String getTableName() {
        return tableName;
    }
    @Column(updatable = false,name = "table_id")
    public Long getTableId() {
        return tableId;
    }
    @Column(updatable = false,name = "file_id")
    public Long getFileId() {
        return fileId;
    }
    @Column(updatable = false,name = "file_field_name")
    public String getFileFieldName() {
        return fileFieldName;
    }
}
