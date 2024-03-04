package com.yue.chip.upms.infrastructure.po.resources;

import com.yue.chip.core.common.enums.State;
import com.yue.chip.core.persistence.JpaInterceptor;
import com.yue.chip.upms.definition.resources.ResourcesDefinition;
import com.yue.chip.upms.enums.Scope;
import com.yue.chip.upms.enums.Type;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import static com.yue.chip.upms.infrastructure.po.resources.ResourcesPo.TABLE_NAME;

/**
 * @author Mr.Liu
 * @date 2023/1/12 下午2:51
 * @description ResourcesPo
 */
@EqualsAndHashCode(callSuper=true)
@Entity
@Table(name = TABLE_NAME, indexes = {@Index(columnList = "parent_id"),@Index(columnList = "name"),@Index(columnList = "create_date_time"), @Index(columnList = "update_date_time")})
@SuperBuilder
@EntityListeners({AuditingEntityListener.class, JpaInterceptor.class})
@NoArgsConstructor
@Comment("菜单资源")
public class ResourcesPo extends ResourcesDefinition {

    public static final String TABLE_NAME = "t_resources";

    @Override
    @Comment("父节点id")
    @ColumnDefault("-9223372036854775808")
    public Long getParentId() {
        return super.getParentId();
    }

    @Override
    @Column(unique = true)
    @Comment("编码-不能为空")
    @ColumnDefault("''")
    public String getCode() {
        return super.getCode();
    }

    @Override
    @Comment("名称-不能为空")
    @ColumnDefault("''")
    public String getName() {
        return super.getName();
    }

    @Override
    @Convert(converter = Scope.ScopeConverter.class)
    @Comment("作用域(0:app,1:后台,2:前端,3:微信)-不能为空")
    @ColumnDefault("1")
    public Scope getScope() {
        return super.getScope();
    }

    @Override
    @Convert(converter = Type.TypeConverter.class)
    @Comment("类型(0:目录,1:菜单,2:功能)-不能为空")
    @ColumnDefault("0")
    public Type getType() {
        return super.getType();
    }

    @Override
    @Convert(converter = State.StateConverter.class)
    @Comment("状态(0:禁用,1:正常)-不能为空")
    @ColumnDefault("1")
    public State getState() {
        return super.getState();
    }

    @Override
    @Comment("是否默认菜单资源,默认资源不能删除-不能为空")
    public Boolean getIsDefault() {
        return super.getIsDefault();
    }

    @Override
    @Comment("url")
    @ColumnDefault("''")
    public String getUrl() {
        return super.getUrl();
    }

    @Override
    @Comment("排序")
    @ColumnDefault("0")
    public Integer getSort() {
        return super.getSort();
    }

    @Override
    @Comment("备注")
    @ColumnDefault("''")
    public String getRemark() {
        return super.getRemark();
    }
}
