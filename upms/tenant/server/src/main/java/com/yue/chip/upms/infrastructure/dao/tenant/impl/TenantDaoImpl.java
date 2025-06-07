package com.yue.chip.upms.infrastructure.dao.tenant.impl;

import com.yue.chip.core.YueChipPage;
import com.yue.chip.core.persistence.curd.BaseDao;
import com.yue.chip.upms.infrastructure.dao.tenant.TenantDaoEx;
import com.yue.chip.upms.infrastructure.po.tenant.TenantPo;
import jakarta.annotation.Resource;
import org.springframework.data.domain.Page;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class TenantDaoImpl  implements TenantDaoEx {

    @Resource
    private BaseDao<TenantPo> baseDao;


    @Override
    public Optional<TenantPo> findTenantByUrl(String requestDomain) {
        StringBuffer sb = new StringBuffer();
        Map<String, Object> para = new HashMap<>();
        sb.append("select t from TenantPo t join TenantRequestDomainPo tr on t.id = tenantId where 1=1 ");
        if (StringUtils.hasText(requestDomain)) {
            sb.append(" and tr.requestDomain = :requestDomain ");
            para.put("requestDomain",requestDomain);
        }
        Page page = (Page<TenantPo>)baseDao.findNavigator(new YueChipPage(0,1),sb.toString(),para);
        List<TenantPo> list = page.getContent();
        if (list!=null&&list.size()>0){
            return Optional.of(list.get(0));
        }
        return Optional.empty();
    }
}
