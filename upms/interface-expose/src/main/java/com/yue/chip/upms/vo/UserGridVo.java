package com.yue.chip.upms.vo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.Map;

/**
 * @author jiacheng.liao on 2024/7/25
 */
@Data
@SuperBuilder
@NoArgsConstructor
public class UserGridVo implements Serializable {

    private Map<String, Long> map;

}
