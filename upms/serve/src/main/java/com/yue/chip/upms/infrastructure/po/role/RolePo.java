package com.yue.chip.upms.infrastructure.po.role;

import com.yue.chip.core.common.enums.State;
import com.yue.chip.core.persistence.JpaInterceptor;
import com.yue.chip.upms.definition.role.RoleDefinition;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Comment;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * @author Mr.Liu
 * @date 2023/1/12 下午2:50
 * @description RolePo
 */
@EqualsAndHashCode(callSuper=true)
@Entity
@Table(name = "t_role",indexes = {@Index(columnList = "name"),@Index(columnList = "create_date_time"), @Index(columnList = "update_date_time")})
@SuperBuilder
@NoArgsConstructor
@Data
@EntityListeners({AuditingEntityListener.class, JpaInterceptor.class})
@Comment("角色")
public class RolePo extends RoleDefinition {

    @Override
    @Convert(converter = State.StateConverter.class)
    @Column(columnDefinition = "int NULL COMMENT '状态(0:禁用,1:正常)-不能为空'")
    public State getState() {
        return super.getState();
    }

    @Override
    @Column(unique = true, columnDefinition = "varchar(255) NULL DEFAULT '' COMMENT '编码-不能为空'")
    public String getCode() {
        return super.getCode();
    }

    @Override
    @Column(name = "name", columnDefinition = "varchar(255) NULL DEFAULT '' COMMENT '名称-不能为空'")
    public String getName() {
        return super.getName();
    }

    @Override
    @Column(columnDefinition = "bit(1) NULL COMMENT '是否默认角色（0：否，1：是）默认角色不能删除-不能为空'")
    public Boolean getIsDefault() {
        return super.getIsDefault();
    }

    @Override
    @Column(columnDefinition = "varchar(255) NULL DEFAULT '' COMMENT '备注'")
    public String getRemark() {
        return super.getRemark();
    }
}
