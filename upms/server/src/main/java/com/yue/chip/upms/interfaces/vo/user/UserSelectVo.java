package com.yue.chip.upms.interfaces.vo.user;

import com.yue.chip.utils.Sm4Api;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.util.StringUtils;

/**
 * @author Mr.Liu
 * @date 2023/3/8 下午1:49
 */
@Data
//@Schema(description = "用户")
@SuperBuilder
@NoArgsConstructor
public class UserSelectVo {

    //@Schema(description = "用户id")
    private String value;

    //@Schema(description = "用户姓名")
    private String label;

    public String getLabel() {
        if (StringUtils.hasText(this.label)) {
            return new Sm4Api().generalDataDec( this.label,"");
        }
        return this.label;
    }
    
}
