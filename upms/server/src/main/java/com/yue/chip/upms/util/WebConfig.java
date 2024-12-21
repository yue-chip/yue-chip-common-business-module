package com.yue.chip.upms.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
/**
 * @author jiacheng.liao on 2024/12/20
 */
@Configuration("tokenUpdate")
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private TokenUpdateInterceptor myInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(myInterceptor)
                .addPathPatterns("/**") // 添加拦截路径
                .excludePathPatterns("/login1")
                .excludePathPatterns("/login2")
                .excludePathPatterns("/login/out")
                .excludePathPatterns("/weixin/login")
                .excludePathPatterns("/weixin/login1")
                .excludePathPatterns("/weixin/login/out");
    }
}
