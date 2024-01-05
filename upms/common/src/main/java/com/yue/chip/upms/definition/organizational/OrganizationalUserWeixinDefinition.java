package com.yue.chip.upms.definition.organizational;

import com.yue.chip.core.BaseDefinition;
//import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author Mr.Liu
 * @description: 微信端维保用户与机构关联关系
 * @date 2023/10/7 下午1:44
 */
@Data
//@Schema(description = "微信端维保用户与机构关联关系")
@EqualsAndHashCode(callSuper=true)
@SuperBuilder
@NoArgsConstructor
@Deprecated
public class OrganizationalUserWeixinDefinition extends BaseDefinition {

    //@Schema(description = "微信用户id")
    private Long userWeixinId;

    //@Schema(description = "机构id")
    private Long organizationalId;
}
