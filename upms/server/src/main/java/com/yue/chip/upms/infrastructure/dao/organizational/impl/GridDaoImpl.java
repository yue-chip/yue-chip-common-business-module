package com.yue.chip.upms.infrastructure.dao.organizational.impl;

import com.yue.chip.core.YueChipPage;
import com.yue.chip.core.persistence.curd.BaseDao;
import com.yue.chip.upms.infrastructure.dao.organizational.GridDaoEx;
import com.yue.chip.upms.infrastructure.po.organizational.GridPo;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author Mr.Liu
 * @description: TODO
 * @date 2023/11/21 下午2:35
 */
public class GridDaoImpl implements GridDaoEx {

    @Resource
    private BaseDao<GridPo> baseDao;

    @Override
    public Page<GridPo> List( Long organizationalId, String name, String userName, YueChipPage yueChipPage) {
        StringBuffer sql = new StringBuffer();
        sql.append("select g from GridPo g join UserPo u on g.userId = u.id where 1=1 ");
        Map<String,Object> para = new HashMap<>();
        if (Objects.nonNull(organizationalId)) {
            sql.append(" and g.organizationalId = :organizationalId ");
            para.put("organizationalId",organizationalId);
        }
        if (StringUtils.hasText(name)) {
            sql.append(" and g.name like :name ");
            para.put("name","%"+name+"%");
        }
        if (StringUtils.hasText(userName)) {
            sql.append(" and u.name like :userName ");
            para.put("userName","%"+userName+"%");
        }
        return (Page<GridPo>) baseDao.findNavigator(yueChipPage,sql.toString(),para);
    }
}