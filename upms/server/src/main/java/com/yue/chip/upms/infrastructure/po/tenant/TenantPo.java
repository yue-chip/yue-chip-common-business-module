package com.yue.chip.upms.infrastructure.po.tenant;

import com.yue.chip.core.common.enums.State;
import com.yue.chip.core.persistence.JpaInterceptor;
import com.yue.chip.upms.definition.tenant.TenantDefinition;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * @author Mr.Liu
 * @description: TODO
 * @date 2023/10/23 下午1:46
 */
@EqualsAndHashCode(callSuper=true)
@Entity
@Table(name = "t_tenant",indexes = {@Index(columnList = "manager"),@Index(columnList = "phoneNumber") })
@SuperBuilder
@EntityListeners({AuditingEntityListener.class, JpaInterceptor.class})
@Comment("租户表")
@NoArgsConstructor
public class TenantPo extends TenantDefinition {
    @Override
    @Column(unique = true)
    @Comment("租户名称-不能为空")
    @ColumnDefault("''")
    public String getName() {
        return super.getName();
    }

    @Override
    @Convert(converter = State.StateConverter.class)
    @Comment("状态(0:禁用,1:正常)-不能为空")
    @ColumnDefault("1")
    public State getState() {
        return super.getState();
    }

    @Override
    @Comment("负责人-不能为空")
    @ColumnDefault("''")
    public String getManager() {
        return super.getManager();
    }

    @Override
    @Comment("负责人联系电话-不能为空")
    @ColumnDefault("''")
    public String getPhoneNumber() {
        return super.getPhoneNumber();
    }

    @Override
    @Comment("访问地址(xxx.xxx.com,120.102.25.45)-用户区分租户")
    @ColumnDefault("''")
    public String getDomain() {
        return super.getDomain();
    }

    @Override
    @Comment("租户名称-简称-不能为空")
    @ColumnDefault("''")
    public String getAbbreviation() {
        return super.getAbbreviation();
    }

    @Override
    @Column(updatable = false)
    @Comment("是否默认租户，默认租户不能删除(系统创建，不能手动添加)")
    @ColumnDefault("0")
    public Boolean getIsDefault() {
        return super.getIsDefault();
    }

    @Override
    @Column(unique = true,updatable = false)
    @Comment("租户编码")
    public Long getTenantNumber() {
        return super.getTenantNumber();
    }

    @Override
    @Comment("数字大屏名称-不能为空")
    @ColumnDefault("0")
    public String getBigScreenName() {
        return super.getBigScreenName();
    }
}
