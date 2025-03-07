package com.yue.chip.upms.interfaces.vo.resources;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.yue.chip.annotation.YueChipDDDEntity;
import com.yue.chip.common.business.expose.file.FileExposeService;
import com.yue.chip.upms.definition.resources.ResourcesDefinition;
import com.yue.chip.upms.infrastructure.po.resources.ResourcesPo;
import com.yue.chip.upms.util.CCSPUtil;
import com.yue.chip.utils.CurrentUserUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.util.Assert;

import java.util.Map;
import java.util.Objects;

/**
 * @author Mr.Liu
 * @date 2023/4/6 上午10:17
 */
@Data
@Schema(description = "资源")
@EqualsAndHashCode(callSuper=true)
@SuperBuilder
@NoArgsConstructor
@YueChipDDDEntity
public class ResourcesVo extends ResourcesDefinition {

    @DubboReference
    private static FileExposeService fileExposeService;

    @Override
    public String getName() {
        String encrypt = super.getNameEncrypt();
        String hmac = super.getNameHmac();
        String decrypt = CCSPUtil.SM4decrypt(encrypt);
        if (CCSPUtil.checkoutHMac(decrypt, hmac)) {
            return decrypt;
        } else {
            return "数据被篡改";
        }
    }

    @Override
    public String getIconUrl() {
        Assert.notNull(getId(),"id不能为空");
        return fileExposeService.getUrlSingle(getId(), ResourcesPo.ICON_PHOTO_FIELD_NAME, ResourcesPo.TABLE_NAME, CurrentUserUtil.getCurrentUserTenantNumber());
    }

    @Override
    public Long getIconId() {
        Assert.notNull(getId(),"id不能为空");
        Map<String,String> fileMap = fileExposeService.getUrl(getId(),ResourcesPo.ICON_PHOTO_FIELD_NAME, ResourcesPo.TABLE_NAME, CurrentUserUtil.getCurrentUserTenantNumber());
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
}
