package com.yue.chip.authentication;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Mr.Liu
 * @date 2023/6/2 下午3:41
 */
public class YueChipUserPasswordFilter extends AbstractAuthenticationProcessingFilter {

    private static final String USERNAME = "username";

    private static final String PASSWORD = "password";

    private static final AntPathRequestMatcher DEFAULT_ANT_PATH_REQUEST_MATCHER = new AntPathRequestMatcher("/login");

    public YueChipUserPasswordFilter() {
        super(DEFAULT_ANT_PATH_REQUEST_MATCHER);
    }

    protected YueChipUserPasswordFilter(String defaultFilterProcessesUrl) {
        super(defaultFilterProcessesUrl);
    }

    protected YueChipUserPasswordFilter(RequestMatcher requiresAuthenticationRequestMatcher) {
        super(requiresAuthenticationRequestMatcher);
    }

    protected YueChipUserPasswordFilter(String defaultFilterProcessesUrl, AuthenticationManager authenticationManager) {
        super(defaultFilterProcessesUrl, authenticationManager);
    }

    protected YueChipUserPasswordFilter(RequestMatcher requiresAuthenticationRequestMatcher, AuthenticationManager authenticationManager) {
        super(requiresAuthenticationRequestMatcher, authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        String username = request.getParameter(USERNAME);
        String password = request.getParameter(PASSWORD);
        YueChipAuthenticationToken authRequest = new YueChipAuthenticationToken(password,username);
        authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
        return this.getAuthenticationManager().authenticate(authRequest);
    }

//    @Override
//    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
//        String username = request.getParameter(USERNAME);
//        String password = request.getParameter(PASSWORD);
//        YueChipAuthenticationToken authRequest = new YueChipAuthenticationToken(password,username);
//        authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
//        return this.getAuthenticationManager().authenticate(authRequest);
//    }
}
