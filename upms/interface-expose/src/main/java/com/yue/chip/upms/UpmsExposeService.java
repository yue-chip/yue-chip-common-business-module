package com.yue.chip.upms;

import com.yue.chip.core.Optional;
import com.yue.chip.core.PageSerializable;
import com.yue.chip.core.YueChipPage;
import com.yue.chip.grid.vo.GridExposeVo;
import com.yue.chip.grid.vo.GridVo;
import com.yue.chip.upms.vo.OrganizationalExposeVo;
import com.yue.chip.upms.vo.OrganizationalUserExposeVo;
import com.yue.chip.upms.vo.UserExposeVo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.Set;

/**
 * @author xianming.chen
 * @description: TODO
 * @date 2023-10-28
 */
public interface UpmsExposeService {

    /**
     * 根据ids查询所有用户
     * @param userIds
     * @return
     */
    List<UserExposeVo> findUserAllByIdIn(@Size(min = 1) List<Long> userIds);

    Optional<UserExposeVo> findUserById(@NotNull Long userId);

    /**
     * 根据机构id查询用户
     * @param organizationalIds
     * @return
     */
    List<UserExposeVo> findUserAllByOrganizationalId(@Size(min = 1)List<Long> organizationalIds);

    /**
     * 根据机构id分页查询用户
     *
     * @param organizationalIds
     * @param yueChipPage
     * @return
     */
    PageSerializable<UserExposeVo> findUserAllByOrganizationalId(@Size(min = 1)List<Long> organizationalIds, String name, @NotNull YueChipPage yueChipPage);

    /**
     * 根据用户id和租户编码查寻用户
     * @param id
     * @param tenantNumber
     * @return
     */
    Optional<UserExposeVo> findByIdAndTenantNumber(@NotNull Long id,Long tenantNumber);

    /**
     * 根据网格id和租户编码查寻用户
     * @param id
     * @param tenantNumber
     * @return
     */
    Optional<UserExposeVo> findByGridIdAndTenantNumber(@NotNull Long id,Long tenantNumber);


    /**
     * 根据id查询机构
     *
     * @param id
     * @return
     */
    public Optional<OrganizationalExposeVo> findOrganizationalById(@NotNull Long id);

    /**
     * 查询机构id数组信息
     * @param ids
     * @return
     */
    public List<OrganizationalExposeVo> findOrganizationalByIdList(@Size(min = 1)Set<Long> ids);

    public List<OrganizationalExposeVo> findOrganizationalAll();

    /**
     * 获取所有节点数据
     * @return
     */
    public List<OrganizationalExposeVo> findOrganizationalAllChildrenByOrganizationalId(@NotNull Long organizationalId);

    /**
     * 根据用户获取所在机构和所有子节点
     * @param userId
     * @return
     */
    public List<OrganizationalExposeVo> findOrganizationalAllChildrenByUserId(@NotNull Long userId);

    /**
     * 根据当前用户获取所在机构和所有子节点
     * @return
     */
    public List<OrganizationalExposeVo> findOrganizationalAllChildrenByCurrentUserId();


    public Set<Long> findOrganizationalAllChildrenOrganizationalIds(@NotNull Long parentId);

    List<UserExposeVo> findUserAllByNameOrPhoneNumber(@NotBlank String name, @NotBlank String phoneNumber);

    public List<OrganizationalExposeVo> findOrganizationalChildrenOrganizationalIds(@NotNull Long parentId);

    public PageSerializable<OrganizationalExposeVo> organizationalExposeVoPage(@Size(min = 1)List<Long> organizationalList, @NotNull YueChipPage yueChipPage);

    /**
     * 获取机构下的网格
     * @param organizationalId
     * @return
     */
    public List<GridExposeVo> findByOrganizationalId(@NotNull Long organizationalId);

    /**
     * 获取机构下的网格树形结构
     * @param organizationalId
     * @return
     */
    List<GridVo> findTreeByOrganizationalId(@NotNull Long organizationalId);

    /**
     * 根据网格id查询网格
     * @param gridId
     * @return
     */
    List<GridExposeVo> findByGridId(@NotNull Set<Long> gridId);

    /**
     * 根据机构id和用户id查询机构下的消防管理员
     * @param organizationalId
     * @param userId
     * @return
     */
    List<OrganizationalUserExposeVo> findUserAllByOrganizationalIdAndUserIdIn(@NotNull Long organizationalId,@Size(min = 1) Set<Long> userId);

    /**
     * 根据用户id查询机构
     * @param userId
     * @return
     */
    List<OrganizationalUserExposeVo> findUserAllByUserIdIn(@Size(min = 1)Set<Long> userId);

}
