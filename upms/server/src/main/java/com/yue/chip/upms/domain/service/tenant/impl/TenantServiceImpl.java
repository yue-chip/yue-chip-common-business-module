package com.yue.chip.upms.domain.service.tenant.impl;

import cn.hutool.crypto.SecureUtil;
import com.yue.chip.core.common.enums.State;
import com.yue.chip.core.tenant.TenantConstant;
import com.yue.chip.exception.BusinessException;
import com.yue.chip.upms.domain.aggregates.Tenant;
import com.yue.chip.upms.domain.repository.tenant.TenantRepository;
import com.yue.chip.upms.domain.service.tenant.CreateSql;
import com.yue.chip.upms.domain.service.tenant.TenantService;
import com.yue.chip.upms.infrastructure.dao.tenant.TenantDao;
import com.yue.chip.upms.infrastructure.po.tenant.TenantPo;
import com.yue.chip.utils.AssertUtil;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.jdbc.ReturningWork;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;

/**
 * @author Mr.Liu
 * @description: TODO
 * @date 2023/10/24 下午2:11
 */
@Service
@Slf4j
public class TenantServiceImpl implements TenantService {

    @Resource
    private TenantDao tenantDao;

    @Resource
    private TenantRepository tenantRepository;

    @Resource
    private DataSource dataSource;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private RedisTemplate redisTemplate;

    @Override
    public void createTenantDatabase(Long tenantNumber) {
        Object result =tenantDao.getSession().doReturningWork(
            new ReturningWork<Boolean>() {
                @Override
                public Boolean execute(java.sql.Connection connection) throws SQLException {;
                    Statement stat = connection.createStatement();
                    stat.executeUpdate("create database common".concat(TenantConstant.PREFIX_TENANT).concat(String.valueOf(tenantNumber)));
                    stat.executeUpdate("create database upms".concat(TenantConstant.PREFIX_TENANT).concat(String.valueOf(tenantNumber)));
                    stat.executeUpdate("create database security".concat(TenantConstant.PREFIX_TENANT).concat(String.valueOf(tenantNumber)));
                    stat.close();
                    //创建表
                    createTenantTable(connection,tenantNumber);
                    return true;
                }
            }
        );
    }

    @Override
    public void initTenantData(@NotNull Long tenantNumber) {
        Object result =tenantDao.getSession().doReturningWork(
            new ReturningWork<Boolean>() {
                @Override
                public Boolean execute(java.sql.Connection connection) throws SQLException {
                    Statement stat =  connection.createStatement();
                    stat.execute("use upms".concat(TenantConstant.PREFIX_TENANT).concat(String.valueOf(tenantNumber)));
                    stat.executeUpdate("INSERT INTO  t_tenant_state(`state`) values (1);");
                    stat.executeUpdate("INSERT INTO  t_user(`name`,`password`,`username`,`tenant_number`,`state`,`is_call`,`is_sms`) values ('superadmin','"+passwordEncoder.encode(SecureUtil.md5("superadmin"))+"','superadmin',"+tenantNumber+",1,1,1);");
                    stat.executeUpdate("INSERT INTO  t_user(`name`,`password`,`username`,`tenant_number`,`state`,`is_call`,`is_sms`) values ('admin','"+passwordEncoder.encode(SecureUtil.md5("admin"))+"','admin',"+tenantNumber+",1,1,1);");
//                  stat.executeUpdate("INSERT INTO  t_resources(`code`,`is_default`,`name`,`parent_id`,`remark`,`scope`,`sort`,`state`,`type`,`url`) select `code`,`is_default`,`name`,`parent_id`,`remark`,`scope`,`sort`,`state`,`type`,`url` from upms.t_resources where code not in ( 'TENANT','MENU');");
                    stat.executeUpdate("INSERT INTO  t_role(`code`,`is_default`,`name`,`remark`,`state`) values ('superadmin',1,'超级管理员','',1);");
                    stat.executeUpdate("INSERT INTO  t_role(`code`,`is_default`,`name`,`remark`,`state`) values ('admin',1,'管理员','',1);");
                    stat.executeUpdate("INSERT INTO  t_role_resources(`resources_id`,`role_id`) select re.id, r.id from t_role r join t_resources re where r.code ='admin';");
                    stat.executeUpdate("INSERT INTO  t_role_resources(`resources_id`,`role_id`) select re.id, r.id from t_role r join t_resources re where r.code ='superadmin';");
                    stat.executeUpdate("INSERT INTO  t_user_role(`user_id`,`role_id`) select u.id, r.id from t_role r join t_user u where r.code ='admin' and u.username ='admin';");
                    stat.executeUpdate("INSERT INTO  t_user_role(`user_id`,`role_id`) select u.id, r.id from t_role r join t_user u where r.code ='superadmin' and u.username ='superadmin';");

                    stat.execute("use security".concat(TenantConstant.PREFIX_TENANT).concat(String.valueOf(tenantNumber)));
                    stat.executeUpdate("INSERT INTO  alarm_category(`gate`,`name`,`state`,`message_type`,`code`,`emergency_call`,`sms`,`push`) select 1, `name`,`state`,`message_type`,`code`,`emergency_call`,`sms`,`push` from security.alarm_category;");
                    stat.executeUpdate("INSERT INTO  device_category(`id`,`name`) select `id`,  `name` from security.device_category;");
                    stat.executeUpdate("INSERT INTO  device_product(`category_id`,`guide`,`name`,`token`,`master_key`,`access_platform`,`heartbeat_intervals`) select `category_id`,`guide`,`name`,`token`,`master_key`,`access_platform`,`heartbeat_intervals` from security.device_product;");
                    stat.executeUpdate("INSERT INTO  fire_station_category(`name`) select  `name` from security.fire_station_category;");

                    stat.execute("use common".concat(TenantConstant.PREFIX_TENANT).concat(String.valueOf(tenantNumber)));
                    stat.executeUpdate("INSERT INTO  t_enum_util(`code`,`value`,`version`) select `code`,`value`,`version` from common.t_enum_util;");
                    stat.close();
                    return true;
                }
            }
        );
    }

    @Override
    public void saveToRedis(Tenant tenant) {
        String domain = tenant.getDomain();
        if (StringUtils.hasText(domain)) {
           String[] domains = domain.split(",");
           for (String str : domains) {
               redisTemplate.opsForValue().set(str,tenant.getTenantNumber());
           }
        }
    }

    @Override
    @Scheduled(cron = "0 0/1 * * * ?")
    public void saveAllToRedis() {
        List<Tenant> list = tenantRepository.findAllTenant(State.NORMAL);
        list.forEach(tenant -> {
            saveToRedis(tenant);
        });
    }

    @Override
    public Boolean checkDomainIsExist(Long id, String domain) {
        AssertUtil.hasText(domain,"登录域不能为空");
        List<TenantPo> list = tenantDao.findAll();
        list.forEach(tenantPo -> {
            String domain1 = tenantPo.getDomain();
            if (StringUtils.hasText(domain1)) {
                String[] strs = domain1.split(",");
                for (String str : strs) {
                    if (Objects.equals(domain,str)) {
                        if (Objects.nonNull(id) && !Objects.equals(id,tenantPo.getId())) {
                            BusinessException.throwException("该登录域已存在");
                        }else {
                            BusinessException.throwException("该登录域已存在");
                        }
                    }
                }
            }
        });
        return false;
    }

    private void createTenantTable(@NotNull Connection connection,@NotNull Long tenantNumber) throws SQLException {
        CreateSql.execute(dataSource, connection, "common", "t_enum_util", tenantNumber,new CreateSql.TempBean().setInsert(false));
        CreateSql.execute(dataSource, connection, "common", "t_file", tenantNumber,new CreateSql.TempBean().setInsert(false));
        CreateSql.execute(dataSource, connection, "common", "t_file_relational", tenantNumber,new CreateSql.TempBean().setInsert(false));
        CreateSql.execute(dataSource, connection, "upms", "branch_table", tenantNumber,new CreateSql.TempBean().setInsert(false));
        CreateSql.execute(dataSource, connection, "upms", "distributed_lock", tenantNumber,new CreateSql.TempBean().setInsert(false));
        CreateSql.execute(dataSource, connection, "upms", "global_table", tenantNumber,new CreateSql.TempBean().setInsert(false));
        CreateSql.execute(dataSource, connection, "upms", "lock_table", tenantNumber,new CreateSql.TempBean().setInsert(false));
        CreateSql.execute(dataSource, connection, "upms", "t_organizational", tenantNumber,new CreateSql.TempBean().setInsert(false));
        CreateSql.execute(dataSource, connection, "upms", "t_organizational_group", tenantNumber,new CreateSql.TempBean().setInsert(false));
        CreateSql.execute(dataSource, connection, "upms", "t_organizational_user", tenantNumber,new CreateSql.TempBean().setInsert(false));
        CreateSql.execute(dataSource, connection, "upms", "t_organizational_user_weixin", tenantNumber,new CreateSql.TempBean().setInsert(false));
        CreateSql.execute(dataSource, connection, "upms", "t_resources", tenantNumber,new CreateSql.TempBean());
        CreateSql.execute(dataSource, connection, "upms", "t_role", tenantNumber,new CreateSql.TempBean().setInsert(false));
        CreateSql.execute(dataSource, connection, "upms", "t_role_resources", tenantNumber,new CreateSql.TempBean().setInsert(false));
        CreateSql.execute(dataSource, connection, "upms", "t_tenant", tenantNumber,new CreateSql.TempBean().setInsert(false));
        CreateSql.execute(dataSource, connection, "upms", "t_tenant_state", tenantNumber,new CreateSql.TempBean().setInsert(false));
        CreateSql.execute(dataSource, connection, "upms", "t_user", tenantNumber,new CreateSql.TempBean().setInsert(false));
        CreateSql.execute(dataSource, connection, "upms", "t_user_role", tenantNumber,new CreateSql.TempBean().setInsert(false));
        CreateSql.execute(dataSource, connection, "upms", "t_user_weixin", tenantNumber,new CreateSql.TempBean().setInsert(false));
        CreateSql.execute(dataSource, connection, "upms", "t_grid", tenantNumber,new CreateSql.TempBean().setInsert(false));
        CreateSql.execute(dataSource, connection, "security", "alarm_category", tenantNumber,new CreateSql.TempBean().setInsert(false));
        CreateSql.execute(dataSource, connection, "security", "alarm_event", tenantNumber,new CreateSql.TempBean().setInsert(false));
        CreateSql.execute(dataSource, connection, "security", "alarm_handle", tenantNumber,new CreateSql.TempBean().setInsert(false));
        CreateSql.execute(dataSource, connection, "security", "car", tenantNumber,new CreateSql.TempBean().setInsert(false));
        CreateSql.execute(dataSource, connection, "security", "contacts", tenantNumber,new CreateSql.TempBean().setInsert(false));
        CreateSql.execute(dataSource, connection, "security", "device", tenantNumber,new CreateSql.TempBean().setInsert(false));
        CreateSql.execute(dataSource, connection, "security", "device_category", tenantNumber,new CreateSql.TempBean().setInsert(false));
        CreateSql.execute(dataSource, connection, "security", "device_product", tenantNumber,new CreateSql.TempBean().setInsert(false));
        CreateSql.execute(dataSource, connection, "security", "drill", tenantNumber,new CreateSql.TempBean().setInsert(false));
        CreateSql.execute(dataSource, connection, "security", "evaluation", tenantNumber,new CreateSql.TempBean().setInsert(false));
        CreateSql.execute(dataSource, connection, "security", "facility", tenantNumber,new CreateSql.TempBean().setInsert(false));
        CreateSql.execute(dataSource, connection, "security", "facility_category", tenantNumber,new CreateSql.TempBean().setInsert(false));
        CreateSql.execute(dataSource, connection, "security", "fire_patrol", tenantNumber,new CreateSql.TempBean().setInsert(false));
        CreateSql.execute(dataSource, connection, "security", "fire_station", tenantNumber,new CreateSql.TempBean().setInsert(false));
        CreateSql.execute(dataSource, connection, "security", "fire_station_category", tenantNumber,new CreateSql.TempBean().setInsert(false));
        CreateSql.execute(dataSource, connection, "security", "intermediary", tenantNumber,new CreateSql.TempBean().setInsert(false));
        CreateSql.execute(dataSource, connection, "security", "iot_data", tenantNumber,new CreateSql.TempBean().setInsert(false));
        CreateSql.execute(dataSource, connection, "security", "maintenance", tenantNumber,new CreateSql.TempBean().setInsert(false));
        CreateSql.execute(dataSource, connection, "security", "patrol_event", tenantNumber,new CreateSql.TempBean().setInsert(false));
        CreateSql.execute(dataSource, connection, "security", "patrol_place", tenantNumber,new CreateSql.TempBean().setInsert(false));
        CreateSql.execute(dataSource, connection, "security", "place", tenantNumber,new CreateSql.TempBean().setInsert(false));
        CreateSql.execute(dataSource, connection, "security", "store", tenantNumber,new CreateSql.TempBean().setInsert(false));
        CreateSql.execute(dataSource, connection, "security", "train", tenantNumber,new CreateSql.TempBean().setInsert(false));
        CreateSql.execute(dataSource, connection, "security", "user_message", tenantNumber,new CreateSql.TempBean().setInsert(false));
    }
}





