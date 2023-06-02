package com.yue.chip.authentication;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Collections;

/**
 * @author Mr.Liu
 * @date 2023/6/2 下午3:22
 */

public class YueChipAuthenticationToken extends AbstractAuthenticationToken {

    /**
     * 用户名
     */
    private Object principal;

    /**
     * 密码
     */
    private Object credentials;

    public YueChipAuthenticationToken(Object principal, Object credentials) {
        super(Collections.EMPTY_LIST);
        this.principal = principal;
        this.credentials = credentials;
        setAuthenticated(false);
    }
    public YueChipAuthenticationToken(Object principal,Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        super.setAuthenticated(true);
    }
    @Override
    public Object getCredentials() {
        return this.principal;
    }

    @Override
    public Object getPrincipal() {
        return this.credentials;
    }
}
