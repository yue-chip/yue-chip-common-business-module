package com.yue.chip.upms.interfaces.vo.tenant;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.yue.chip.core.common.enums.State;
import com.yue.chip.upms.definition.tenant.TenantDefinition;
import com.yue.chip.upms.util.CCSPUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Objects;

/**
 * @author Mr.Liu
 * @description: TODO
 * @date 2023/10/24 上午11:35
 */
@Data
@Schema(description = "租户")
@EqualsAndHashCode(callSuper=true)
@SuperBuilder
@NoArgsConstructor
@JsonIgnoreProperties(
        ignoreUnknown = true,
        value = { "updateDateTime", "createUserId", "updateUserId"}
)
public class TenantVo extends TenantDefinition {

    /**
     *
     */
    private Boolean stateTmp;

    public Boolean getStateTmp() {
        return Objects.equals(getState(), State.NORMAL);
    }

    @Override
    public String getManager() {
        String encrypt = super.getManagerEncrypt();
        String hmac = super.getManagerHmac();
        String decrypt = CCSPUtil.SM4decrypt(encrypt);
        if (CCSPUtil.checkoutHMac(decrypt, hmac)) {
            return decrypt;
        } else {
            return "数据被篡改";
        }
    }

    @Override
    public String getPhoneNumber() {
        String encrypt = super.getPhoneNumberEncrypt();
        String hmac = super.getPhoneNumberHmac();
        String decrypt = CCSPUtil.SM4decrypt(encrypt);
        if (CCSPUtil.checkoutHMac(decrypt, hmac)) {
            return decrypt;
        } else {
            return "数据被篡改";
        }
    }
}
