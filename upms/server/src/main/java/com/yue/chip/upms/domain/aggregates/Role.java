package com.yue.chip.upms.domain.aggregates;

import com.yue.chip.annotation.YueChipDDDEntity;
import com.yue.chip.upms.definition.role.RoleDefinition;
import com.yue.chip.upms.domain.repository.upms.UpmsRepository;
import com.yue.chip.utils.SpringContextUtil;
//import io.swagger.v3.oas.annotations.media.Schema;
import javax.annotation.Resource;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.util.Assert;

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
@YueChipDDDEntity
public class Role extends RoleDefinition {

    @Resource
    private  static UpmsRepository upmsRepository;

    /**
     *  资源 - 值对象(此值对象非彼值对象) 意思意思
     */
    private List<Resources> resources;

    public List<Resources> getResources() {
        if (Objects.nonNull(this.resources)) {
            return this.resources;
        }
        Assert.notNull(getId(),"id不能为空");
        List<Resources> list = upmsRepository.findResourcesByRoleId(getId());
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
        Assert.notNull(getId(),"id不能为空");
        List<Resources> list = upmsRepository.findResourcesByRoleId(getId());
        List<Long> returnList = new ArrayList<>();
        list.forEach(resources -> {
            List<Resources> allChildren = resources.getAllChildren();
            AtomicReference<Boolean> isCheckedAllChildren = new AtomicReference<>(true);
            allChildren.forEach(cr -> {
                if (!list.contains(cr)) {
                    isCheckedAllChildren.set(false);
                }
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
        Assert.notNull(getId(),"id不能为空");
        List<User> list = upmsRepository.findUserByRoleId(getId());
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
        Assert.hasText(getName(),"名称不能为空");
        Optional<Role> optional = upmsRepository.findRoleByName(getName());
        if (optional.isPresent()) {
            if (Objects.nonNull(getId()) && optional.get().getId().equals(getId())){
                return false;
            }
            return true;
        }
        return false;
    }
}
