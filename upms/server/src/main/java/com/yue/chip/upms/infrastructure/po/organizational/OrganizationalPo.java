package com.yue.chip.upms.infrastructure.po.organizational;

import com.yue.chip.core.common.enums.State;
import com.yue.chip.core.persistence.JpaInterceptor;
import com.yue.chip.upms.definition.organizational.OrganizationalDefinition;
import javax.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
//import org.hibernate.annotations.Comment;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

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
    //@Column(name = "sort", columnDefinition = "int NULL DEFAULT 0 COMMENT '排序'")
    public Integer getSort() {
        return super.getSort();
    }

    @Override
    //@Column( columnDefinition = "bigint NULL DEFAULT 0 COMMENT '负责人id'")
    public Long getLeaderId() {
        return super.getLeaderId();
    }

    @Override
    @Convert(converter = State.StateConverter.class)
    //@Column(columnDefinition = "int NULL DEFAULT 1 COMMENT '状态(0:禁用,1:正常)-不能为空'")
    public State getState() {
        return super.getState();
    }

    @Override
    //@Column( columnDefinition = "varchar(255) NULL DEFAULT '' COMMENT '紧急联系电话'")
    public String getPhoneNumber() {
        return super.getPhoneNumber();
    }
}
