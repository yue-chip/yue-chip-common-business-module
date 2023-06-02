package com.yue.chip.authentication;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.util.StringUtils;

import java.util.Objects;

/**
 * @author Mr.Liu
 * @date 2023/6/2 下午3:53
 */
public class YueChipAuthenticationProvider implements AuthenticationProvider {

    private UserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Object username = authentication.getPrincipal();
        Object password = authentication.getCredentials();
        if (Objects.isNull(username)  || !StringUtils.hasText(String.valueOf(username)) ) {
            throw new AuthenticationServiceException("登录账号不能为空");
        }
        if ( Objects.isNull(password) || !StringUtils.hasText(String.valueOf(password))) {
            throw new AuthenticationServiceException("密码不能为空");
        }
        UserDetails userDetails = userDetailsService.loadUserByUsername(String.valueOf(username));
        if (Objects.isNull(userDetails)){
            throw new AuthenticationServiceException("该账号不存在");
        }
        YueChipAuthenticationToken authResult = new YueChipAuthenticationToken(userDetails, userDetails.getAuthorities());
        return authResult;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (YueChipAuthenticationToken.class.isAssignableFrom(authentication));
    }

    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }
}
