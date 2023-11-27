package com.yue.chip.upms.infrastructure.dao.tenant.impl;

import com.yue.chip.core.YueChipPage;
import com.yue.chip.core.common.enums.State;
import com.yue.chip.core.persistence.curd.BaseDao;
import com.yue.chip.upms.infrastructure.dao.tenant.TenantDaoEx;
import com.yue.chip.upms.infrastructure.po.tenant.TenantPo;
import com.yue.chip.utils.TenantDatabaseUtil;
import jakarta.annotation.Resource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.hibernate.jdbc.ReturningWork;
import org.springframework.data.domain.Page;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author Mr.Liu
 * @description: TODO
 * @date 2023/10/24 上午11:39
 */
public class TenantDaoImpl implements TenantDaoEx {

    @Resource
    private BaseDao<TenantPo> baseDao;

    @Override
    public Page<TenantPo> list(String name, String manager, State state, String phoneNumber, YueChipPage pageable) {
        StringBuffer sb = new StringBuffer();
        sb.append(" select t from TenantPo t where 1=1 ");
        Map<String,Object> para = new HashMap<>();
        if (StringUtils.hasText(name)) {
            sb.append(" and t.name like :name ");
            para.put("name","%"+name+"%");
        }
        if (StringUtils.hasText(manager)) {
            sb.append(" and t.manager like :manager ");
            para.put("manager","%"+manager+"%");
        }
        if (StringUtils.hasText(phoneNumber)) {
            sb.append(" and t.phoneNumber like :phoneNumber ");
            para.put("phoneNumber","%"+phoneNumber+"%");
        }
        if (Objects.nonNull(state)) {
            sb.append(" and t.state = :state ");
            para.put("state",state);
        }
        return (Page<TenantPo>) baseDao.findNavigator(pageable,sb.toString(),para);
    }

    @Override
    public void updateOtherDataBase(State state, Long tenantNumber) {
        Object result = baseDao.getSession().doReturningWork(
            new ReturningWork<Boolean>() {
                @Override
                public Boolean execute(java.sql.Connection connection) throws SQLException {
                    Statement stat =  connection.createStatement();
                    String dataBaseName = TenantDatabaseUtil.tenantDatabaseName("upms",tenantNumber);
                    stat.execute("use ".concat(dataBaseName));
                    stat.executeUpdate("update t_tenant_state set state = "+state.getKey()+";");
                    stat.close();
                    return true;
                }
            });
    }

    @Override
    public void insertOtherDataBase(State state, Long tenantNumber) {
        Object result = baseDao.getSession().doReturningWork(
                new ReturningWork<Boolean>() {
                    @Override
                    public Boolean execute(java.sql.Connection connection) throws SQLException {
                        Statement stat =  connection.createStatement();
                        String dataBaseName = TenantDatabaseUtil.tenantDatabaseName("upms",tenantNumber);
                        stat.execute("use ".concat(dataBaseName));
                        stat.executeUpdate("insert t_tenant_state (state) values("+state.getKey()+")");
                        stat.close();
                        return true;
                    }
                });
    }

    @Override
    public List<TenantPo> findAllByState(State state) {
        List<TenantPo> result = baseDao.getSession().doReturningWork(
            new ReturningWork<List<TenantPo>>() {
                @Override
                public List<TenantPo> execute(java.sql.Connection connection) throws SQLException {
                    Statement stat =  connection.createStatement();
                    stat.execute("use upms");
                    QueryRunner queryRunner = new QueryRunner();
                    List<TenantPo> list = queryRunner.query(connection, "select * from t_tenant where state = ?  ", new BeanListHandler<TenantPo>(TenantPo.class), new Object[]{state.getKey()});
                    stat.close();
                    return list;
                }
            });
        return result;
    }


}
