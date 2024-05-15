package com.yue.chip.upms.infrastructure.dao.organizational.impl;

import com.yue.chip.core.YueChipPage;
import com.yue.chip.core.persistence.curd.BaseDao;
import com.yue.chip.upms.infrastructure.dao.organizational.GridDaoEx;
import com.yue.chip.upms.infrastructure.po.organizational.GridPo;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

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

    @Override
    public Page<GridPo> listGridQuery(Set<Long> organizationalIds, String name, YueChipPage yueChipPage, Set<Long> userIds, String time) {
        LocalDateTime startTime = null;
        LocalDateTime endTime = null;
        //if有时间
        if (Objects.nonNull(time)) {
            // 解析时间字符串为LocalDateTime对象
            LocalDateTime dateTime = LocalDateTime.parse(time + "-01 00:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            // 获取该月份的第一天
            startTime = LocalDateTime.of(dateTime.getYear(), dateTime.getMonth(), 1, 0, 0, 0);
            // 获取该月份的最后一秒
            endTime = LocalDateTime.of(dateTime.plusMonths(1).getYear(), dateTime.plusMonths(1).getMonth(), 1, 0, 0, 0).minusSeconds(1);
        }
        StringBuffer sql = new StringBuffer();
        sql.append("select g from GridPo g join UserPo u on g.userId = u.id where 1=1 ");
        Map<String,Object> para = new HashMap<>();
        if (Objects.nonNull(organizationalIds)) {
            sql.append(" and g.organizationalId in :organizationalIds ");
            para.put("organizationalIds",organizationalIds);
        }
        if (StringUtils.hasText(name)) {
            sql.append(" and g.name like :name ");
            para.put("name","%"+name+"%");
        }
        if (!CollectionUtils.isEmpty(userIds)) {
            sql.append(" and g.userId in :userIds ");
            para.put("userIds",userIds);
        }
        if (Objects.nonNull(startTime) && Objects.nonNull(endTime)) {
            sql.append(" and g.createDateTime BETWEEN :date1 AND :date2 ");
            para.put("date1", startTime);
            para.put("date2", endTime);
        }
        return (Page<GridPo>) baseDao.findNavigator(yueChipPage,sql.toString(),para);
    }
}
