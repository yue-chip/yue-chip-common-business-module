package com.yue.chip.upms.domain.service.login;

import jakarta.validation.constraints.NotBlank;

/**
 * @author Mr.Liu
 * @date 2023/5/25 下午2:05
 */
public interface LoginService {

    /**
     * pc 登录
     * @param username
     * @param password
     * @return
     */
    public String login(@NotBlank String username, @NotBlank String password);

    /**
     * 粤政易登录
     * @param username
     * @return
     */
    public String loginGrid(@NotBlank String username);

    /**
     *
     * @param phoneNumber
     * @param openId
     * @return
     */
    public String login1(@NotBlank String phoneNumber,@NotBlank String openId);


    /**
     * 退出登录
     */
    public void loginOut();
}
