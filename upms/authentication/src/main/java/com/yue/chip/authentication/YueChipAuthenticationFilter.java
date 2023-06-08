package com.yue.chip.authentication;

import com.yue.chip.security.properties.AuthorizationIgnoreProperties;
import com.yue.chip.utils.YueChipRedisTokenStoreUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * @author Mr.Liu
 * @date 2023/6/8 上午9:42
 */
public class YueChipAuthenticationFilter extends GenericFilterBean implements InitializingBean {
    private AuthorizationIgnoreProperties authorizationIgnoreProperties;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String url = request.getRequestURI();
        String token = request.getHeader("token");
        String username = "";
        if (StringUtils.hasText(token)) {
            username = YueChipRedisTokenStoreUtil.getUsername(token);
        }
        if (StringUtils.hasText(username) || !authorizationIgnoreProperties.getIgnoreUrl().contains(url)) {
            YueChipAuthenticationToken yueChipAuthenticationToken = new YueChipAuthenticationToken(username);
            SecurityContextHolder.getContext().setAuthentication(yueChipAuthenticationToken);
            YueChipRedisTokenStoreUtil.renewal(username,null,token);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    public void setAuthorizationIgnoreProperties(AuthorizationIgnoreProperties authorizationIgnoreProperties) {
        this.authorizationIgnoreProperties = authorizationIgnoreProperties;
    }
}
