package com.yue.chip.upms.infrastructure.dao.user.impl;

import com.yue.chip.core.persistence.curd.BaseDao;
import com.yue.chip.exception.BusinessException;
import com.yue.chip.upms.infrastructure.dao.user.UserDao;
import com.yue.chip.upms.infrastructure.dao.user.UserDaoEx;
import com.yue.chip.upms.infrastructure.po.user.UserPo;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Optional;

/**
 * @author Mr.Liu
 * @date 2023/1/14 上午10:34
 * @description UserDaoImpl
 */
public class UserDaoImpl implements UserDaoEx {

    @Autowired
    public BaseDao<UserPo> baseDao;

    @Autowired
    private DataSource dataSource;

    @Override
    public Optional<UserPo> find(String username) {
        QueryRunner queryRunner = new QueryRunner(dataSource);
        try {
            UserPo userPo = queryRunner.query("select * from t_user where username = ? limit 0,1 ",new BeanHandler<UserPo>(UserPo.class),new Object[]{username});
            return Optional.ofNullable(userPo);
        } catch (SQLException e) {
            e.printStackTrace();
            BusinessException.throwException("查询用户失败");
        }
        return Optional.empty();
    }
}
