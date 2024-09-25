package com.yue.chip.upms.infrastructure.po.resources;

import com.yue.chip.core.common.enums.State;
import com.yue.chip.core.persistence.JpaInterceptor;
import com.yue.chip.upms.definition.resources.ResourcesDefinition;
import com.yue.chip.upms.enums.Scope;
import com.yue.chip.upms.enums.Type;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

/**
 * @author Mr.Liu
 * @date 2023/1/12 下午2:51
 * @description ResourcesPo
 */
@EqualsAndHashCode(callSuper=true)
@Entity
@Table(name = "T_RESOURCES", indexes = {@Index(columnList = "PARENT_ID"),@Index(columnList = "NAME"),@Index(columnList = "CREATE_DATE_TIME"), @Index(columnList = "UPDATE_DATE_TIME")})
@SuperBuilder
@EntityListeners({AuditingEntityListener.class, JpaInterceptor.class})
@NoArgsConstructor
@Data
//@Comment("菜单资源")
public class ResourcesPo extends ResourcesDefinition {

    @Override
    @Column(name = "PARENT_ID")
    public Long getParentId() {
        return super.getParentId();
    }

    @Override
    @Column(unique = true,name = "CODE")
    public String getCode() {
        return super.getCode();
    }

    @Override
    @Column(name = "NAME")
    public String getName() {
        return super.getName();
    }

    @Override
    @Convert(converter = Scope.ScopeConverter.class)
    @Column(name = "SCOPE")
    public Scope getScope() {
        return super.getScope();
    }

    @Override
    @Convert(converter = Type.TypeConverter.class)
    @Column(name = "TYPE")
    public Type getType() {
        return super.getType();
    }

    @Override
    @Convert(converter = State.StateConverter.class)
    @Column(name = "STATE")
    public State getState() {
        return super.getState();
    }

    @Override
    @Column(name = "IS_DEFAULT")
    public Boolean getIsDefault() {
        return super.getIsDefault();
    }

    @Override
    @Column(name = "URL")
    public String getUrl() {
        return super.getUrl();
    }

    @Override
    @Column(name = "SORT")
    public Integer getSort() {
        return super.getSort();
    }

    @Override
    @Column(name = "REMARK")
    public String getRemark() {
        return super.getRemark();
    }


    @Override
    @Column(name = "URL_HMAC",length = 500)
    public String getUrlHmac() {
        return super.getUrlHmac();
    }

    @Override
    @Column(name = "NAME_HMAC",length = 500)
    public String getNameHmac() {
        return super.getNameHmac();
    }

    @Override
    @Column(name = "CODE_HMAC",length = 500)
    public String getCodeHmac() {
        return super.getCodeHmac();
    }
}
