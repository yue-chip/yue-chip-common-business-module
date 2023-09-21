package com.yue.chip.upms.state.machine.test;

import cn.hutool.core.util.NumberUtil;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.yue.chip.core.IEnum;
import com.yue.chip.core.common.enums.EnumConverter;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author Mr.Liu
 * @date 2023/8/10 下午2:14
 */
public enum Events {
    ONLINE(0, "ONLINE"),
    PUBLISH(1, "PUBLISH"),
    ROLLBACK(2, "ROLLBACK");

    private final int key;
    private final String desc;

    private Events(int key, String desc) {
        this.key = key;
        this.desc = desc;
    }

}
