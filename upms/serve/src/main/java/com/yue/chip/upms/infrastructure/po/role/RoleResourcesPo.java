package com.yue.chip.upms.infrastructure.po.role;

import com.yue.chip.core.persistence.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;

/**
 * @author Mr.Liu
 * @date 2023/1/12 下午2:53
 * @description RoleResourcesPo
 */
@EqualsAndHashCode(callSuper=true)
@Entity
@Table(name = "t_role_resources",indexes = {@Index(columnList = "roleId"),@Index(columnList = "resourcesId") })
@DynamicInsert
@DynamicUpdate
@SelectBeforeUpdate
@Data
@SuperBuilder
public class RoleResourcesPo extends BaseEntity {

    /**
     * 角色id
     */
    private Long roleId;

    /**
     * 资源id
     */
    private Long resourcesId;

    public RoleResourcesPo() {
    }

    @Column( nullable = false)
    public Long getRoleId() {
        return roleId;
    }

    @Column(nullable = false)
    public Long getResourcesId() {
        return resourcesId;
    }

}
