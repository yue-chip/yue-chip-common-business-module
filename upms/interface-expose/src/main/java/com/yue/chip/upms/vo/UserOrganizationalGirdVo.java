package com.yue.chip.upms.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * @author jiacheng.liao on 2024/7/23
 */
@Data
@SuperBuilder
@NoArgsConstructor
public class UserOrganizationalGirdVo implements Serializable {

    private String storeName;

    private String storeType;

    private String place;

    private String address;

    private String organizationalName;

    private String girdName;

    private String nameAndPhone;

    private String fireWarden;

    private String contact;

    private String contactManager;

    private Long organizationalId;

    private String name;

    private String phone;

}
