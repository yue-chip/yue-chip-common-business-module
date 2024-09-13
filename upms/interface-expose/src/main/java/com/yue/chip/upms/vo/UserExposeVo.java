package com.yue.chip.upms.vo;

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
 * @date 2023-10-31
 */
@Data
@EqualsAndHashCode(callSuper=true)
@SuperBuilder
@NoArgsConstructor
public class UserExposeVo extends UserDefinition {
    private Long organizationalId;

    @Override
    public String getName() {
        if (StringUtils.hasText(super.getName())) {
            System.out.println(super.getName());
            return new Sm4Api().generalDataDec( super.getName());
        }
        return super.getName();
    }

    @Override
    public String getPhoneNumber() {
        if (StringUtils.hasText(super.getPhoneNumber())) {
            System.out.println(super.getPhoneNumber());
            return new Sm4Api().generalDataDec( super.getPhoneNumber());
        }
        return super.getPhoneNumber();
    }
}
