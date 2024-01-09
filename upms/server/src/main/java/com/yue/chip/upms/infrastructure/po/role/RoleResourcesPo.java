package com.yue.chip.upms.infrastructure.po.role;

import com.yue.chip.core.persistence.JpaInterceptor;
import com.yue.chip.core.persistence.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

/**
 * @author Mr.Liu
 * @date 2023/1/12 下午2:53
 * @description RoleResourcesPo
 */
@EqualsAndHashCode(callSuper=true)
@Entity
@Table(name = "t_role_resources",indexes = {@Index(columnList = "role_id"),@Index(columnList = "resources_id") })
@Data
@SuperBuilder
@EntityListeners({AuditingEntityListener.class, JpaInterceptor.class})
//@Comment("角色-资源关联表")
public class RoleResourcesPo extends BaseEntity {

    private Long roleId;

    private Long resourcesId;

    public RoleResourcesPo() {
    }

    @Column(name = "role_id")
    public Long getRoleId() {
        return roleId;
    }

    @Column(name = "resources_id")
    public Long getResourcesId() {
        return resourcesId;
    }

}
