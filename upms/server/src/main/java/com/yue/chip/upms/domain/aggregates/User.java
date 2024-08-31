package com.yue.chip.upms.domain.aggregates;

import com.yue.chip.annotation.YueChipDDDEntity;
import com.yue.chip.common.business.expose.file.FileExposeService;
import com.yue.chip.upms.assembler.organizational.OrganizationalMapper;
import com.yue.chip.upms.assembler.resources.ResourcesMapper;
import com.yue.chip.upms.assembler.role.RoleMapper;
import com.yue.chip.upms.definition.user.UserDefinition;
import com.yue.chip.upms.domain.repository.organizational.OrganizationalRepository;
import com.yue.chip.upms.domain.repository.tenant.TenantRepository;
import com.yue.chip.upms.domain.repository.upms.UpmsRepository;
import com.yue.chip.upms.enums.Scope;
import com.yue.chip.upms.infrastructure.po.user.UserPo;
import com.yue.chip.upms.interfaces.vo.resources.ResourcesTreeListVo;
import com.yue.chip.utils.CurrentUserUtil;
import jakarta.annotation.Resource;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.util.Assert;

import java.util.*;

/**
 * @author Mr.Liu
 * @date 2023/1/12 下午2:11
 * @description 用户聚合根 此聚合根非彼聚合根 意思意思
 */
@Data
@EqualsAndHashCode(callSuper=true)
@SuperBuilder
@NoArgsConstructor
@YueChipDDDEntity
public class User extends UserDefinition {

    @Resource
    private static UpmsRepository upmsRepository;

    @DubboReference
    private static FileExposeService fileExposeService;

    @Resource
    private static OrganizationalRepository organizationalRepository;

    @Resource
    private static TenantRepository tenantRepository;

    @Builder.Default
    private RoleMapper roleMapper = RoleMapper.INSTANCE;

    @Builder.Default
    private ResourcesMapper resourcesMapper = ResourcesMapper.INSTANCE;
    @Builder.Default
    private OrganizationalMapper organizationalMapper = OrganizationalMapper.INSTANCE;

    /**
     * 组织架构
     */
    private Organizational organizational;

    private List<Organizational> organizationalList;

    /**
     * 角色 - 值对象(此值对象非彼值对象) 意思意思
     */
    private List<Role> roles;

    /**
     * 租户
     */
    private Tenant tenant;


    public Boolean checkUsernameIsExist() {
        Assert.hasText(getUsername(),"帐号不能为空");
        Optional<User> optional = upmsRepository.findUserByUsername(getUsername());
        return optional.isPresent();
    }

    public List<Role> getRoles() {
        if (Objects.nonNull(this.roles)) {
            return this.roles;
        }
        Assert.notNull(getId(),"id不能为空");
        List<Role> list = upmsRepository.findRoleByUserId(getId());
        return list;
    }

    /**
     * 获取用户关联的资源权限(平级)
     * @return
     */
    public List<Resources> getResources() {
        List<Role> listRole = getRoles();
        List<Resources > resourcesList = new ArrayList<>();
        listRole.forEach(role -> {
            List<Resources> list = role.getResources();
            resourcesList.addAll(list);
        });
        return resourcesList;
    }

    /**
     * 获取用户关联的权限(树形结构)
     * @return
     */
    public List<ResourcesTreeListVo> getResourcesTree() {
        Assert.notNull(getId(),"id不能为空");
        List<ResourcesTreeListVo> list = upmsRepository.findResourcesToTreeList(getId(),0L, Scope.CONSOLE);
        return list;
    }

    @Override
    public String getProfilePhotoUrl() {
        Assert.notNull(getId(),"id不能为空");
        return fileExposeService.getUrlSingle(getId(),UserPo.PROFILE_PHOTO_FIELD_NAME, UserPo.TABLE_NAME, CurrentUserUtil.getCurrentUserTenantNumber());
    }

    @Override
    public Long getProfilePhotoId() {
        Assert.notNull(getId(),"id不能为空");
        Map<String,String> fileMap = fileExposeService.getUrl(getId(),UserPo.PROFILE_PHOTO_FIELD_NAME, UserPo.TABLE_NAME, CurrentUserUtil.getCurrentUserTenantNumber());
        if (Objects.nonNull(fileMap) && fileMap.size()>0) {
            Object obj = fileMap.keySet().toArray()[0];
            if (obj instanceof Long) {
                return (Long) obj;
            }else {
                return Long.valueOf(String.valueOf(obj));
            }
        }
        return null;
    }

    public Organizational getOrganizational() {
        if (Objects.nonNull(organizational)) {
            return this.organizational;
        }
        Assert.notNull(getId(),"id不能为空");
        Optional<Organizational> optional = organizationalRepository.findByUserIdFist(getId());
        if (optional.isPresent()) {
            return optional.get();
        }
        return Organizational.builder().build();
    }

    public List<Organizational> getOrganizationalList() {
        if (Objects.nonNull(organizationalList)) {
            return this.organizationalList;
        }
        Assert.notNull(getId(),"id不能为空");
        List<Organizational> list = organizationalRepository.findByUserId(getId());
        return list;
    }

    public Tenant getTenant() {
        if (Objects.nonNull(tenant)) {
            return this.tenant;
        }
        Optional<Tenant> optional = tenantRepository.findTenantByTenantNumber(getTenantNumber());
        if (optional.isPresent()){
            return optional.get();
        }
        return Tenant.builder().build();
    }
}
