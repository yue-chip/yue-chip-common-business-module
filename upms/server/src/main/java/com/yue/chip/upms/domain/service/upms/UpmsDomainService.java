package com.yue.chip.upms.domain.service.upms;

import com.yue.chip.security.YueChipUserDetails;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * @author Mr.Liu
 * @date 2023/3/6 上午11:26
 */
public interface UpmsDomainService {

    /**
     * 绑定角色资源
     * @param roleId
     * @param resourcesIds
     */
    public void roleResources(@NotNull Long roleId, Long[] resourcesIds);

    /**
     * 绑定用户与组织机构的关联关系
     * @param userId
     * @param organizationalId
     */
    public void userOrganizational(@NotNull Long userId,Long organizationalId);

    /**
     * 判断资源名称是否存在
     *
     * @param name
     * @param parentId
     * @param id
     */
    public void checkResourcesNameIsExist(@NotBlank String name,@NotNull Long parentId, Long id);

    /**
     * 判断资源编码是否存在
     * @param code
     * @param id
     */
    public void checkResourcesCodeIsExist(@NotBlank String code,Long id);

    /**
     * 判断资源编码是否存在
     * @param url
     * @param id
     */
    public void checkResourcesUrlIsExist(@NotBlank String url,Long id);

    /**
     * 根据登陆用户名查寻用户信息
     * @param username
     * @return
     */
    YueChipUserDetails loadUserByUsername(String username);

    /**
     * 根据手机号码查寻用户信息
     * @param phoneNumber
     * @return
     */
    YueChipUserDetails loadUserByPhoneNumber(String phoneNumber);
}
