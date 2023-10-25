package com.yue.chip.common.business.infrastructure.dao.enums.impl;

import com.yue.chip.common.business.infrastructure.dao.enums.EnumUtilDaoEx;
import com.yue.chip.common.business.infrastructure.po.enmus.EnumUtilPo;
import com.yue.chip.core.persistence.curd.BaseDao;
import com.yue.chip.core.tenant.TenantConstant;
import jakarta.annotation.Resource;

import javax.annotation.meta.When;
import javax.sql.DataSource;
import java.sql.*;

/**
 * @author Mr.Liu
 * @description: TODO
 * @date 2023/10/25 下午7:28
 */
public class EnumUtilDaoImpl implements EnumUtilDaoEx {

    @Resource
    private BaseDao<EnumUtilPo> enumUtilPoBaseDao;

    @Resource
    private DataSource dataSource;

    @Override
    public void saveOtherTenantEnum(EnumUtilPo enumUtilPo) {
        Connection connection = null;
        try {
            //创建数据库
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);
            Statement stat = connection.createStatement();
            ResultSet resultSet = stat.executeQuery("select id from upms.t_tenant; ");
            while (resultSet.next()) {
                Long tenantNumber = resultSet.getLong("id");
                stat.execute("use common".concat(TenantConstant.PREFIX_TENANT).concat(String.valueOf(tenantNumber)));
                PreparedStatement delete = connection.prepareStatement("delete t_enum_util where code =? and version = ?");
                delete.setString(1,enumUtilPo.getCode());
                delete.setString(2,enumUtilPo.getVersion());
                delete.executeUpdate();
                delete.close();

                PreparedStatement insert = connection.prepareStatement("INSERT INTO t_enum_util(`code`,`value`,`version`)");
                insert.setString(1,enumUtilPo.getCode());
                insert.setString(2,enumUtilPo.getValue());
                insert.setString(3,enumUtilPo.getVersion());
                insert.executeUpdate();
                insert.close();
            }
            resultSet.close();
            stat.close();
            connection.commit();
            connection.close();
        } catch (SQLException e) {
            try {
                connection.rollback();
                connection.close();
            } catch (SQLException ex) {
            }
        }
    }
}
