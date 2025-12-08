package com.yue.chip.upms.domain.service.upms;

import com.yue.chip.security.YueChipUserDetails;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

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

    public void userOrganizationals(@NotNull Long userId, List<Long> organizationalId);

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

    /**
     * 根据手机号码查寻用户信息
     * @param email Email
     * @return
     */
    YueChipUserDetails loadUserByEmail(String email);

    /**
     * 根据手机号码查寻用户信息
     * @param account   账号(用户名/手机号/email)
     * @return
     */
    YueChipUserDetails loadUserByAccount(String account);

    /**
     * 根据手机号码查寻用户信息
     * @param socialType   第三方类型
     * @param socialUid    第三方uid
     * @return
     */
    YueChipUserDetails loadUserBySocialTypeAndSocialUid(String socialType, String socialUid);

    /**
     * 根据手机号码查寻用户信息
     * @param socialType        第三方类型
     * @param socialUid         第三方uid
     * @param socialAcc         第三方账号
     * @param socialNickname    第三方昵称
     * @return
     */
    YueChipUserDetails saveUserSocial(String socialType, String socialUid, String socialAcc, String socialNickname);

}
