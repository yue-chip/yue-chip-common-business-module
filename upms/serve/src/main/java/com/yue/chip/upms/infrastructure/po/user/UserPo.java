package com.yue.chip.upms.infrastructure.po.user;

import com.yue.chip.core.persistence.JpaInterceptor;
import com.yue.chip.upms.definition.user.UserDefinition;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;

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
    @Column( columnDefinition = "varchar(255) NULL DEFAULT '' COMMENT '密码'")
    public String getPassword() {
        return super.getPassword();
    }

    @Override
    @Column(unique = true, columnDefinition = "varchar(255) NULL DEFAULT '' COMMENT '登录帐号'")
    public String getUsername() {
        return super.getUsername();
    }

    @Override
    @Column(name = "name", columnDefinition = "varchar(255) NULL DEFAULT '' COMMENT '姓名'")
    public String getName() {
        return super.getName();
    }

    @Override
    @Column(columnDefinition = "datetime NULL COMMENT '生日'")
    public LocalDate getBirthday() {
        return super.getBirthday();
    }

    @Override
    @Column(columnDefinition = "bit(1) NULL DEFAULT 0 COMMENT ''")
    public boolean isAccountNonExpired() {
        return super.isAccountNonExpired();
    }

    @Override
    @Column(columnDefinition = "bit(1) NULL DEFAULT 0 COMMENT ''")
    public boolean isAccountNonLocked() {
        return super.isAccountNonLocked();
    }

    @Override
    @Column(columnDefinition = "bit(1) NULL DEFAULT 0 COMMENT ''")
    public boolean isCredentialsNonExpired() {
        return super.isCredentialsNonExpired();
    }

    @Override
    @Column(columnDefinition = "bit(1) NULL DEFAULT 1 COMMENT ''")
    public boolean isEnabled() {
        return super.isEnabled();
    }
}
