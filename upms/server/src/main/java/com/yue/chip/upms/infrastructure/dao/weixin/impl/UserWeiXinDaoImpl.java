package com.yue.chip.upms.infrastructure.dao.weixin.impl;

import com.yue.chip.core.persistence.curd.BaseDao;
import com.yue.chip.upms.infrastructure.dao.weixin.UserWeiXinDaoEx;
import com.yue.chip.upms.infrastructure.po.user.UserPo;
import com.yue.chip.upms.infrastructure.po.user.UserWeiXinPo;
import com.yue.chip.utils.AssertUtil;
import com.yue.chip.utils.TenantDatabaseUtil;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.hibernate.jdbc.ReturningWork;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;
import java.util.Optional;

public class UserWeiXinDaoImpl implements UserWeiXinDaoEx {

    @Resource
    BaseDao<UserWeiXinPo> baseDao;

    @Override
    public Optional<UserWeiXinPo> findById(Long id, Long tenantNumber) {
        AssertUtil.nonNull(id,"用户id不能为空");
        Optional<UserWeiXinPo> result =baseDao.getSession().doReturningWork(
                new ReturningWork<Optional<UserWeiXinPo>>() {
                    @Override
                    public Optional<UserWeiXinPo> execute(java.sql.Connection connection) throws SQLException {
                        Statement stat =  connection.createStatement();
                        String dataBaseName = TenantDatabaseUtil.tenantDatabaseName("upms",tenantNumber);
                        stat.execute("use ".concat(dataBaseName));
                        QueryRunner queryRunner = new QueryRunner();
                        UserWeiXinPo userWeiXinPo = queryRunner.query(connection, "select * from t_user_weixin where id = ?  ", new ResultSetHandler<UserWeiXinPo>() {
                            @Override
                            public UserWeiXinPo handle(ResultSet rs) throws SQLException {
                                while (rs.next()) {
                                    return UserWeiXinPo.builder()
                                            .id(rs.getLong("id"))
                                            .tenantNumber(Objects.nonNull(rs.getObject("tenant_number"))?rs.getLong("tenant_number"):null)
                                            .openId(Objects.nonNull(rs.getObject("open_id"))?rs.getString("open_id"):null)
                                            .phoneNumber(Objects.nonNull(rs.getObject("phone_number"))?rs.getString("phone_number"):null)
                                            .build();
                                }
                                return null;
                            }
                        }, new Object[]{id});
                        stat.close();
                        return Optional.ofNullable(userWeiXinPo);
                    }
                });
        return result;
    }
}
