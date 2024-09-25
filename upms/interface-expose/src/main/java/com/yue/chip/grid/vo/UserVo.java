package com.yue.chip.grid.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.yue.chip.upms.definition.user.UserDefinition;
import com.yue.chip.utils.Sm4Api;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.util.StringUtils;

/**
 * @author xianming.chen
 * @description: TODO
 * @date 2024-06-03
 */
@Data
@EqualsAndHashCode(callSuper=true)
@SuperBuilder
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true,value = {"updateDateTime","password","accountNonExpired","accountNonLocked","credentialsNonExpired","enabled","createUserId","updateUserId"})
public class UserVo extends UserDefinition {

    private String organizationalName;

    private Long organizationalId;

    private String tenantName;

    private String tenantAbbreviation;

    private Long tenantNumber;

    private String bigScreenName;


    @Override
    public String getName() {
        if (StringUtils.hasText(super.getName())) {
            System.out.println(super.getName());
            return new Sm4Api().generalDataDec( super.getName(),super.getNameHmac());
        }
        return super.getName();
    }

    @Override
    public String getPhoneNumber() {
        if (StringUtils.hasText(super.getPhoneNumber())) {
            System.out.println(super.getPhoneNumber());
            return new Sm4Api().generalDataDec( super.getPhoneNumber(),super.getPhoneNumberHmac());
        }
        return super.getPhoneNumber();
    }
}
