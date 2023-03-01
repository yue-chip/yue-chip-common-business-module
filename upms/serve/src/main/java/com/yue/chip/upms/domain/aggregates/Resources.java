package com.yue.chip.upms.domain.aggregates;

import com.yue.chip.upms.definition.aggregates.ResourcesVODefinition;
import com.yue.chip.upms.domain.repository.resources.ResourcesRepository;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Objects;

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

    /**
     * 判断编码是否存在
     * @param resources
     * @param code
     * @param id
     * @return
     */
    public Boolean checkCodeIsExist(Resources resources, String code,Long id) {
        return checkIsExist(resources,code,id);
    }

    /**
     * 判断名称是否存在
     * @param resources
     * @param name
     * @param id
     * @return
     */
    public Boolean checkNameIsExist(Resources resources, String name,Long id) {
        return checkIsExist(resources,name,id);
    }

    /**
     * 判断url是否存在
     * @param resources
     * @param url
     * @param id
     * @return
     */
    public Boolean checkUrlIsExist(Resources resources, String url,Long id) {
        return checkIsExist(resources,url,id);
    }

    private Boolean checkIsExist(Resources resources, String value,Long id) {
        if (Objects.isNull(resources)) {
            return false;
        }
        if (!StringUtils.hasText(value)){
            return false;
        }
        if (Objects.nonNull(id) && resources.getId().equals(id)){
            return false;
        }
        return true;
    }
}
