package com.yue.chip.upms.infrastructure.dao.tenant.impl;

import com.yue.chip.core.YueChipPage;
import com.yue.chip.core.common.enums.State;
import com.yue.chip.core.persistence.curd.BaseDao;
import com.yue.chip.upms.infrastructure.dao.tenant.TenantDaoEx;
import com.yue.chip.upms.infrastructure.po.tenant.TenantPo;
import com.yue.chip.utils.TenantDatabaseUtil;
import jakarta.annotation.Resource;
import org.hibernate.jdbc.ReturningWork;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.util.StringUtils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

/**
 * @author Mr.Liu
 * @description: TODO
 * @date 2023/10/24 上午11:39
 */
public class TenantDaoImpl implements TenantDaoEx {

    @Resource
    private BaseDao<TenantPo> baseDao;

    @Value("${multiTenant.dataBase.upms}")
    private String upms;

    @Value("${multiTenant.dataBase.common}")
    private String common;

    @Value("${multiTenant.dataBase.security}")
    private String security;

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
        sb.append(" and t.isDefault = false");

        return (Page<TenantPo>) baseDao.findNavigator(pageable,sb.toString(),para);
    }

    @Override
    public void updateOtherDataBase(State state, Long tenantNumber) {
        Object result = baseDao.getSession().doReturningWork(
            new ReturningWork<Boolean>() {
                @Override
                public Boolean execute(java.sql.Connection connection) throws SQLException {
                    Statement stat =  connection.createStatement();
                    stat.execute("use `".concat(TenantDatabaseUtil.tenantDatabaseName(tenantNumber)).concat("`"));
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
                        stat.execute("use `".concat(TenantDatabaseUtil.tenantDatabaseName(tenantNumber)).concat("`"));
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
                    stat.execute("use `".concat(TenantDatabaseUtil.tenantDatabaseName(null)).concat("`"));
                    PreparedStatement prepareStatement =  connection.prepareStatement("select * from t_tenant where state = ?");
                    prepareStatement.setInt(1,state.getKey());
                    ResultSet resultSet = prepareStatement.executeQuery();
                    List<TenantPo> list = new ArrayList<>();
                    while (resultSet.next()) {
                        list.add(TenantPo.builder()
                                .manager(Objects.nonNull(resultSet.getObject("manager"))?resultSet.getString("manager"):null)
                                .id(resultSet.getLong("id"))
                                .isDefault(Objects.nonNull(resultSet.getObject("is_default"))?resultSet.getBoolean("is_default"):null)
                                .abbreviation(Objects.nonNull(resultSet.getObject("abbreviation"))?resultSet.getString("abbreviation"):null)
                                .domain(Objects.nonNull(resultSet.getObject("domain"))?resultSet.getString("domain"):null)
                                .phoneNumber(Objects.nonNull(resultSet.getObject("phone_number"))?resultSet.getString("phone_number"):null)
                                .name(Objects.nonNull(resultSet.getObject("name"))?resultSet.getString("name"):null)
                                .tenantNumber(Objects.nonNull(resultSet.getObject("tenant_number"))?resultSet.getLong("tenant_number"):null)
                                .build());
                    }
                    resultSet.close();
                    prepareStatement.close();
                    stat.close();
                    return list;
                }
            });
        return result;
    }

    @Override
    public Optional<TenantPo> findTenantByTenantNumber(Long tenantNumber) {
        TenantPo result = baseDao.getSession().doReturningWork(
                new ReturningWork<TenantPo>() {
                    @Override
                    public TenantPo execute(java.sql.Connection connection) throws SQLException {
                        Statement stat =  connection.createStatement();
                        stat.execute(" use `".concat(upms).concat("`"));
                        String sql = "select * from t_tenant where tenant_number = ?";
                        Object[] params = new Object[]{tenantNumber};
                        if (Objects.isNull(tenantNumber)) {
                            sql = "select * from t_tenant where tenant_number is null ";
                            params = new Object[]{};
                        }

                        PreparedStatement prepareStatement =  connection.prepareStatement(sql);
                        if (Objects.nonNull(tenantNumber)) {
                            prepareStatement.setLong(1,tenantNumber);
                        }
                        ResultSet resultSet = prepareStatement.executeQuery();
                        TenantPo tenantPo = null;
                        while (resultSet.next()) {
                            tenantPo = TenantPo.builder()
                                    .manager(Objects.nonNull(resultSet.getObject("manager"))?resultSet.getString("manager"):null)
                                    .id(resultSet.getLong("id"))
                                    .isDefault(Objects.nonNull(resultSet.getObject("is_default"))?resultSet.getBoolean("is_default"):null)
                                    .abbreviation(Objects.nonNull(resultSet.getObject("abbreviation"))?resultSet.getString("abbreviation"):null)
                                    .domain(Objects.nonNull(resultSet.getObject("domain"))?resultSet.getString("domain"):null)
                                    .phoneNumber(Objects.nonNull(resultSet.getObject("phone_number"))?resultSet.getString("phone_number"):null)
                                    .name(Objects.nonNull(resultSet.getObject("name"))?resultSet.getString("name"):null)
                                    .tenantNumber(Objects.nonNull(resultSet.getObject("tenant_number"))?resultSet.getLong("tenant_number"):null)
                                    .bigScreenName(Objects.nonNull(resultSet.getObject("big_screen_name"))?resultSet.getString("big_screen_name"):null)
                                    .build();
                        }
                        resultSet.close();
                        prepareStatement.close();
                        stat.close();
                        return tenantPo;
                    }
                });
        return Optional.ofNullable(result);
    }


}
