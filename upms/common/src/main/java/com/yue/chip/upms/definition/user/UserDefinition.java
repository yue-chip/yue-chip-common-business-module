package com.yue.chip.upms.definition.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.yue.chip.core.BaseDefinition;
import com.yue.chip.core.common.enums.State;
import com.yue.chip.core.common.enums.UserType;
import com.yue.chip.upms.enums.Gender;
import com.yue.chip.upms.enums.IdCardType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Transient;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

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

    public static final String OTHER_PHOTO_FIELD_NAME = "otherPhoto";

    @Schema(description = "头像id")
    @Transient //放这里其实是无效的，UserPo 不Override get方法也不会在表中创建字段，写在这里只是告诉你该字段不应该也不会出现在表中
    private Long profilePhotoId;

    @Transient //放这里其实是无效的，UserPo 不Override get方法也不会在表中创建字段，写在这里只是告诉你该字段不应该也不会出现在表中
    @Schema(description = "头像url")
    private String profilePhotoUrl;

    @Schema(description = "其它照片id")
    @Transient //放这里其实是无效的，UserPo 不Override get方法也不会在表中创建字段，写在这里只是告诉你该字段不应该也不会出现在表中
    private Long otherPhotoId;

    @Transient //放这里其实是无效的，UserPo 不Override get方法也不会在表中创建字段，写在这里只是告诉你该字段不应该也不会出现在表中
    @Schema(description = "其它照片url")
    private String otherPhotoUrl;

    @Schema(description = "密码")
    private String password;

    @Schema(description = "用户登陆账号")
    private  String username;

    @Schema(description = "姓名")
    private String name;

    @Schema(description = "性别")
    private Gender gender;

    @Schema(description = "昵称")
    private String nickname;

    @Schema(description = "联系电话")
    private String phoneNumber;

    @Schema(description = "电子邮箱")
    private String email;

    @Schema(description = "证件类型")
    private IdCardType idCardType;

    @Schema(description = "证书编号")
    private String certificateNumber;

    @Schema(description = "身份证号码")
    private String identificationNumber;

    @Schema(description = "状态")
    private State state;

    @Schema(description = "是否接收短信通知")
    private Boolean isSms;

    @Schema(description = "是否接收紧急呼叫")
    private Boolean isCall;

    @Schema(description = "用户类型")
    private UserType userType;

    @Schema(description = "最后登录时间")
    private LocalDateTime lastLoginTime;

    @Schema(description = "租户id")
    @JsonIgnore
    private Long tenantNumber;

    @Schema(description = "极光用户推送标识")
    private String jiGuangUserKey;

    private  Boolean accountNonExpired;

    private  Boolean accountNonLocked;

    private  Boolean credentialsNonExpired;

    private  Boolean enabled;

}
