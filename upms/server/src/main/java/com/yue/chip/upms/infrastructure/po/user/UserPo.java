package com.yue.chip.upms.infrastructure.po.user;

import com.yue.chip.core.common.enums.State;
import com.yue.chip.core.persistence.JpaInterceptor;
import com.yue.chip.upms.definition.user.UserDefinition;
import com.yue.chip.upms.enums.IdCardType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * @author Mr.Liu
 * @date 2023/1/10 下午4:17
 * @description UserPo
 */
@EqualsAndHashCode(callSuper=true)
@Entity
@Table(name = UserPo.TABLE_NAME,indexes = {@Index(columnList = "name"),@Index(columnList = "create_date_time"), @Index(columnList = "update_date_time")})
@SuperBuilder
@NoArgsConstructor
@EntityListeners({AuditingEntityListener.class, JpaInterceptor.class})
@Comment("用户")
public class UserPo extends UserDefinition {

    public static final String TABLE_NAME = "t_user";

    @Override
    @Column(updatable = false)
    @NotNull
    @Comment("密码-不能为空")
    public String getPassword() {
        return super.getPassword();
    }

    @Override
    @Column(updatable = false,unique = true)
    @NotNull
    @Comment("登录帐号-不能为空")
    public String getUsername() {
        return super.getUsername();
    }

    @Override
    @ColumnDefault("''")
    @Comment("姓名-不能为空")
    public String getName() {
        return super.getName();
    }

    @Override
    @Convert(converter = State.StateConverter.class)
    @ColumnDefault("1")
    @Comment("状态(0:禁用,1:正常)-不能为空")
    public State getState() {
        return super.getState();
    }

    @Override
    @ColumnDefault("''")
    @Comment("联系电话")
    public String getPhoneNumber() {
        return super.getPhoneNumber();
    }

    @Override
    @ColumnDefault("0")
    @Comment("是否接收短信通知")
    public Boolean getIsSms() {
        return super.getIsSms();
    }

    @Override
    @ColumnDefault("0")
    @Comment("是否接收紧急呼叫")
    public Boolean getIsCall() {
        return super.getIsCall();
    }

    @Override
    public LocalDateTime getLastLoginTime() {
        return super.getLastLoginTime();
    }

    @Override
    @ColumnDefault("0")
    public boolean isAccountNonExpired() {
        return super.isAccountNonExpired();
    }

    @Override
    @ColumnDefault("0")
    public boolean isAccountNonLocked() {
        return super.isAccountNonLocked();
    }

    @Override
    @ColumnDefault("0")
    public boolean isCredentialsNonExpired() {
        return super.isCredentialsNonExpired();
    }

    @Override
    @ColumnDefault("1")
    public boolean isEnabled() {
        return super.isEnabled();
    }

    @Override
    @Comment("租户编码")
    public Long getTenantNumber() {
        return super.getTenantNumber();
    }

    @Override
    @Comment("电子邮箱")
    @ColumnDefault("''")
    public String getEmail() {
        return super.getEmail();
    }

    @Override
    @Convert(converter = IdCardType.IdCardTypeConverter.class)
    @Comment("证书类型")
    @ColumnDefault("0")
    public IdCardType getIdCardType() {
        return super.getIdCardType();
    }

    @Override
    @Comment("证书编号")
    @ColumnDefault("''")
    public String getCertificateNumber() {
        return super.getCertificateNumber();
    }

    @Override
    @Comment("身份证号码")
    @ColumnDefault("''")
    public String getIdentificationNumber() {
        return super.getIdentificationNumber();
    }
}
