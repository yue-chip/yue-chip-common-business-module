package com.yue.chip.common.business.infrastructure.po.enmus;

import com.yue.chip.common.business.definition.enums.EnumUtilDefinition;
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
 * @date 2023/7/6 上午11:18
 */
@EqualsAndHashCode(callSuper=true)
@Entity
@Table(name = "T_ENUM_UTIL",indexes = {@Index(columnList = "CODE,VERSION",unique=true)})
@SuperBuilder
@NoArgsConstructor
//@Comment("保存所有微服务的枚举值，用于前端生成select下拉框")
public class EnumUtilPo extends EnumUtilDefinition {

    @Override
    @Column( name = "CODE")
    public String getCode() {
        return super.getCode();
    }

    @Override
    @Column(name = "VERSION")
    public String getVersion() {
        return super.getVersion();
    }

    @Override
    @Column(length = 1000,name="VALUE")
    public String getValue() {
        return super.getValue();
    }
}
