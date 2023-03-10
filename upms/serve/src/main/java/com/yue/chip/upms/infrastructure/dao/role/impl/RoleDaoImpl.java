package com.yue.chip.upms.infrastructure.dao.role.impl;

import com.yue.chip.core.persistence.curd.BaseDao;
import com.yue.chip.upms.infrastructure.dao.role.RoleDaoEx;
import com.yue.chip.upms.infrastructure.po.role.RolePo;
import jakarta.annotation.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * @author Mr.Liu
 * @date 2023/1/14 上午10:43
 * @description RoleDaoImpl
 */
public class RoleDaoImpl implements RoleDaoEx {

    @Resource
    private BaseDao<RolePo> baseDao;

    @Override
    public Page<RolePo> list(String name, String code, Pageable pageable) {
        StringBuffer sb = new StringBuffer();
        Map<String, Object> para = new HashMap<>();
        sb.append("select r from RolePo r where 1=1 ");
        if (StringUtils.hasText(name)) {
            sb.append(" and r.name like :name ");
            para.put("name","%"+name+"%");
        }
        if (StringUtils.hasText(code)) {
            sb.append(" and r.code like :code ");
            para.put("code","%"+code+"%");
        }
        return (Page<RolePo>) baseDao.findNavigator(pageable,sb.toString(),para);
    }

    @Override
    public List<RolePo> list(Long userId) {
        if (Objects.isNull(userId)) {
            return Collections.EMPTY_LIST;
        }
        StringBuffer sb = new StringBuffer();
        Map<String, Object> para = new HashMap<>();
        sb.append("select r from RolePo r join UserRolePo ur on r.id = ur.roleId where ur.userId = :userId ");
        para.put("userId",userId);
        return (List<RolePo>) baseDao.findAll(sb.toString(),para);
    }
}
