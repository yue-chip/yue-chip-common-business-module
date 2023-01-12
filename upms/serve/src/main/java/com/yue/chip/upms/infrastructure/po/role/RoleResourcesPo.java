package com.yue.chip.upms.infrastructure.po.role;

import com.yue.chip.core.persistence.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
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
@Table(name = "t_role_resources",indexes = {@Index(columnList = "role_id"),@Index(columnList = "resources_id") })
@DynamicInsert
@DynamicUpdate
@SelectBeforeUpdate
@Data
@SuperBuilder
public class RoleResourcesPo extends BaseEntity {

    /**
     * 角色id
     */
    @Column(name = "role_id",nullable = false)
    private Long roleId;

    /**
     * 资源id
     */
    @Column(name = "resources_id",nullable = false)
    private Long resourcesId;

    public RoleResourcesPo() {

    }
}
