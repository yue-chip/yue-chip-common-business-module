package com.yue.chip.common.business.domain.service;

import com.yue.chip.core.health.HealthCheckUpService;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

@Service
public class HealthCheckUpServiceImpl implements HealthCheckUpService {

    @Resource
    @Lazy
    private DataSource dataSource;

    @Resource
    @Lazy
    private RedisTemplate redisTemplate;

    @Override
    public Boolean checkHealth() {
        Boolean jdbcIsHealth = checkJdbcHealth();
        if (!jdbcIsHealth) {
            return false;
        }
        Boolean redisIsHealth = checkRedisHealth();
        if (!redisIsHealth) {
            return false;
        }
        return true;
    }

    private Boolean checkRedisHealth() {
        try {
            redisTemplate.opsForValue().set("1","1",1, TimeUnit.SECONDS);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private Boolean checkJdbcHealth (){
        try {
            Boolean jdbcIsHealth = true;
            Connection connection = dataSource.getConnection();

            if (connection.isClosed()) {
                jdbcIsHealth = false;
            }
            Boolean isExecute = connection.createStatement().execute("SELECT 1");
            if (!isExecute) {
                jdbcIsHealth = false;
            }
            DataSourceUtils.releaseConnection(connection,dataSource);
            if (!jdbcIsHealth) {
                return jdbcIsHealth;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
