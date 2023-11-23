package com.yue.chip.upms;

import com.yue.chip.core.Optional;
import com.yue.chip.core.YueChipPage;
import com.yue.chip.upms.vo.GridExposeVo;
import com.yue.chip.upms.vo.OrganizationalExposeVo;
import com.yue.chip.upms.vo.OrganizationalUserExposeVo;
import com.yue.chip.upms.vo.UserExposeVo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;

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
    List<UserExposeVo> findUserAllByIdIn(List<Long> userIds);

    /**
     * 根据机构id查询用户
     * @param organizationalIds
     * @return
     */
    List<UserExposeVo> findUserAllByOrganizationalId(List<Long> organizationalIds);

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
    public List<OrganizationalExposeVo> findOrganizationalByIdList(Set<Long> ids);

    public List<OrganizationalExposeVo> findOrganizationalAll();

    /**
     * 获取所有节点数据
     * @return
     */
    public List<OrganizationalExposeVo> findOrganizationalAllChildrenByOrganizationalId(Long organizationalId);

    /**
     * 根据用户获取所在机构和所有子节点
     * @param userId
     * @return
     */
    public List<OrganizationalExposeVo> findOrganizationalAllChildrenByUserId(Long userId);

    /**
     * 根据当前用户获取所在机构和所有子节点
     * @return
     */
    public List<OrganizationalExposeVo> findOrganizationalAllChildrenByCurrentUserId();


    public Set<Long> findOrganizationalAllChildrenOrganizationalIds(Long parentId);

    List<UserExposeVo> findUserAllByNameOrPhoneNumber(@NotBlank String name, @NotBlank String phoneNumber);

    public List<OrganizationalExposeVo> findOrganizationalChildrenOrganizationalIds(Long parentId);

    public Page<OrganizationalExposeVo> organizationalExposeVoPage(List<Long> organizationalList, YueChipPage yueChipPage);

    /**
     * 获取机构下的网格
     * @param organizationalId
     * @return
     */
    public List<GridExposeVo> findByOrganizationalId(@NotNull Long organizationalId);

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
    List<OrganizationalUserExposeVo> findUserAllByOrganizationalIdAndUserIdIn(Long organizationalId, Set<Long> userId);

    /**
     * 根据网格名称模糊查询网格信息
     * @param name
     * @return
     */
    List<GridExposeVo> findGridByName(String name);

}
