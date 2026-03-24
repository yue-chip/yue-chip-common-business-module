package com.yue.chip.upms.interfaces.vo.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 用户年度增长统计VO
 * @author zak
 */
@Data
@Schema(description = "用户年度增长统计")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserGrowthByYearVo {

    @Schema(description = "年度总数量")
    private Long totalCount;

    @Schema(description = "每月数量列表")
    private List<UserGrowthVo> monthlyList;

}
