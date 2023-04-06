package com.yue.chip.upms.infrastructure.dao.resources.impl;

import com.yue.chip.core.persistence.curd.BaseDao;
import com.yue.chip.upms.enums.Scope;
import com.yue.chip.upms.infrastructure.dao.resources.ResourcesDaoEx;
import com.yue.chip.upms.infrastructure.po.resources.ResourcesPo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * @author Mr.Liu
 * @date 2023/1/14 上午10:44
 * @description ResourcesDaoImpl
 */
public class ResourcesDaoImpl implements ResourcesDaoEx {

    @Autowired
    private BaseDao<ResourcesPo> baseDao;

    @Override
    public List<ResourcesPo> find(Long userId, Long parentId, Scope scope) {
        StringBuffer sb = new StringBuffer(" select re from ResourcesPo re " +
                "join RoleResourcesPo ro on re.id = ro.resourcesId " +
                "join UserRolePo ur on ro.roleId = ur.roleId " +
                "where ur.userId = :userId and re.parentId = :parentId and re.scope = :scope");
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
        StringBuffer sb = new StringBuffer(" select re from ResourcesPo re " +
                "join RoleResourcesPo ro on re.id = ro.resourcesId " +
                "where ro.roleId = :roleId ");
        Map<String, Object> para = new HashMap<>();
        para.put("roleId",roleId);
        List<ResourcesPo> list = (List<ResourcesPo>) baseDao.findAll(sb.toString(),para);
        return list;
    }


}
