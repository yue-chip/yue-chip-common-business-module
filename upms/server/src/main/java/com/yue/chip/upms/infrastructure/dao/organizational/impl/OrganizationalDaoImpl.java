package com.yue.chip.upms.infrastructure.dao.organizational.impl;

import com.yue.chip.core.YueChipPage;
import com.yue.chip.core.persistence.curd.BaseDao;
import com.yue.chip.upms.infrastructure.dao.organizational.OrganizationalDaoEx;
import com.yue.chip.upms.infrastructure.po.organizational.OrganizationalPo;
import com.yue.chip.upms.infrastructure.po.user.UserPo;
import javax.annotation.Resource;
import org.springframework.data.domain.Page;
import org.springframework.util.CollectionUtils;
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
    public Optional<OrganizationalPo> findByUserIdFist(Long userId) {
        if (Objects.isNull(userId)) {
            return Optional.empty();
        }
        StringBuffer sb = new StringBuffer();
        sb.append(" select o from OrganizationalPo o join OrganizationalUserPo ou on o.id=ou.organizationalId  where ou.userId = :userId ");
        Map<String,Object> para = new HashMap<>();
        para.put("userId",userId);
        List<OrganizationalPo> list = (List<OrganizationalPo>) baseDao.findAll(sb.toString(),para);
        if (list.size()>0) {
            return Optional.ofNullable(list.get(0));
        }
        return Optional.empty();
    }

    @Override
    public List<OrganizationalPo> findByUserId(Long userId) {
        if (Objects.isNull(userId)) {
            return List.of();
        }
        StringBuffer sb = new StringBuffer();
        sb.append(" select o from OrganizationalPo o join OrganizationalUserPo ou on o.id=ou.organizationalId  where ou.userId = :userId ");
        Map<String,Object> para = new HashMap<>();
        para.put("userId",userId);
        List<OrganizationalPo> list = (List<OrganizationalPo>) baseDao.findAll(sb.toString(),para);
        return list;
    }

    @Override
    public Page<OrganizationalPo> organizationalPoPage(List<Long> organizationalList, YueChipPage yueChipPage) {
        if (!CollectionUtils.isEmpty(organizationalList)) {
            StringBuffer sb = new StringBuffer();
            Map<String,Object> para = new HashMap<>();
            sb.append("select r from OrganizationalPo r where id in :ids ");
            para.put("ids", organizationalList);
            sb.append(" ORDER BY r.createDateTime ASC ");
            return (Page<OrganizationalPo>) baseDao.findNavigator(yueChipPage, sb.toString(), para);
        }
        return null;
    }
}
