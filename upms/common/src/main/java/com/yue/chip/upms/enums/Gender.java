package com.yue.chip.upms.enums;

import cn.hutool.core.util.NumberUtil;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.yue.chip.core.IEnum;
import com.yue.chip.core.common.enums.EnumConverter;
import com.yue.chip.utils.SpringContextUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author xianming.chen
 * @description: TODO
 * @date 2024-03-05
 */
public enum Gender implements IEnum {

    BOY(0, "男"),
    GIRL(1,"女")
    ;

    private final int key;

    private final String desc;


    public static final String code = "gender";

    public static final String version = "1";

    private Gender(int key, String desc) {
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
    public static Gender instance(Object value){
        if (Objects.isNull(value)){
            return null;
        }
        String str = String.valueOf(value);
        if (NumberUtil.isInteger(str)) {
            return instance(Integer.valueOf(str));
        } else if (value instanceof LinkedHashMap<?,?>) {
            return instance(String.valueOf(((LinkedHashMap<?, ?>) value).get("name")));
        } else if (str.matches("^\\s*(\\{.*\\}|\\[.*\\])\\s*$")) {
            ObjectMapper objectMapper = (ObjectMapper) SpringContextUtil.getBean(ObjectMapper.class);
            try {
                JsonNode jsonNode = objectMapper.readTree(str);
                if (jsonNode.has("key") && NumberUtil.isInteger(jsonNode.get("key").toString())) {
                    return instance(jsonNode.get("key").intValue());
                }
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
        return instance(String.valueOf(value));
    }

    private static Gender instance(Integer key){
        for(Gender item : values()){
            if (item.getKey()==key){
                return item;
            }
        }
        return null;
    }

    private static Gender instance(String name){
        for(Gender item : values()){
            if(Objects.equals(item.getName(),name)){
                return item;
            }
        }
        return null;
    }

    public static class GenderConverter extends EnumConverter<Gender,Integer> {
    }

//    public static class GenderSerializer extends JsonSerializer<Gender> {
//        @Override
//        public void serialize(Gender value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
//            if (value == null) {
//                gen.writeNull();
//            } else {
//                gen.writeString(value.getName()); // 自定义的序列化逻辑
//            }
//        }
//    }
}
