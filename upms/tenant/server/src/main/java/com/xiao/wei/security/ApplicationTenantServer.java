package com.xiao.wei.security;

import com.yue.chip.core.persistence.BaseDaoFactoryBean;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@Slf4j
@SpringBootApplication()
@ComponentScan(basePackages = {"com.yue.chip.**","com.xiao.wei.**"})
@EnableDiscoveryClient
@EnableCaching
@DubboComponentScan(basePackages = {"com.yue.chip.**","com.xiao.wei.**"})
@EnableJpaRepositories(basePackages = {"com.xiao.wei.security.infrastructure.dao.**"}, repositoryFactoryBeanClass = BaseDaoFactoryBean.class)
@EntityScan({"com.xiao.wei.security.infrastructure.po.**"})
@EnableJpaAuditing
@EnableAsync
@EnableScheduling
public class ApplicationTenantServer {

    public static void main ( String args[] ) throws Exception {
        /*
         * new SpringApplicationBuilder(Application.class)
         * .web(WebApplicationType.NONE) .run(args);
         */
        ApplicationContext applicationContext = SpringApplication.run(ApplicationTenantServer.class, args);
    }
}