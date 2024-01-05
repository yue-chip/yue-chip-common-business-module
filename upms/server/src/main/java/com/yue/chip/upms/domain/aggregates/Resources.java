package com.yue.chip.upms.domain.aggregates;

import com.yue.chip.annotation.YueChipDDDEntity;
import com.yue.chip.upms.definition.resources.ResourcesDefinition;
import com.yue.chip.upms.domain.repository.upms.UpmsRepository;
import com.yue.chip.utils.SpringContextUtil;
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

/**
 * @author Mr.Liu
 * @date 2023/1/12 下午2:17
 * @description 资源值对象
 */
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper=true)
@NoArgsConstructor
@YueChipDDDEntity
public class Resources extends ResourcesDefinition {

    @Resource
    private  static UpmsRepository upmsRepository;

    /**
     * 判断编码是否存在
     * @return
     */
    public Boolean checkCodeIsExist() {
        Assert.hasText(getCode(),"编码不能为空");
        Optional<Resources> optional =  upmsRepository.findResourcesByCode(getCode());
        if (optional.isPresent()) {
            return checkIsExist(optional.get(), getId());
        }
        return false;
    }

    /**
     * 判断名称是否存在
     * @return
     */
    public Boolean checkNameIsExist() {
        Assert.hasText(getName(),"名称不能为空");
        Assert.notNull(getParentId(),"父节点id不能为空");
        Optional<Resources> optional =  upmsRepository.findResourcesByNameAndParentId(getName(),getParentId());
        if (optional.isPresent()) {
            return checkIsExist(optional.get(), getId());
        }
        return false;
    }

    /**
     * 判断url是否存在
     * @return
     */
    public Boolean checkUrlIsExist() {
        Assert.hasText(getUrl(),"url不能为空");
        Optional<Resources> optional =  upmsRepository.findResourcesByUrl(getUrl());
        if (optional.isPresent()) {
            return checkIsExist(optional.get(), getId());
        }
        return false;
    }

    public Optional<Resources> getParent() {
        Assert.notNull(getParentId(),"父节点id不能为空");
        return upmsRepository.findResourcesById(getParentId());
    }

    public List<Resources> getChildren() {
        Assert.notNull(getId(),"id不能为空");
        return upmsRepository.findResourcesByParentId(getId());
    }

    public List<Resources> getAllChildren() {
        List<Resources> list = new ArrayList<>();
        getAllChildren(list);
        return list;
    }
    private void getAllChildren(List<Resources> list) {
        List<Resources> children = getChildren();
        list.addAll(children);
        children.forEach(resources -> {
            resources.getAllChildren(list);
        });
    }

    private Boolean checkIsExist(Resources resources,Long id) {
        if (Objects.isNull(resources)) {
            return false;
        }
        if (Objects.nonNull(id) && resources.getId().equals(id)){
            return false;
        }
        return true;
    }

}
