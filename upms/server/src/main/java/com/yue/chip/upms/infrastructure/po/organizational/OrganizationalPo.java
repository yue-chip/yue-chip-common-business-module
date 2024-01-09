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
@Table(name = "t_organizational", indexes = {@Index(columnList = "name"),@Index(columnList = "parent_id"),@Index(columnList = "organizational_group_id")})
@SuperBuilder
@EntityListeners({AuditingEntityListener.class, JpaInterceptor.class})
@NoArgsConstructor
@Data
//@Comment("组织机构")
public class OrganizationalPo extends OrganizationalDefinition {

    @Override
    @Column(unique = true,name = "name")
    public String getName() {
        return super.getName();
    }

    @Override
    @Column(name = "parent_id")
    public Long getParentId() {
        return super.getParentId();
    }

    @Override
    @Column(name = "organizational_group_id")
    public Long getOrganizationalGroupId() {
        return super.getOrganizationalGroupId();
    }

    @Override
    @Column(name = "sort")
    public Integer getSort() {
        return super.getSort();
    }

    @Override
    @Column( name = "leader_id")
    public Long getLeaderId() {
        return super.getLeaderId();
    }

    @Override
    @Convert(converter = State.StateConverter.class)
    @Column(name = "state")
    public State getState() {
        return super.getState();
    }

    @Override
    @Column( name = "phone_number")
    public String getPhoneNumber() {
        return super.getPhoneNumber();
    }
}
