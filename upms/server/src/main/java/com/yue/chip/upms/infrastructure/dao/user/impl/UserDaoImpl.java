package com.yue.chip.upms.infrastructure.dao.user.impl;

import com.yue.chip.core.YueChipPage;
import com.yue.chip.core.common.enums.State;
import com.yue.chip.core.common.enums.UserType;
import com.yue.chip.core.persistence.curd.BaseDao;
import com.yue.chip.upms.infrastructure.dao.user.UserDaoEx;
import com.yue.chip.upms.infrastructure.po.user.UserPo;
import com.yue.chip.utils.AssertUtil;
import com.yue.chip.utils.HibernateSessionJdbcUtil;
import com.yue.chip.utils.TenantDatabaseUtil;
import jakarta.validation.constraints.NotNull;
import org.hibernate.jdbc.ReturningWork;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

/**
 * @author Mr.Liu
 * @date 2023/1/14 上午10:34
 * @description UserDaoImpl
 */
public class UserDaoImpl implements UserDaoEx {

    @Autowired
    public BaseDao<UserPo> baseDao;

//    @Override
//    public Optional<UserPo> find(String username) {
//        QueryRunner queryRunner = new QueryRunner(dataSource);
//        try {
//            UserPo userPo = queryRunner.query("select * from t_user where username = ? limit 0,1 ", new ResultSetHandler<UserPo>() {
//                @Override
//                public UserPo handle(ResultSet rs) throws SQLException {
//                    if (rs.next()) {
//                        UserPo userPo = UserPo.builder()
//                                .username(rs.getString("username"))
//                                .id(rs.getLong("id"))
//                                .name(rs.getString("name"))
//                                .password(rs.getString("password"))
////                                .version(rs.getLong("version"))
//                                .tenantId(rs.getLong("tenant_id"))
//                                .build();
//                        return userPo;
//                    }
//                    return null;
//                }
//            }, new Object[]{username});
////            UserPo userPo = queryRunner.query("select * from t_user where username = ? limit 0,1 ",new BeanHandler<UserPo>(UserPo.class),new Object[]{username});
//            return Optional.ofNullable(userPo);
//        } catch (SQLException e) {
//            e.printStackTrace();
//            BusinessException.throwException("查询用户失败");
//        }
//        return Optional.empty();
//    }

    @Override
    public Page<UserPo> find(String name, String username, Pageable pageable) {
        StringBuffer sb = new StringBuffer();
        sb.append(" select u from UserPo u where 1=1 ");
        Map<String,Object> para = new HashMap<>();
        if (StringUtils.hasText(name)) {
            sb.append(" and u.name like :name ");
            para.put("name","%"+name+"%");
        }
        if (StringUtils.hasText(username)) {
            sb.append(" and u.username like :username ");
            para.put("name","%"+username+"%");
        }
        sb.append(" and u.username <> 'superadmin' ");
        return (Page<UserPo>) baseDao.findNavigator(pageable,sb.toString(),para);
    }

    @Override
    public Page<UserPo> find(List<Long> ids, String name, Pageable pageable) {
        StringBuffer sb = new StringBuffer();
        sb.append(" select u from UserPo u where 1=1 ");
        Map<String,Object> para = new HashMap<>();
        if (!CollectionUtils.isEmpty(ids)) {
            sb.append(" and u.id in :ids ");
            para.put("ids", ids);
        }
        if (StringUtils.hasText(name)) {
            sb.append(" and u.name like :name ");
            para.put("name","%"+name+"%");
        }
        sb.append(" and u.username <> 'superadmin' ");
        return (Page<UserPo>) baseDao.findNavigator(pageable,sb.toString(),para);
    }

    @Override
    public Page<UserPo> find(String name, String nickname, String username, String phoneNumber, String email, State state, String nameLike, UserType userType, YueChipPage yueChipPage) {
        StringBuffer sb = new StringBuffer();
        sb.append(" select u from UserPo u where 1=1 ");
        Map<String,Object> para = new HashMap<>();
        if (Objects.nonNull(state)) {
            sb.append(" and u.state = :state ");
            para.put("state", state);
        }
        if (Objects.nonNull(userType)) {
            sb.append(" and u.userType = :userType ");
            para.put("userType", userType);
        }
        if (StringUtils.hasText(name)) {
            sb.append(" and u.name like :name ");
            para.put("name","%"+name+"%");
        }
        if (StringUtils.hasText(nickname)) {
            sb.append(" and u.nickname like :nickname ");
            para.put("nickname","%"+nickname+"%");
        }
        if (StringUtils.hasText(username)) {
            sb.append(" and u.username like :username ");
            para.put("username","%"+username+"%");
        }
        if (StringUtils.hasText(phoneNumber)) {
            sb.append(" and u.phoneNumber like :phoneNumber ");
            para.put("phoneNumber","%"+phoneNumber+"%");
        }
        if (StringUtils.hasText(email)) {
            sb.append(" and u.email like :email ");
            para.put("email","%"+email+"%");
        }
        if (StringUtils.hasText(nameLike)) {
            String keyword = "%" + nameLike + "%";
            sb.append(" and (u.name like :keyword or u.nickname like :keyword or u.username like :keyword or u.email like :keyword or u.phoneNumber like :keyword)");
            para.put("keyword", keyword);
        }
        sb.append(" and u.username <> 'superadmin' ");
        return (Page<UserPo>) baseDao.findNavigator(yueChipPage,sb.toString(),para);
    }
    @Override
    public Page<UserPo> find(Long id, String phoneNumber, State state, Pageable pageable) {
        StringBuffer sb = new StringBuffer();
        sb.append(" select u from UserPo u where 1=1 ");
        Map<String,Object> para = new HashMap<>();
        if (Objects.nonNull(id)) {
            sb.append(" and u.id = :id ");
            para.put("id", id);
        }
        if (StringUtils.hasText(phoneNumber)) {
            sb.append(" and u.phoneNumber like :phoneNumber ");
            para.put("phoneNumber","%"+phoneNumber+"%");
        }
        if (Objects.nonNull(state)) {
            sb.append(" and u.state = :state ");
            para.put("state", state);
        }
        sb.append(" and u.username <> 'superadmin' ");
        return (Page<UserPo>) baseDao.findNavigator(pageable,sb.toString(),para);
    }

    @Override
    public List<UserPo> findByRoleId(Long roleId) {
        if (Objects.isNull(roleId)) {
            return Collections.EMPTY_LIST;
        }
        StringBuffer sb = new StringBuffer();
        sb.append(" select u from UserPo u join UserRolePo ur on u.id = ur.userId where ur.roleId = :roleId");
        Map<String,Object> para = new HashMap<>();
        para.put("roleId",roleId);
        return (List<UserPo>) baseDao.findAll(sb.toString(),para);
    }

    @Override
    public List<UserPo> findUserByOrganizationalId(Long organizationalId, @NotNull State state) {
        if (Objects.isNull(organizationalId)) {
            return Collections.EMPTY_LIST;
        }
        StringBuffer sb = new StringBuffer();
        sb.append(" select u from UserPo u join OrganizationalUserPo ou on u.id = ou.userId join OrganizationalPo o on ou.organizationalId = o.id" +
                "  where ou.organizationalId = :organizationalId and o.state = :state ");
        Map<String,Object> para = new HashMap<>();
        para.put("organizationalId",organizationalId);
        para.put("state",state);
        return (List<UserPo>) baseDao.findAll(sb.toString(),para);
    }

    @Override
    public List<UserPo> findUserByOrganizationalId(List<Long> organizationalIds, State state) {
        if (Objects.isNull(organizationalIds) || organizationalIds.size() ==0) {
            return Collections.EMPTY_LIST;
        }
        StringBuffer sb = new StringBuffer();
        sb.append(" select u from UserPo u join OrganizationalUserPo ou on u.id = ou.userId join OrganizationalPo o on ou.organizationalId = o.id" +
                "  where ou.organizationalId in :organizationalId and o.state = :state ");
        Map<String,Object> para = new HashMap<>();
        para.put("organizationalId",organizationalIds);
        para.put("state",state);
        List<UserPo> list = (List<UserPo>) baseDao.findAll(sb.toString(),para);
        return list;
    }

    @Override
    public List<UserPo> findUserByGridId(Long gridId) {
        if (Objects.isNull(gridId)) {
            return Collections.EMPTY_LIST;
        }
        StringBuffer sb = new StringBuffer();
        sb.append(" select u from UserPo u join GridPo g on u.id = g.userId " +
                "  where g.id in :gridId");
        Map<String,Object> para = new HashMap<>();
        para.put("gridId",gridId);
        List<UserPo> list = (List<UserPo>) baseDao.findAll(sb.toString(),para);
        return list;
    }

    @Override
    public Optional<UserPo> findByIdAndTenantNumber(Long id, Long tenantNumber) {
        AssertUtil.nonNull(id,"用户id不能为空");
        Optional<UserPo> result =baseDao.getSession().doReturningWork(
                new ReturningWork<Optional<UserPo>>() {
                    @Override
                    public Optional<UserPo> execute(java.sql.Connection connection) throws SQLException {
                        Statement stat = null;
                        PreparedStatement prepareStatement = null;
                        ResultSet resultSet = null;
                        try {
                            stat =  connection.createStatement();
                            stat.execute("use `".concat(TenantDatabaseUtil.tenantDatabaseName(tenantNumber)).concat("`"));
                            prepareStatement =  connection.prepareStatement("select * from t_user where id = ?");
                            prepareStatement.setLong(1,id);
                            resultSet = prepareStatement.executeQuery();
                            UserPo userPo = null;
                            while (resultSet.next()) {
                                userPo = UserPo.builder()
                                        .id(resultSet.getLong("id"))
                                        .isSms(resultSet.getBoolean("is_sms"))
                                        .isCall(resultSet.getBoolean("is_call"))
                                        .name(resultSet.getString("name"))
                                        .phoneNumber(resultSet.getString("phone_number"))
                                        .build();
                            }
                            return Optional.ofNullable(userPo);
                        }finally {
                            HibernateSessionJdbcUtil.close(stat,prepareStatement,resultSet);
                        }
                    }
                });
        return result;
    }

    @Override
    public Optional<UserPo> findByGridIdAndTenantNumber(Long id, Long tenantNumber) {
        AssertUtil.nonNull(id,"用户id不能为空");
        Optional<UserPo> result =baseDao.getSession().doReturningWork(
            new ReturningWork<Optional<UserPo>>() {
                @Override
                public Optional<UserPo> execute(java.sql.Connection connection) throws SQLException {
                    Statement stat = null;
                    PreparedStatement prepareStatement = null;
                    ResultSet resultSet = null;
                    try {
                        stat =  connection.createStatement();
                        stat.execute("use `".concat(TenantDatabaseUtil.tenantDatabaseName(tenantNumber)).concat("`"));
                        prepareStatement =  connection.prepareStatement("select u.* from t_user u join t_grid g on u.id = g.user_id where g.id = ?");
                        prepareStatement.setLong(1,id);
                        resultSet = prepareStatement.executeQuery();
                        UserPo userPo = null;
                        while (resultSet.next()) {
                            userPo = UserPo.builder()
                                    .id(resultSet.getLong("id"))
                                    .name(resultSet.getString("name"))
                                    .isSms(resultSet.getBoolean("is_sms"))
                                    .isCall(resultSet.getBoolean("is_call"))
                                    .phoneNumber(resultSet.getString("phone_number"))
                                    .build();
                        }
                        return Optional.ofNullable(userPo);
                    }finally {
                        HibernateSessionJdbcUtil.close(stat,prepareStatement,resultSet);
                    }

                }
            });
        return result;
    }

}
