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
     * 粤商通登录
     *
     * @param phoneNumber 手机号
     * @return token
     */
    String loginYst(@NotBlank String phoneNumber);

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
