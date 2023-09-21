package com.yue.chip.upms.state.machine.test;

import cn.hutool.core.util.NumberUtil;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.yue.chip.core.IEnum;
import com.yue.chip.core.common.enums.EnumConverter;
import com.yue.chip.core.common.enums.State;
import com.yue.chip.upms.enums.Scope;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author Mr.Liu
 * @date 2023/8/10 下午2:14
 */
public enum States implements IEnum {
    DRAFT(0, "DRAFT"),
    PUBLISH_TODO(1, "PUBLISH_TODO"),
    PUBLISH_DONE(2, "PUBLISH_DONE");

    private final int key;
    private final String desc;
    public static final String code = "state";
    public static final String version = "1";

    private States(int key, String desc) {
        this.key = key;
        this.desc = desc;
    }

    public Integer getKey() {
        return this.key;
    }

    public String getName() {
        return this.toString();
    }

    public String getDesc() {
        return this.desc;
    }

    public static final String DRAFT1 = States.DRAFT.toString();

    public Map<String, Object> jsonValue() {
        Map<String, Object> map = new HashMap();
        map.put("key", this.key);
        map.put("desc", this.desc);
        map.put("name", this.getName());
        return map;
    }

    @JsonCreator
    public static States instance(Object value){
        if (Objects.isNull(value)){
            return null;
        }
        if (NumberUtil.isInteger(String.valueOf(value))) {
            return instance(Integer.valueOf(String.valueOf(value)));
        }
        return instance(String.valueOf(value));
    }

    private static States instance(Integer key){
        for(States item : values()){
            if (item.getKey()==key){
                return item;
            }
        }
        return null;
    }

    private static States instance(String name){
        for(States item : values()){
            if(Objects.equals(item.getName(),name)){
                return item;
            }
        }
        return null;
    }

    public static class StatesConverter extends EnumConverter<States, Integer> {
    }
}
