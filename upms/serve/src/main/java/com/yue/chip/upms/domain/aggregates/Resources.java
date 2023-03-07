package com.yue.chip.upms.domain.aggregates;

import com.yue.chip.upms.definition.aggregates.ResourcesVODefinition;
import com.yue.chip.upms.domain.repository.upms.UpmsRepository;
import com.yue.chip.utils.SpringContextUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

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
public class Resources extends ResourcesVODefinition {
    private  static volatile UpmsRepository upmsRepository;

    /**
     * 判断编码是否存在
     * @return
     */
    public Boolean checkCodeIsExist() {
        Optional<Resources> optional =  getRepository().findResourcesByCode(getCode());
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
        Optional<Resources> optional =  getRepository().findResourcesByName(getName());
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
        Optional<Resources> optional =  getRepository().findResourcesByUrl(getUrl());
        if (optional.isPresent()) {
            return checkIsExist(optional.get(), getId());
        }
        return false;
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
