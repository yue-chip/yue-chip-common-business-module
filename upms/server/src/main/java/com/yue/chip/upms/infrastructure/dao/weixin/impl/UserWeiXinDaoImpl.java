package com.yue.chip.upms.infrastructure.dao.weixin.impl;

import com.yue.chip.core.persistence.curd.BaseDao;
import com.yue.chip.upms.infrastructure.dao.weixin.UserWeiXinDaoEx;
import com.yue.chip.upms.infrastructure.po.user.UserWeiXinPo;
import com.yue.chip.utils.AssertUtil;
import com.yue.chip.utils.TenantDatabaseUtil;
import jakarta.annotation.Resource;
import org.hibernate.jdbc.ReturningWork;

import java.sql.PreparedStatement;
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
                        stat.execute("use `".concat(TenantDatabaseUtil.tenantDatabaseName(tenantNumber)).concat("`"));
                        PreparedStatement prepareStatement =  connection.prepareStatement("select * from t_user_weixin where id = ?");
                        prepareStatement.setLong(1,id);
                        ResultSet resultSet = prepareStatement.executeQuery();
                        UserWeiXinPo userWeiXinPo = null;
                        while (resultSet.next()) {
                            userWeiXinPo = UserWeiXinPo.builder()
                                    .id(resultSet.getLong("id"))
                                    .tenantNumber(Objects.nonNull(resultSet.getObject("tenant_number"))?resultSet.getLong("tenant_number"):null)
                                    .openId(Objects.nonNull(resultSet.getObject("open_id"))?resultSet.getString("open_id"):null)
                                    .phoneNumber(Objects.nonNull(resultSet.getObject("phone_number"))?resultSet.getString("phone_number"):null)
                                    .build();
                        }
                        resultSet.close();
                        prepareStatement.close();
                        stat.close();
                        return Optional.ofNullable(userWeiXinPo);
                    }
                });
        return result;
    }
}
