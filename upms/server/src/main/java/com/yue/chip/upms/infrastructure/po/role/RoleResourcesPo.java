package com.yue.chip.upms.infrastructure.po.role;

import com.yue.chip.core.persistence.JpaInterceptor;
import com.yue.chip.core.persistence.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * @author Mr.Liu
 * @date 2023/1/12 下午2:53
 * @description RoleResourcesPo
 */
@EqualsAndHashCode(callSuper=true)
@Entity
@Table(name = "t_role_resources",indexes = {@Index(columnList = "role_id"),@Index(columnList = "resources_id") })
@SuperBuilder
@EntityListeners({AuditingEntityListener.class, JpaInterceptor.class})
@Comment("角色-资源关联表")
public class RoleResourcesPo extends BaseEntity {

    @Setter
    private Long roleId;

    @Setter
    private Long resourcesId;

    public RoleResourcesPo() {
    }

    @Comment("角色id-不能为空")
    @ColumnDefault("-9223372036854775808")
    public Long getRoleId() {
        return roleId;
    }

    @Comment("菜单资源id-不能为空")
    @ColumnDefault("-9223372036854775808")
    public Long getResourcesId() {
        return resourcesId;
    }

}
