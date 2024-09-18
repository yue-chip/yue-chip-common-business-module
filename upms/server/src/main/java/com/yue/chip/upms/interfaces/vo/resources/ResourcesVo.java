package com.yue.chip.upms.interfaces.vo.resources;

import com.yue.chip.upms.definition.resources.ResourcesDefinition;
import com.yue.chip.utils.Sm4Api;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.util.StringUtils;

/**
 * @author Mr.Liu
 * @date 2023/4/6 上午10:17
 */
@Data
//@Schema(description = "资源")
@EqualsAndHashCode(callSuper=true)
@SuperBuilder
@NoArgsConstructor
public class ResourcesVo extends ResourcesDefinition {

    @Override
    public String getName() {
        if (StringUtils.hasText(super.getName())) {
            return new Sm4Api().generalDataDec( super.getName());
        }
        return super.getName();
    }

    @Override
    public String getCode() {
        if (StringUtils.hasText(super.getCode())) {
            return new Sm4Api().generalDataDec( super.getCode());
        }
        return super.getCode();
    }

    @Override
    public String getUrl() {
        if (StringUtils.hasText(super.getUrl())) {
            return new Sm4Api().generalDataDec( super.getUrl());
        }
        return super.getUrl();
    }
}
