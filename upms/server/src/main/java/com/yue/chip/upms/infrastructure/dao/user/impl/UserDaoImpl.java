package com.yue.chip.upms.infrastructure.dao.user.impl;

import com.yue.chip.core.common.enums.State;
import com.yue.chip.core.persistence.curd.BaseDao;
import com.yue.chip.upms.infrastructure.dao.user.UserDaoEx;
import com.yue.chip.upms.infrastructure.po.user.UserPo;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.util.*;

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

}
