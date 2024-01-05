package com.yue.chip.common.business;

import com.yue.chip.core.persistence.BaseDaoFactoryBean;
import org.apache.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author Mr.Liu
 * @date 2023/6/13 下午4:05
 */
@SpringBootApplication(scanBasePackages="com.yue.chip.**",exclude = {JdbcTemplateAutoConfiguration.class})
@EnableDiscoveryClient
@DubboComponentScan(basePackages = {"com.yue.chip.**"})
@EnableJpaRepositories(basePackages = {"com.yue.chip.common.business.infrastructure.dao.**"}, repositoryFactoryBeanClass = BaseDaoFactoryBean.class)
@EntityScan({"com.yue.chip.common.business.infrastructure.po.**"})
@EnableJpaAuditing
@EnableCaching
@EnableAsync
public class ApplicationCommonBusinessServe {

    public static void main ( String args[] ) throws Exception {
        /*
         * new SpringApplicationBuilder(Application.class)
         * .web(WebApplicationType.NONE) .run(args);
         */
        SpringApplication.run(ApplicationCommonBusinessServe.class, args);
    }
}
