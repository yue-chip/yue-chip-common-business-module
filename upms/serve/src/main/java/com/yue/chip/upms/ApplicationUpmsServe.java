package com.yue.chip.upms;

import com.yue.chip.core.persistence.BaseDaoFactoryBean;
import com.yue.chip.security.SecurityConfig;
import org.apache.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication ()
@ComponentScan(basePackages = "com.yue.chip.**",excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,classes = SecurityConfig.class)})
@EnableDiscoveryClient
@DubboComponentScan(basePackages = {"com.yue.chip.**"})
@EnableJpaRepositories(basePackages = {"com.yue.chip.upms.infrastructure.dao.**"}, repositoryFactoryBeanClass = BaseDaoFactoryBean.class)
@EntityScan({"com.yue.chip.upms.infrastructure.po.**"})
@EnableJpaAuditing
@EnableCaching
public class ApplicationUpmsServe {

    public static void main ( String args[] ) throws Exception {
        /*
         * new SpringApplicationBuilder(Application.class)
         * .web(WebApplicationType.NONE) .run(args);
         */
        SpringApplication.run(ApplicationUpmsServe.class, args);
    }

}