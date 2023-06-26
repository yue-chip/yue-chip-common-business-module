package com.yue.chip.authentication;

import com.yue.chip.core.ResultData;
import com.yue.chip.core.common.enums.ResultDataState;
import com.yue.chip.security.AbstractSecurityConfig;
import com.yue.chip.security.AuthorizationIgnoreConfiguration;
import jakarta.annotation.Resource;
import jakarta.servlet.Servlet;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Mr.Liu
 * @date 2023/6/2 下午4:14
 */
@Configuration()
@EnableWebSecurity
@ConditionalOnClass({EnableWebSecurity.class, Servlet.class})
@AutoConfigureAfter({AuthorizationIgnoreConfiguration.class})
@EnableMethodSecurity(
        prePostEnabled = true,
        securedEnabled = true,
        jsr250Enabled = true
)
public class YueChipAuthenticationSecurityConfig extends AbstractSecurityConfig {
    private final HttpMessageConverter<Object> responseConverter = new MappingJackson2HttpMessageConverter();
    @Resource
    private UserDetailsService userDetailsService;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http = security(http);
        YueChipAuthenticationProvider yueChipAuthenticationProvider = new YueChipAuthenticationProvider();
        yueChipAuthenticationProvider.setUserDetailsService(userDetailsService);
        yueChipAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        YueChipUserPasswordFilter yueChipUserPasswordFilter = new YueChipUserPasswordFilter();
        yueChipUserPasswordFilter.setAuthenticationSuccessHandler((request, response, authentication) -> {
            YueChipAuthenticationToken yueChipAuthenticationToken = (YueChipAuthenticationToken) authentication;
            Map<String,String> map = new HashMap<>();
            map.put("token",yueChipAuthenticationToken.getToken());
            ResultData resultData = ResultData.builder().data(map).build();
            responseWrite(response,resultData);
        });
        yueChipUserPasswordFilter.setAuthenticationFailureHandler((request, response, exception) -> {
            ResultData resultData = ResultData.builder().status(ResultDataState.LOGIN_FAIL.getKey()).message(exception.getMessage()).build();
            responseWrite(response,resultData);
        });
        http.authenticationProvider(yueChipAuthenticationProvider)
                .addFilterBefore(yueChipUserPasswordFilter,AnonymousAuthenticationFilter.class);
        SecurityFilterChain securityFilterChain = http.build();
        AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
        yueChipUserPasswordFilter.setAuthenticationManager(authenticationManager);
        return securityFilterChain;
    }


    private void responseWrite(HttpServletResponse response, ResultData resultData) throws IOException {
        ServletServerHttpResponse httpResponse = new ServletServerHttpResponse(response);
        httpResponse.setStatusCode(HttpStatus.OK);
        responseConverter.write(resultData, MediaType.APPLICATION_JSON_UTF8,httpResponse);
    }
}
