package com.yue.chip.upms.infrastructure.po.role;

import com.yue.chip.core.persistence.JpaInterceptor;
import com.yue.chip.core.persistence.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * @author Mr.Liu
 * @date 2023/1/12 下午2:53
 * @description RoleResourcesPo
 */
@EqualsAndHashCode(callSuper=true)
@Entity
@Table(name = "t_role_resources",indexes = {@Index(columnList = "roleId"),@Index(columnList = "resourcesId") })
@Data
@SuperBuilder
@EntityListeners({AuditingEntityListener.class, JpaInterceptor.class})
@Comment("角色-资源关联表")
public class RoleResourcesPo extends BaseEntity {

    @Column( columnDefinition = "bigint NULL DEFAULT -9223372036854775808 COMMENT '角色id'")
    private Long roleId;

    @Column( columnDefinition = "bigint NULL DEFAULT -9223372036854775808 COMMENT '菜单资源id'")
    private Long resourcesId;

    public RoleResourcesPo() {
    }

    public Long getRoleId() {
        return roleId;
    }

    public Long getResourcesId() {
        return resourcesId;
    }

}
