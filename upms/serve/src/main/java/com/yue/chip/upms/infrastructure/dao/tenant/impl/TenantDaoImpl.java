package com.yue.chip.upms.infrastructure.dao.tenant.impl;

import com.yue.chip.core.YueChipPage;
import com.yue.chip.core.common.enums.State;
import com.yue.chip.core.persistence.curd.BaseDao;
import com.yue.chip.upms.infrastructure.dao.tenant.TenantDaoEx;
import com.yue.chip.upms.infrastructure.po.tenant.TenantPo;
import com.yue.chip.upms.infrastructure.po.user.UserPo;
import jakarta.annotation.Resource;
import org.springframework.data.domain.Page;
import org.springframework.util.StringUtils;

import java.util.HashMap;
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
            para.put("name","%"+manager+"%");
        }
        if (StringUtils.hasText(phoneNumber)) {
            sb.append(" and t.phoneNumber like :phoneNumber ");
            para.put("name","%"+phoneNumber+"%");
        }
        if (Objects.nonNull(state)) {
            sb.append(" and t.state = :state ");
            para.put("state",state);
        }
        return (Page<TenantPo>) baseDao.findNavigator(pageable,sb.toString(),para);
    }
}
