package com.yue.chip.common.business.infrastructure.dao.enums.impl;

import com.yue.chip.common.business.infrastructure.dao.enums.EnumUtilDaoEx;
import com.yue.chip.common.business.infrastructure.po.enmus.EnumUtilPo;
import com.yue.chip.core.persistence.curd.BaseDao;
import com.yue.chip.core.tenant.TenantConstant;
import com.yue.chip.utils.HibernateSessionJdbcUtil;
import com.yue.chip.utils.TenantDatabaseUtil;
import jakarta.annotation.Resource;
import org.hibernate.jdbc.ReturningWork;
import org.springframework.beans.factory.annotation.Value;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Mr.Liu
 * @description: TODO
 * @date 2023/10/25 下午7:28
 */
public class EnumUtilDaoImpl implements EnumUtilDaoEx {

    @Resource
    private BaseDao<EnumUtilPo> enumUtilPoBaseDao;

    @Value("${multiTenant.dataBase.upms:upms}")
    private String upms;

    @Value("${multiTenant.dataBase.common:common}")
    private String common;

    @Value("${multiTenant.dataBase.security:security}")
    private String security;

    @Override
    public void saveOtherTenantEnum(EnumUtilPo enumUtilPo) {
        Object result =enumUtilPoBaseDao.getSession().doReturningWork(
            new ReturningWork<Boolean>() {
                @Override
                public Boolean execute(java.sql.Connection connection) throws SQLException {
                    Statement stat =null;
                    ResultSet resultSet =null;
                    try {
                        stat = connection.createStatement();
                        resultSet = stat.executeQuery("select tenant_number from "+upms+".t_tenant; ");
                        List<Long> tenantNumbers = new ArrayList<>();
                        while (resultSet.next()) {
                            Long tenantNumber = Objects.nonNull(resultSet.getObject("tenant_number"))?resultSet.getLong("tenant_number"):null;
                            tenantNumbers.add(tenantNumber);
                        }
                        for (Long tenantNumber:tenantNumbers){
                            PreparedStatement delete = null;
                            PreparedStatement insert = null;
                            try {
                                stat.execute("use `".concat(TenantDatabaseUtil.tenantDatabaseName(common,tenantNumber)).concat("`"));
                                delete = connection.prepareStatement("delete from t_enum_util where code =? and version = ?");
                                delete.setString(1, enumUtilPo.getCode());
                                delete.setString(2, enumUtilPo.getVersion());
                                delete.executeUpdate();

                                insert = connection.prepareStatement("INSERT INTO t_enum_util(`code`,`value`,`version`) values (?,?,?)");
                                insert.setString(1, enumUtilPo.getCode());
                                insert.setString(2, enumUtilPo.getValue());
                                insert.setString(3, enumUtilPo.getVersion());
                                insert.executeUpdate();
                            }catch (Exception ex) {

                            }finally {
                                HibernateSessionJdbcUtil.close(delete,insert);
                            }
                        }
                        return true;
                    }finally {
                        HibernateSessionJdbcUtil.close(resultSet,stat);
                    }

                }
            });
    }
}
