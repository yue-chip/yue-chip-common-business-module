package com.yue.chip.upms.domain.service;

import com.yue.chip.core.health.HealthCheckUpService;
import com.yue.chip.core.persistence.curd.BaseDao;
import com.yue.chip.upms.infrastructure.po.user.UserPo;
import jakarta.annotation.Resource;
import lombok.extern.log4j.Log4j2;
import org.hibernate.jdbc.ReturningWork;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

@Component
@Log4j2
@Primary
public class HealthCheckUpServiceImpl implements HealthCheckUpService {

    @Resource
    private BaseDao<UserPo> baseDao;

    @Resource
    private RedisTemplate redisTemplate;

    @Override
    public Boolean checkHealth() {
        Boolean jdbcIsHealth = checkJdbcHealth();
        if (!jdbcIsHealth) {
            log.error("服务重启:jdbc检测不通过");
            return false;
        }
        Boolean redisIsHealth = checkRedisHealth();
        if (!redisIsHealth) {
            log.error("服务重启:redis检测不通过");
            return false;
        }
        return true;
    }

    private Boolean checkRedisHealth() {
        try {
            redisTemplate.opsForValue().set("1", "1", 1, TimeUnit.SECONDS);
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private Boolean checkJdbcHealth(){
        try {
            Boolean isExecute = baseDao.getSession().doReturningWork(new ReturningWork<Boolean>() {
                public Boolean execute(Connection connection) throws SQLException {
                    return connection.createStatement().execute("SELECT 1");
                }
            });
            return isExecute;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
