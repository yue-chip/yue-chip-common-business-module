package com.yue.chip.upms;

import com.yue.chip.core.Optional;
import com.yue.chip.upms.vo.OrganizationalExposeVo;
import com.yue.chip.upms.vo.UserExposeVo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

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

}
