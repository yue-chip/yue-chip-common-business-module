package com.yue.chip.upms.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.yue.chip.upms.definition.user.UserDefinition;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author xianming.chen
 * @description: TODO
 * @date 2023-10-31
 */
@Data
@EqualsAndHashCode(callSuper=true)
@SuperBuilder
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true,value = {"createDateTime","updateDateTime","createUserId","updateUserId"
        ,"profilePhotoId","profilePhotoUrl","password","state","isSms","isCall","lastLoginTime"})
public class UserExposeVo extends UserDefinition {
}
