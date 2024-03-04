package com.yue.chip.upms.domain.aggregates;

import com.yue.chip.annotation.YueChipDDDEntity;
import com.yue.chip.common.business.expose.file.FileExposeService;
import com.yue.chip.upms.definition.resources.ResourcesDefinition;
import com.yue.chip.upms.domain.repository.upms.UpmsRepository;
import com.yue.chip.upms.enums.Type;
import com.yue.chip.upms.infrastructure.po.resources.ResourcesPo;
import jakarta.annotation.Resource;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.util.Assert;

import java.util.*;

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

    @DubboReference
    private static FileExposeService fileExposeService;

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
        if (Objects.equals(getType(),Type.MENU)) {
            Assert.hasText(getUrl(), "url不能为空");
            Optional<Resources> optional = upmsRepository.findResourcesByUrl(getUrl());
            if (optional.isPresent()) {
                return checkIsExist(optional.get(), getId());
            }
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

    @Override
    public Long getIconId() {
        Assert.notNull(getId(),"id不能为空");
        Map<String,String> fileMap = fileExposeService.getUrl(getId(),ResourcesPo.TABLE_NAME, ResourcesPo.ICON_FIELD_NAME);
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
    public String getIconUrl() {
        Assert.notNull(getId(),"id不能为空");
        return fileExposeService.getUrlSingle(getId(), ResourcesPo.ICON_FIELD_NAME, ResourcesPo.TABLE_NAME);
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
