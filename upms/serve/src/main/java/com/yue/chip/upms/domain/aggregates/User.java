package com.yue.chip.upms.domain.aggregates;

import com.yue.chip.annotation.YueChipDDDEntity;
import com.yue.chip.upms.definition.user.UserDefinition;
import com.yue.chip.upms.domain.repository.upms.UpmsRepository;
import com.yue.chip.upms.enums.Scope;
import com.yue.chip.upms.assembler.resources.ResourcesMapper;
import com.yue.chip.upms.assembler.role.RoleMapper;
import com.yue.chip.upms.interfaces.vo.resources.ResourcesTreeListVo;
import jakarta.annotation.Resource;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author Mr.Liu
 * @date 2023/1/12 下午2:11
 * @description 用户聚合根
 */
@Data
@EqualsAndHashCode(callSuper=true)
@SuperBuilder
@NoArgsConstructor
@YueChipDDDEntity
public class User extends UserDefinition {

    @Resource
    private  static UpmsRepository upmsRepository;

    @Builder.Default
    private RoleMapper roleMapper = RoleMapper.INSTANCE;

    @Builder.Default
    private ResourcesMapper resourcesMapper = ResourcesMapper.INSTANCE;

    /**
     * 角色
     */
    private List<Role> roles;

    public Boolean checkUsernameIsExist() {
        Optional<User> optional = upmsRepository.findUserByUsername(getUsername());
        return optional.isPresent();
    }

    public List<Role> getRoles() {
        if (Objects.nonNull(this.roles)) {
            return this.roles;
        }
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
        List<ResourcesTreeListVo> list = upmsRepository.findResourcesToTreeList(getId(),0L, Scope.CONSOLE);
        return list;
    }

}
