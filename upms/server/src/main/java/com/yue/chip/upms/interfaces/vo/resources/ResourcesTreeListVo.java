package com.yue.chip.upms.interfaces.vo.resources;

import com.yue.chip.upms.definition.resources.ResourcesDefinition;
import com.yue.chip.utils.Sm4Api;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author Mr.Liu
 * @date 2023/2/28 下午2:31
 */
@Data
//@Schema(description = "资源")
@EqualsAndHashCode(callSuper=true)
@SuperBuilder
@NoArgsConstructor
public class ResourcesTreeListVo extends ResourcesDefinition {

    private List<ResourcesTreeListVo> children;


    @Override
    public String getName() {
        if (StringUtils.hasText(super.getName())) {
            return new Sm4Api().generalDataDec( super.getName(),super.getNameHmac());
        }
        return super.getName();
    }

    @Override
    public String getCode() {
        if (StringUtils.hasText(super.getCode())) {
            return new Sm4Api().generalDataDec( super.getCode(),super.getCodeHmac());
        }
        return super.getCode();
    }

    @Override
    public String getUrl() {
        if (StringUtils.hasText(super.getUrl())) {
            return new Sm4Api().generalDataDec( super.getUrl(),super.getUrlHmac());
        }
        return super.getUrl();
    }
}
