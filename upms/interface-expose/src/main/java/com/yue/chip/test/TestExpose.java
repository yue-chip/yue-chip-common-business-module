package com.yue.chip.test;

import com.yue.chip.upms.definition.user.UserDefinition;
import jakarta.validation.constraints.NotBlank;

import java.util.Map;

/**
 * @author Mr.Liu
 * @date 2023/3/17 下午1:53
 */
public interface TestExpose {
    public Map<String,String> test(@NotBlank(message = "编码不能为空") String code);

    public UserDefinition test1(String code);
}
