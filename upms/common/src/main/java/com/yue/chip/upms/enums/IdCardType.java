package com.yue.chip.upms.enums;

import cn.hutool.core.util.NumberUtil;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.yue.chip.core.IEnum;
import com.yue.chip.core.common.enums.EnumConverter;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author coby
 * @description: TODO
 * @date 2024/1/8 下午4:13
 */
public enum IdCardType implements IEnum {

    ID_CARD(0, "身份证");

    private final int key;

    private final String desc;


    public static final String code = "idCardType";

    public static final String version = "1";

    private IdCardType(int key, String desc) {
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
    public static IdCardType instance(Object value){
        if (Objects.isNull(value)){
            return null;
        }
        if (NumberUtil.isInteger(String.valueOf(value))) {
            return instance(Integer.valueOf(String.valueOf(value)));
        }
        return instance(String.valueOf(value));
    }

    private static IdCardType instance(Integer key){
        for(IdCardType item : values()){
            if (item.getKey()==key){
                return item;
            }
        }
        return null;
    }

    private static IdCardType instance(String name){
        for(IdCardType item : values()){
            if(Objects.equals(item.getName(),name)){
                return item;
            }
        }
        return null;
    }

    public static class IdCardTypeConverter extends EnumConverter<IdCardType,Integer> {
    }
}
