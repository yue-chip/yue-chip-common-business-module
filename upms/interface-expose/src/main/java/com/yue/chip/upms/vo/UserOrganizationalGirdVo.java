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

    private String nameAndPhone;

    private String organizationalName;

    private Long organizationalId;

    private String girdName;

}
