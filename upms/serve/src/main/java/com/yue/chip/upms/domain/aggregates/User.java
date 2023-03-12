package com.yue.chip.upms.domain.aggregates;

import com.yue.chip.upms.definition.aggregates.ResourcesVODefinition;
import com.yue.chip.upms.definition.aggregates.RoleARVODefinition;
import com.yue.chip.upms.definition.aggregates.UserARDefinition;
import com.yue.chip.upms.domain.repository.upms.UpmsRepository;
import com.yue.chip.upms.enums.Scope;
import com.yue.chip.upms.infrastructure.assembler.resources.ResourcesMapper;
import com.yue.chip.upms.infrastructure.assembler.role.RoleMapper;
import com.yue.chip.upms.interfaces.vo.resources.ResourcesTreeList;
import com.yue.chip.utils.SpringContextUtil;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Mr.Liu
 * @date 2023/1/12 下午2:11
 * @description 用户聚合根
 */
@Data
@EqualsAndHashCode(callSuper=true)
@SuperBuilder
@NoArgsConstructor
public class User extends UserARDefinition {

    private  static volatile UpmsRepository upmsRepository;

    @Builder.Default
    private RoleMapper roleMapper = RoleMapper.INSTANCE;

    @Builder.Default
    private ResourcesMapper resourcesMapper = ResourcesMapper.INSTANCE;

    @Override
    public List<RoleARVODefinition> getRoles() {
        List<? extends RoleARVODefinition> list = getRepository().findRoleByUserId(getId());
        super.setRoles((List<RoleARVODefinition>) list);
        return super.getRoles();
    }

    /**
     * 获取用户关联的资源权限(平级)
     * @return
     */
    public List<Resources> getResources() {
        List<RoleARVODefinition> listRoleARVODefinition = getRoles();
        List<Role> roleList = roleMapper.listRoleARVODefinitionToRoleList(listRoleARVODefinition);
        List<Resources > resourcesList = new ArrayList<>();
        roleList.forEach(role -> {
            List<ResourcesVODefinition> list = role.getResources();
            resourcesList.addAll(resourcesMapper.listResourcesVODefinitionToResourcesList(list));
        });
        return resourcesList;
    }

    /**
     * 获取用户关联的权限(树形结构)
     * @return
     */
    public List<ResourcesTreeList> getResourcesTree() {
        List<ResourcesTreeList> list = getRepository().findResourcesToTreeList(getId(),0L, Scope.CONSOLE);
        return list;
    }


    private UpmsRepository getRepository() {
        if (Objects.isNull(upmsRepository)) {
            synchronized (this) {
                if (Objects.isNull(upmsRepository)) {
                    upmsRepository = (UpmsRepository) SpringContextUtil.getBean(UpmsRepository.class);
                }
            }
        }
        return upmsRepository;
    }

}
