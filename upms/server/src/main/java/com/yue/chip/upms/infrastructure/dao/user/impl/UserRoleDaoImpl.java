package com.yue.chip.upms.infrastructure.dao.user.impl;

import com.yue.chip.core.persistence.curd.BaseDao;
import com.yue.chip.upms.infrastructure.dao.user.UserRoleDaoEx;
import com.yue.chip.upms.infrastructure.po.user.UserPo;
import com.yue.chip.upms.interfaces.dto.user.UseRoleListDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author jiacheng.liao on 2024/12/10
 */
public class UserRoleDaoImpl implements UserRoleDaoEx {

    @Autowired
    public BaseDao<UserPo> baseDao;

    @Override
    public Page<UserPo> roleUseList(UseRoleListDto useRoleListDto, Pageable pageable) {
        StringBuffer sb = new StringBuffer();
        sb.append(" select u from UserPo u left join UserRolePo ur on u.id = ur.userId where ur.roleId = :roleId");
        Map<String,Object> para = new HashMap<>();
        para.put("roleId", useRoleListDto.getRoleId());
        if (StringUtils.hasText(useRoleListDto.getName())) {
            sb.append(" and ( u.name like :name or u.username like :name ) ");
            para.put("name", "%"+useRoleListDto.getName()+"%");
        }
        if (StringUtils.hasText(useRoleListDto.getPhone())) {
            sb.append(" and u.phoneNumber like :phone");
            para.put("phone", "%"+useRoleListDto.getPhone()+"%");
        }
        sb.append(" and u.username <> 'superadmin' ");
        sb.append(" ORDER BY u.id ASC");
        return (Page<UserPo>) baseDao.findNavigator(pageable,sb.toString(),para);
    }

    @Override
    public Page<UserPo> roleUseList(List<Long> userIds, UseRoleListDto useRoleListDto, Pageable pageable) {
        StringBuffer sb = new StringBuffer();
        sb.append(" select u from UserPo u where u.id in :userIds");
        Map<String,Object> para = new HashMap<>();
        para.put("userIds", userIds);
        if (StringUtils.hasText(useRoleListDto.getName())) {
            sb.append(" and ( u.name like :name or u.username like :name ) ");
            para.put("name", "%"+useRoleListDto.getName()+"%");
        }
        if (StringUtils.hasText(useRoleListDto.getPhone())) {
            sb.append(" and u.phoneNumber like :phone");
            para.put("phone", "%"+useRoleListDto.getPhone()+"%");
        }
        sb.append(" and u.username <> 'superadmin' ");
        sb.append(" ORDER BY u.id ASC");
        return (Page<UserPo>) baseDao.findNavigator(pageable,sb.toString(),para);
    }
}
