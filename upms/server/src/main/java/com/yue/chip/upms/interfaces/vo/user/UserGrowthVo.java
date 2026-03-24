package com.yue.chip.upms.interfaces.vo.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户增长统计VO
 * @author zak
 */
@Data
@Schema(description = "用户增长统计")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserGrowthVo {

    @Schema(description = "时间标识（如：2026-01 或 2026）")
    private String timeLabel;

    @Schema(description = "用户数量")
    private Long count;

}
