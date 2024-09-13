package com.yue.chip.upms.vo;

import lombok.Data;
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

    private String address;

    private String organizationalName;

    private String girdName;

    private String nameAndPhone;

    private Long organizationalId;

    private String name;

    private String phone;



}
