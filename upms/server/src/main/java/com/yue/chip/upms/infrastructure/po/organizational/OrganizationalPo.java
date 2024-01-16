package com.yue.chip.upms.infrastructure.po.organizational;

import com.yue.chip.core.common.enums.State;
import com.yue.chip.core.persistence.JpaInterceptor;
import com.yue.chip.upms.definition.organizational.OrganizationalDefinition;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

/**
 * @author Mr.Liu
 * @description: TODO
 * @date 2023/10/7 下午1:49
 */
@EqualsAndHashCode(callSuper=true)
@Entity
@Table(name = "T_ORGANIZATIONAL", indexes = {@Index(columnList = "NAME"),@Index(columnList = "PARENT_ID"),@Index(columnList = "ORGANIZATIONAL_GROUP_ID")})
@SuperBuilder
@EntityListeners({AuditingEntityListener.class, JpaInterceptor.class})
@NoArgsConstructor
@Data
//@Comment("组织机构")
public class OrganizationalPo extends OrganizationalDefinition {

    @Override
    @Column(unique = true,name = "NAME")
    public String getName() {
        return super.getName();
    }

    @Override
    @Column(name = "PARENT_ID")
    public Long getParentId() {
        return super.getParentId();
    }

    @Override
    @Column(name = "ORGANIZATIONAL_GROUP_ID")
    public Long getOrganizationalGroupId() {
        return super.getOrganizationalGroupId();
    }

    @Override
    @Column(name = "SORT")
    public Integer getSort() {
        return super.getSort();
    }

    @Override
    @Column( name = "LEADER_ID")
    public Long getLeaderId() {
        return super.getLeaderId();
    }

    @Override
    @Convert(converter = State.StateConverter.class)
    @Column(name = "STATE")
    public State getState() {
        return super.getState();
    }

    @Override
    @Column( name = "PHONE_NUMBER")
    public String getPhoneNumber() {
        return super.getPhoneNumber();
    }
}
