package com.yue.chip.upms.domain.aggregates;

import com.yue.chip.upms.definition.role.RoleDefinition;
import com.yue.chip.upms.domain.repository.upms.UpmsRepository;
import com.yue.chip.utils.SpringContextUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author Mr.Liu
 * @date 2023/1/12 下午2:13
 * @description 角色值对象/聚合根（角色-资源，角色就是聚合根）
 */
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper=true)
@NoArgsConstructor
public class Role extends RoleDefinition {

    private  static volatile UpmsRepository upmsRepository;

    @Schema(description = "资源")
    private List<Resources> resources;

    public List<Resources> getResources() {
        if (Objects.nonNull(this.resources)) {
            return this.resources;
        }
        List<Resources> list = getRepository().findResourcesByRoleId(getId());
        return list;
    }

    /**
     * 获取角色关联资源的id
     * @return
     */
    public List<Long> getResourcesId(){
        List<Resources> list = getResources();
        List<Long> returnList = new ArrayList<>();
        list.forEach(resources -> {
            returnList.add(resources.getId());
        });
        return returnList;
    }

    /**
     * 获取角色关联资源的id(仅限前端展示用)
     * @return
     */
    public List<Long> getResourcesIdForFront(){
        List<Resources> list = getRepository().findResourcesByRoleId(getId());
        List<Long> returnList = new ArrayList<>();
        list.forEach(resources -> {
            List<Resources> allChildren = resources.getAllChildren();
            AtomicReference<Boolean> isCheckedAllChildren = new AtomicReference<>(false);
            allChildren.forEach(cr -> {
                list.forEach(r->{
                    if (Objects.equals(cr.getId(),r.getId())) {
                        isCheckedAllChildren.set(true);
                    }
                });
            });
            if (isCheckedAllChildren.get()) {
                returnList.add(resources.getId());
            }
        });
        return returnList;
    }


    /**
     * 获取角色关联的用户
     * @return
     */
    public List<User> getUser() {
        List<User> list = getRepository().findUserByRoleId(getId());
        return list;
    }

    /**
     * 获取角色关联的用户id
     * @return
     */
    public List<Long> getUserId(){
        List<User> list = getUser();
        List<Long> returnList = new ArrayList<>();
        list.forEach(user -> {
            returnList.add(user.getId());
        });
        return returnList;
    }

    public Boolean checkNameIsExist() {
        Optional<Role> optional = getRepository().findRoleByName(getName());
        if (optional.isPresent()) {
            if (Objects.nonNull(getId()) && optional.get().getId().equals(getId())){
                return false;
            }
            return true;
        }
        return false;
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
