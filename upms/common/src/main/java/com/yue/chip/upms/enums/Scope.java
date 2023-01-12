package com.yue.chip.upms.enums;

import cn.hutool.core.util.NumberUtil;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.yue.chip.core.IEnum;
import com.yue.chip.core.common.enums.EnumConverter;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author mr.liu
 * @title: Scope
 * @description: 资源类型
 * @date 2020/8/14上午11:29
 */
public enum Scope implements IEnum {
    APP(0, "app"), CONSOLE(1, "后台"),FRONT(2,"前端"),WE_CHAT(3,"微信");

    private final int key;

    private final String desc;

    private Scope(int key, String desc) {
        this.key = key;
        this.desc = desc;
    }

    @Override
    public Integer getKey() {
        return key;
    }

    @Override
    public String getName() {
        return this.toString();
    }

    @Override
    public String getDesc(){
        return desc;
    }

    @Override
    public Map<String, Object> jsonValue() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("key", key);
        map.put("desc", desc);
        map.put("name", getName());
        return map;
    }

    @JsonCreator
    public static Scope instance(Object value){
        if (Objects.isNull(value)){
            return null;
        }
        if (NumberUtil.isInteger(String.valueOf(value))) {
            return instance(Integer.valueOf(String.valueOf(value)));
        }
        return instance(String.valueOf(value));
    }

    private static Scope instance(Integer key){
        for(Scope item : values()){
            if (item.getKey()==key){
                return item;
            }
        }
        return null;
    }

    private static Scope instance(String name){
        for(Scope item : values()){
            if(Objects.equals(item.getName(),name)){
                return item;
            }
        }
        return null;
    }

    public static class ScopeConverter extends EnumConverter<Scope,Integer> {
    }
}



