package com.yue.chip.common.business.infrastructure.po.enmus;

import com.yue.chip.common.business.definition.enums.EnumUtilDefinition;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Comment;

/**
 * @author Mr.Liu
 * @date 2023/7/6 上午11:18
 */
@EqualsAndHashCode(callSuper=true)
@Entity
@Table(name = "t_enum_util",indexes = {@Index(columnList = "code"),@Index(columnList = "version")})
@SuperBuilder
@NoArgsConstructor
@Comment("保存所有微服务的枚举值，用于前端生成select下拉框")
public class EnumUtilPo extends EnumUtilDefinition {

    @Override
    @Column(name = "code",columnDefinition = "varchar(255) NULL DEFAULT '' COMMENT '编码'")
    public String getCode() {
        return super.getCode();
    }

    @Override
    @Column(name = "version",columnDefinition = "varchar(255) NULL DEFAULT '' COMMENT '版本号'")
    public String getVersion() {
        return super.getVersion();
    }

    @Override
    @Column(name = "value",columnDefinition = "varchar(1000) NULL DEFAULT '' COMMENT '枚举值'")
    public String getValue() {
        return super.getValue();
    }
}
