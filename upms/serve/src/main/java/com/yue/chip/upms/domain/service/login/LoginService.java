package com.yue.chip.upms.domain.service.login;

import jakarta.validation.constraints.NotBlank;

/**
 * @author Mr.Liu
 * @date 2023/5/25 下午2:05
 */
public interface LoginService {

    /**
     *
     * @param username
     * @param password
     * @return
     */
    public String login(@NotBlank String username,@NotBlank String password);
}
