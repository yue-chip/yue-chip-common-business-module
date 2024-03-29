package com.yue.chip.upms.infrastructure.po.organizational;

import com.yue.chip.core.persistence.JpaInterceptor;
import com.yue.chip.upms.definition.organizational.OrganizationalGroupDefinition;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Comment;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * @author Mr.Liu
 * @description: 组织机构分组
 * @date 2023/10/7 下午2:24
 */
@EqualsAndHashCode(callSuper=true)
@Entity
@Table(name = "t_organizational_group",indexes = {@Index(columnList = "name")})
@SuperBuilder
@EntityListeners({AuditingEntityListener.class, JpaInterceptor.class})
@NoArgsConstructor
@Data
@Comment("组织机构分组")
public class OrganizationalGroupPo extends OrganizationalGroupDefinition {

    @Override
    @Column(unique = true,name = "name",columnDefinition = "varchar(255) NULL DEFAULT '' COMMENT '机构分组名称-不能为空'")
    public String getName() {
        return super.getName();
    }
}
