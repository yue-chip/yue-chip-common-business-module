package com.yue.chip.upms.domain.service.tenant.impl;

import com.yue.chip.core.tenant.TenantConstant;
import com.yue.chip.upms.domain.service.tenant.CreateSql;
import com.yue.chip.upms.domain.service.tenant.TenantService;
import com.yue.chip.upms.infrastructure.dao.tenant.TenantDao;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

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
    private DataSource dataSource;

    @Override
    public void createTenantDatabase(Long tenantNumber) {
        Connection connection = null;
        try {
            //创建数据库
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);
            Statement stat = connection.createStatement();
            stat.executeUpdate("create database common".concat(TenantConstant.PREFIX_TENANT).concat(String.valueOf(tenantNumber)));
            stat.executeUpdate("create database upms".concat(TenantConstant.PREFIX_TENANT).concat(String.valueOf(tenantNumber)));
            stat.executeUpdate("create database security".concat(TenantConstant.PREFIX_TENANT).concat(String.valueOf(tenantNumber)));
            stat.close();
            //创建表
            createTenantTable(connection,tenantNumber);
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);
        }

    }

    @Override
    public void initTenantData(@NotNull Long tenantNumber) {
        Connection connection = null;
        try {
            connection =dataSource.getConnection();
            Statement stat =  dataSource.getConnection().createStatement();
            connection.setAutoCommit(false);
            stat.execute("use upms".concat(TenantConstant.PREFIX_TENANT).concat(String.valueOf(tenantNumber)));
            stat.executeUpdate("inster t_tenant_state(`state`) values (1);");
            stat.close();
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);
        }

    }

    private void createTenantTable(@NotNull Connection connection,@NotNull Long tenantNumber) throws SQLException {
        Statement stat = connection.createStatement();
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
        CreateSql.execute(dataSource, connection, "upms", "t_resources", tenantNumber,new CreateSql.TempBean().setInsert(false));
        CreateSql.execute(dataSource, connection, "upms", "t_role", tenantNumber,new CreateSql.TempBean().setInsert(false));
        CreateSql.execute(dataSource, connection, "upms", "t_role_resources", tenantNumber,new CreateSql.TempBean().setInsert(false));
        CreateSql.execute(dataSource, connection, "upms", "t_tenant", tenantNumber,new CreateSql.TempBean().setInsert(false));
        CreateSql.execute(dataSource, connection, "upms", "t_tenant_state", tenantNumber,new CreateSql.TempBean().setInsert(false));
        CreateSql.execute(dataSource, connection, "upms", "t_user", tenantNumber,new CreateSql.TempBean().setInsert(false));
        CreateSql.execute(dataSource, connection, "upms", "t_user_role", tenantNumber,new CreateSql.TempBean().setInsert(false));
        CreateSql.execute(dataSource, connection, "upms", "t_user_weixin", tenantNumber,new CreateSql.TempBean().setInsert(false));
        CreateSql.execute(dataSource, connection, "security", "alarm_category", tenantNumber,new CreateSql.TempBean());
        CreateSql.execute(dataSource, connection, "security", "alarm_event", tenantNumber,new CreateSql.TempBean().setInsert(false));
        CreateSql.execute(dataSource, connection, "security", "alarm_handle", tenantNumber,new CreateSql.TempBean().setInsert(false));
        CreateSql.execute(dataSource, connection, "security", "car", tenantNumber,new CreateSql.TempBean().setInsert(false));
        CreateSql.execute(dataSource, connection, "security", "contacts", tenantNumber,new CreateSql.TempBean().setInsert(false));
        CreateSql.execute(dataSource, connection, "security", "device", tenantNumber,new CreateSql.TempBean().setInsert(false));
        CreateSql.execute(dataSource, connection, "security", "device_category", tenantNumber,new CreateSql.TempBean().setInsert(false));
        CreateSql.execute(dataSource, connection, "security", "device_product", tenantNumber,new CreateSql.TempBean().setInsert(false));
        CreateSql.execute(dataSource, connection, "security", "facility", tenantNumber,new CreateSql.TempBean().setInsert(false));
        CreateSql.execute(dataSource, connection, "security", "facility_category", tenantNumber,new CreateSql.TempBean().setInsert(false));
        CreateSql.execute(dataSource, connection, "security", "fire_station", tenantNumber,new CreateSql.TempBean().setInsert(false));
        CreateSql.execute(dataSource, connection, "security", "fire_station_category", tenantNumber,new CreateSql.TempBean().setInsert(false));
        CreateSql.execute(dataSource, connection, "security", "iot_data", tenantNumber,new CreateSql.TempBean().setInsert(false));
        CreateSql.execute(dataSource, connection, "security", "place", tenantNumber,new CreateSql.TempBean().setInsert(false));
        CreateSql.execute(dataSource, connection, "security", "user_message", tenantNumber,new CreateSql.TempBean().setInsert(false));

    }
}
