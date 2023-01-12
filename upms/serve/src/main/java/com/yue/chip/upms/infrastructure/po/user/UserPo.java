package com.yue.chip.upms.infrastructure.po.user;

import com.yue.chip.upms.definition.user.UserDefinition;
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
 * @date 2023/1/10 下午4:17
 * @description UserPo
 */
@EqualsAndHashCode(callSuper=true)
@Entity
@Table(name = "t_user",indexes = {@Index(columnList = "name"),@Index(columnList = "username")})
@DynamicInsert
@DynamicUpdate
@SelectBeforeUpdate
@Data
@SuperBuilder
public class UserPo extends UserDefinition {
    @Override
    @Column(nullable = false)
    public String getPassword() {
        return super.getPassword();
    }

    @Override
    @Column(name = "username",nullable = false,unique = true)
    public String getUsername() {
        return super.getUsername();
    }

    @Override
    @Column(name = "name", nullable = false)
    public String getName() {
        return super.getName();
    }

    public UserPo() {
    }
}
