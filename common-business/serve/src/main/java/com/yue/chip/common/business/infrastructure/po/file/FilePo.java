package com.yue.chip.common.business.infrastructure.po.file;

import com.yue.chip.common.business.definition.file.FileDefinition;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author Mr.Liu
 * @date 2023/6/13 下午4:27
 */
@EqualsAndHashCode(callSuper=true)
@Entity
@Table(name = "t_file")
@SuperBuilder
@NoArgsConstructor
public class FilePo extends FileDefinition {

    @Override
    public String getOriginalFileName() {
        return super.getOriginalFileName();
    }

    @Override
    public Long getSize() {
        return super.getSize();
    }

    @Override
    public String getFileName() {
        return super.getFileName();
    }

    @Override
    public String getUrl() {
        return super.getUrl();
    }
}
