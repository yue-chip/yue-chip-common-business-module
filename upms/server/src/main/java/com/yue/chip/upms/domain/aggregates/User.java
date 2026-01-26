package com.yue.chip.upms.domain.aggregates;

import com.yue.chip.common.business.expose.file.RemoteFile;
import com.yue.chip.core.ResultData;
import com.yue.chip.core.tenant.common.TenantDefinition;
import com.yue.chip.core.tenant.remote.http.RemoteTenant;
import com.yue.chip.upms.assembler.resources.ResourcesMapper;
import com.yue.chip.upms.assembler.role.RoleMapper;
import com.yue.chip.upms.definition.user.UserDefinition;
import com.yue.chip.upms.domain.repository.organizational.OrganizationalRepository;
import com.yue.chip.upms.domain.repository.upms.UpmsRepository;
import com.yue.chip.upms.enums.Scope;
import com.yue.chip.upms.infrastructure.po.user.UserPo;
import com.yue.chip.upms.interfaces.vo.resources.ResourcesTreeListVo;
import com.yue.chip.utils.CheckRemoteHttpResultDataUtil;
import jakarta.annotation.Resource;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.*;

/**
 * @author Mr.Liu
 * @date 2023/1/12 下午2:11
 * @description 用户聚合根 此聚合根非彼聚合根 意思意思
 */
@Data
@EqualsAndHashCode(callSuper=false)
@SuperBuilder
@NoArgsConstructor
@Component
public class User extends UserDefinition {

    private static UpmsRepository upmsRepository;
    private static RemoteFile remoteFile;
    private static OrganizationalRepository organizationalRepository;
    private static RemoteTenant remoteTenant;

    @Builder.Default
    private RoleMapper roleMapper = RoleMapper.INSTANCE;

    @Builder.Default
    private ResourcesMapper resourcesMapper = ResourcesMapper.INSTANCE;

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
    private TenantDefinition tenantDefinition;


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
        ResultData<String> resultData = remoteFile.urlSingle(getId(),UserPo.PROFILE_PHOTO_FIELD_NAME, UserPo.TABLE_NAME);
        CheckRemoteHttpResultDataUtil.check(resultData);
        return resultData.getData();
    }

    @Override
    public Long getProfilePhotoId() {
        Assert.notNull(getId(),"id不能为空");
        ResultData<Map<String, String>>  resultData = remoteFile.url(getId(),UserPo.PROFILE_PHOTO_FIELD_NAME, UserPo.TABLE_NAME);
        CheckRemoteHttpResultDataUtil.check(resultData);
        Map<String,String> fileMap = resultData.getData();
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

    @Override
    public String getOtherPhotoUrl() {
        Assert.notNull(getId(),"id不能为空");
        ResultData<String> resultData = remoteFile.urlSingle(getId(),UserPo.OTHER_PHOTO_FIELD_NAME, UserPo.TABLE_NAME);
        CheckRemoteHttpResultDataUtil.check(resultData);
        return resultData.getData();
    }

    @Override
    public Long getOtherPhotoId() {
        Assert.notNull(getId(),"id不能为空");
        ResultData<Map<String, String>>  resultData = remoteFile.url(getId(),UserPo.OTHER_PHOTO_FIELD_NAME, UserPo.TABLE_NAME);
        CheckRemoteHttpResultDataUtil.check(resultData);
        Map<String,String> fileMap = resultData.getData();
        if (Objects.nonNull(fileMap) && !fileMap.isEmpty()) {
            Object obj = fileMap.keySet().toArray()[0];
            if (obj instanceof Long) {
                return (Long) obj;
            }else {
                return Long.valueOf(String.valueOf(obj));
            }
        }
        return null;
    }

    public List<Organizational> getOrganizationalList() {
        if (Objects.nonNull(organizationalList)) {
            return this.organizationalList;
        }
        Assert.notNull(getId(),"id不能为空");
        List<Organizational> list = organizationalRepository.findAllByUserId(getId());
        return list;
    }

    public Organizational getOrganizational() {
        if (Objects.nonNull(organizational)) {
            return this.organizational;
        }
        Assert.notNull(getId(),"id不能为空");
        if (Objects.nonNull(organizational)) {
            return this.organizational;
        }
        Optional<Organizational> optional = organizationalRepository.findByUserId(getId());
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
    }

    public TenantDefinition getTenant() {
        if (Objects.nonNull(tenantDefinition)) {
            return this.tenantDefinition;
        }
        ResultData<TenantDefinition> resultData = remoteTenant.get(getTenantNumber());
        CheckRemoteHttpResultDataUtil.check(resultData);
        TenantDefinition  tenantDefinition = resultData.getData();
        return tenantDefinition;
    }

    @Resource
    public void setUpmsRepository(UpmsRepository upmsRepository) {
        User.upmsRepository = upmsRepository;
    }

    @Resource
    public void setRemoteFile(RemoteFile remoteFile) {
        User.remoteFile = remoteFile;
    }

    @Resource
    public void setOrganizationalRepository(OrganizationalRepository organizationalRepository) {
        User.organizationalRepository = organizationalRepository;
    }

    @Resource
    public void setRemoteTenant(RemoteTenant remoteTenant) {
        User.remoteTenant = remoteTenant;
    }

    public int hashCode() {
        return 1;
    }
}
