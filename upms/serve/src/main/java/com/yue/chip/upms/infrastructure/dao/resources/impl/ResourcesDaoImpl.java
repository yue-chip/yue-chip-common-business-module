package com.yue.chip.upms.infrastructure.dao.resources.impl;

import com.yue.chip.core.persistence.curd.BaseDao;
import com.yue.chip.exception.BusinessException;
import com.yue.chip.upms.enums.Scope;
import com.yue.chip.upms.infrastructure.dao.resources.ResourcesDaoEx;
import com.yue.chip.upms.infrastructure.po.resources.ResourcesPo;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotNull;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.*;

/**
 * @author Mr.Liu
 * @date 2023/1/14 上午10:44
 * @description ResourcesDaoImpl
 */
public class ResourcesDaoImpl implements ResourcesDaoEx {

    @Autowired
    private BaseDao<ResourcesPo> baseDao;

    @Resource
    private DataSource dataSource;

    @Override
    public List<ResourcesPo> find(@NotNull Long userId, @NotNull Long parentId, @NotNull Scope scope) {
        StringBuffer sb = new StringBuffer(" select re from ResourcesPo re " +
                "join RoleResourcesPo ro on re.id = ro.resourcesId " +
                "join UserRolePo ur on ro.roleId = ur.roleId " +
                "where ur.userId = :userId and re.parentId = :parentId and re.scope = :scope order by sort asc");
        Map<String, Object> para = new HashMap<>();
        para.put("userId",userId);
        para.put("parentId",parentId);
        para.put("scope",scope);
        List<ResourcesPo> list = (List<ResourcesPo>) baseDao.findAll(sb.toString(),para);
        return list;
    }

    @Override
    public List<ResourcesPo> find(Long roleId) {
        if (Objects.isNull(roleId)) {
            return Collections.EMPTY_LIST;
        }
//        StringBuffer sb = new StringBuffer(" select re from ResourcesPo re " +
//                "join RoleResourcesPo ro on re.id = ro.resourcesId " +
//                "where ro.roleId = :roleId ");
//        Map<String, Object> para = new HashMap<>();
//        para.put("roleId",roleId);
//        List<ResourcesPo> list = (List<ResourcesPo>) baseDao.findAll(sb.toString(),para);
//        return list;
        QueryRunner queryRunner = new QueryRunner(dataSource);
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(" select re.* from t_resources re join t_role_resources ro on re.id = ro.resources_id where ro.role_id = ?  ");
            List<ResourcesPo> list = queryRunner.query(sb.toString(),new BeanListHandler<ResourcesPo>(ResourcesPo.class),roleId);
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
            BusinessException.throwException(e.getMessage());
        }
        return Collections.EMPTY_LIST;
    }


}
