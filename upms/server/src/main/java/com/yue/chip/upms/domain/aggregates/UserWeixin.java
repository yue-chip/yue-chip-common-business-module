package com.yue.chip.upms.domain.aggregates;

import com.yue.chip.annotation.YueChipDDDEntity;
import com.yue.chip.upms.definition.user.UserWeiXinDefinition;
import com.yue.chip.upms.util.CCSPUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author Mr.Liu
 * @description: TODO
 * @date 2023/10/7 下午6:25
 */
@Data
@EqualsAndHashCode(callSuper=true)
@SuperBuilder
@NoArgsConstructor
@YueChipDDDEntity
public class UserWeixin extends UserWeiXinDefinition {

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
