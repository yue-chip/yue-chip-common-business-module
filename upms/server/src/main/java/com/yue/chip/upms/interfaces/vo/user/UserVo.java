package com.yue.chip.upms.interfaces.vo.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.yue.chip.upms.definition.user.UserDefinition;
import com.yue.chip.upms.domain.aggregates.Organizational;
import com.yue.chip.upms.util.CCSPUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Mr.Liu
 * @date 2023/3/8 下午1:49
 */
@Data
@Schema(description = "用户")
@EqualsAndHashCode(callSuper=true)
@SuperBuilder
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true,value = {"updateDateTime","password","accountNonExpired","accountNonLocked","credentialsNonExpired","enabled","createUserId","updateUserId"})
public class UserVo extends UserDefinition {

    @Schema(description = "组织机构名称")
    private String organizationalName;

    @Schema(description = "组织机构id")
    private Long organizationalId;

    private List<Organizational> organizationalList;

    @Schema(description = "租户名称")
    private String tenantName;

    @Schema(description = "租户简称")
    private String tenantAbbreviation;

    @Schema(description = "租户编码")
    private Long tenantNumber;

    @Schema(description = "数字大屏名称")
    private String bigScreenName;

    @Override
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public LocalDateTime getLastPasswordTime() {
        return super.getLastPasswordTime();
    }

    @Override
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public LocalDateTime getLastLoginTime() {
        return super.getLastLoginTime();
    }

    @Override
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public LocalDateTime getCreateDateTime() {
        return super.getCreateDateTime();
    }


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

    @Override
    public String getIdentificationNumber() {
        String encrypt = super.getIdentificationNumberEncrypt();
        String hmac = super.getIdentificationNumberHmac();
        String decrypt = CCSPUtil.SM4decrypt(encrypt);
        if (CCSPUtil.checkoutHMac(decrypt, hmac)) {
            return decrypt;
        } else {
            return "数据被篡改";
        }
    }
}
