package com.yue.chip.upms.infrastructure.dao.organizational.impl;

import com.yue.chip.core.persistence.curd.BaseDao;
import com.yue.chip.upms.infrastructure.dao.organizational.OrganizationalDaoEx;
import com.yue.chip.upms.infrastructure.po.organizational.OrganizationalPo;
import com.yue.chip.upms.infrastructure.po.user.UserPo;
import jakarta.annotation.Resource;
import org.springframework.data.domain.Page;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * @author Mr.Liu
 * @description: TODO
 * @date 2023/10/8 下午3:04
 */
public class OrganizationalDaoImpl implements OrganizationalDaoEx {

    @Resource
    private BaseDao<OrganizationalPo> baseDao;

    @Override
    public Optional<OrganizationalPo> findByUserId(Long userId) {
        if (Objects.isNull(userId)) {
            return Optional.empty();
        }
        StringBuffer sb = new StringBuffer();
        sb.append(" select o from OrganizationalPo o join OrganizationalUserPo ou on o.id=ou.organizationalId  where ou.userId = :userId limit 0,1");
        Map<String,Object> para = new HashMap<>();
        para.put("userId",userId);
        List<OrganizationalPo> list = (List<OrganizationalPo>) baseDao.findAll(sb.toString(),para);
        if (list.size()>0) {
            return Optional.ofNullable(list.get(0));
        }
        return Optional.empty();
    }
}
