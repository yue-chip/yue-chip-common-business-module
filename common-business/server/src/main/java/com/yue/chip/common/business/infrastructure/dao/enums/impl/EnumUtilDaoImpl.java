package com.yue.chip.common.business.infrastructure.dao.enums.impl;

import com.ibm.icu.impl.coll.BOCSU;
import com.yue.chip.common.business.infrastructure.dao.enums.EnumUtilDaoEx;
import com.yue.chip.common.business.infrastructure.po.enmus.EnumUtilPo;
import com.yue.chip.core.persistence.curd.BaseDao;
import com.yue.chip.core.tenant.TenantConstant;
import jakarta.annotation.Resource;
import org.hibernate.jdbc.ReturningWork;
import org.springframework.jdbc.datasource.DataSourceUtils;

import javax.annotation.meta.When;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
        Object result =enumUtilPoBaseDao.getSession().doReturningWork(
            new ReturningWork<Boolean>() {
                @Override
                public Boolean execute(java.sql.Connection connection) throws SQLException {
                    Statement stat = connection.createStatement();
                    ResultSet resultSet = stat.executeQuery("select id from upms.t_tenant; ");
                    List<Long> tenantNumbers = new ArrayList<>();
                    while (resultSet.next()) {
                        Long tenantNumber = resultSet.getLong("id");
                        tenantNumbers.add(tenantNumber);
                    }
                    for (Long tenantNumber:tenantNumbers){
                        try {
                            stat.execute("use common".concat(TenantConstant.PREFIX_TENANT).concat(String.valueOf(tenantNumber)));
                            PreparedStatement delete = connection.prepareStatement("delete from t_enum_util where code =? and version = ?");
                            delete.setString(1, enumUtilPo.getCode());
                            delete.setString(2, enumUtilPo.getVersion());
                            delete.executeUpdate();

                            PreparedStatement insert = connection.prepareStatement("INSERT INTO t_enum_util(`code`,`value`,`version`) values (?,?,?)");
                            insert.setString(1, enumUtilPo.getCode());
                            insert.setString(2, enumUtilPo.getValue());
                            insert.setString(3, enumUtilPo.getVersion());
                            insert.executeUpdate();
                        }catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                    resultSet.close();
                    stat.close();
                    return true;
                }
            });
    }
}
