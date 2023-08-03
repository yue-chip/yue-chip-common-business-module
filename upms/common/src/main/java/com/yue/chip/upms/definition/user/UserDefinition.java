package com.yue.chip.upms.definition.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.yue.chip.core.BaseDefinition;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Transient;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

/**
 * user 字段的定义 避免在聚合实体entity，dto，vo，po……等bean 进行重复定义
 */
@Data
@Schema(description = "用户")
@EqualsAndHashCode(callSuper=true)
@SuperBuilder
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true,value = {"createDateTime","updateDateTime","password","accountNonExpired","accountNonLocked","credentialsNonExpired","enabled","createUserId","updateUserId"})
public class UserDefinition extends BaseDefinition {

    /**
     * redis key 获取缓存对象用该key就好
     */
    public static final String CACHE_KEY = "user";

    /**
     * 存放于 FileRelationalPo(t_file_relational)表中的字段名 头像  任何表的文件关联都方在 common-business 微服务的 t_file_relational 表
     */
    public static final String PROFILE_PHOTO_FIELD_NAME = "profilePhoto";

    @Schema(description = "头像id")
//    @Getter(AccessLevel.NONE)
//    @Setter(AccessLevel.NONE)
    @Transient //放这里其实是无效的，UserPo 不Override get方法也不会在表中创建字段，写在这里只是告诉你该字段不应该也不会出现在表中
    private Long profilePhotoId;

    @Transient //放这里其实是无效的，UserPo 不Override get方法也不会在表中创建字段，写在这里只是告诉你该字段不应该也不会出现在表中
    @Schema(description = "头像url")
//    @Getter(AccessLevel.NONE)
//    @Setter(AccessLevel.NONE)
    private String profilePhotoUrl;

    @Schema(description = "密码")
    private String password;

    @Schema(description = "用户登陆账号")
    private  String username;

    @Schema(description = "姓名")
    private String name;

    @Schema(description = "出生日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate birthday;

    private  boolean accountNonExpired;

    private  boolean accountNonLocked;

    private  boolean credentialsNonExpired;

    private  boolean enabled;

}
