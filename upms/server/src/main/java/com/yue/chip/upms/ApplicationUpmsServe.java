package com.yue.chip.upms;

import com.yue.chip.annotation.HttpExchangeScan;
import com.yue.chip.annotation.HttpExchangeScans;
import com.yue.chip.core.persistence.BaseDaoFactoryBean;
import com.yue.chip.security.SecurityConfig;
import jakarta.annotation.Resource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.restart.RestartEndpoint;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication (exclude = {SecurityConfig.class})
@ComponentScan(basePackages = "com.yue.chip.**")
@EnableDiscoveryClient
@EnableCaching()
@EnableJpaRepositories(basePackages = {"com.yue.chip.upms.infrastructure.dao.**"}, repositoryFactoryBeanClass = BaseDaoFactoryBean.class)
@EntityScan({"com.yue.chip.upms.infrastructure.po.**"})
@EnableJpaAuditing
@EnableAsync
@EnableScheduling
@HttpExchangeScans({@HttpExchangeScan("com.yue.chip.**")})
public class ApplicationUpmsServe {

    @Resource
    RestartEndpoint restartEndpoint;

    public static void main ( String args[] ) throws Exception {
        /*
         * new SpringApplicationBuilder(Application.class)
         * .web(WebApplicationType.NONE) .run(args);
         */
        SpringApplication.run(ApplicationUpmsServe.class, args);
//        System.out.println(PasswordEncoderFactories.createDelegatingPasswordEncoder().encode(SecureUtil.md5("admin")));
    }

    @Scheduled(cron = "*/6 * * * * ?")
    public void sayHello() {
        restartEndpoint.doRestart();
    }
}