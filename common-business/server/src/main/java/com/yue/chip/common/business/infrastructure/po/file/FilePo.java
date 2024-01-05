package com.yue.chip.common.business.infrastructure.po.file;

import com.yue.chip.common.business.definition.file.FileDefinition;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
//import org.hibernate.annotations.Comment;

/**
 * @author Mr.Liu
 * @date 2023/6/13 下午4:27
 */
@EqualsAndHashCode(callSuper=true)
@Entity
@Table(name = "t_file")
@SuperBuilder
@NoArgsConstructor
//@Comment("文件")
public class FilePo extends FileDefinition {

    @Override
    //@Column(columnDefinition = "varchar(255) NULL DEFAULT '' COMMENT '原始文件名'")
    public String getOriginalFileName() {
        return super.getOriginalFileName();
    }

    @Override
    //@Column(columnDefinition = "bigint DEFAULT 0 COMMENT '文件大小'")
    public Long getSize() {
        return super.getSize();
    }

    @Override
    //@Column(columnDefinition = "varchar(255) NULL DEFAULT '' COMMENT '文件存储后的文件名'")
    public String getFileName() {
        return super.getFileName();
    }

    @Override
    //@Column(columnDefinition = "varchar(500) NULL DEFAULT '' COMMENT '文件存储后/访问路径'")
    public String getUrl() {
        return super.getUrl();
    }
}
