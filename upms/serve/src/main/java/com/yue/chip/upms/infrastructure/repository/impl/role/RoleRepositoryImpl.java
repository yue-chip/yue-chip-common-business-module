package com.yue.chip.upms.infrastructure.repository.impl.role;

import com.yue.chip.core.IPageResultData;
import com.yue.chip.core.PageResultData;
import com.yue.chip.core.YueChipPage;
import com.yue.chip.core.repository.impl.BaseRepositoryImpl;
import com.yue.chip.upms.domain.aggregates.Role;
import com.yue.chip.upms.domain.repository.role.RoleRepository;
import com.yue.chip.upms.infrastructure.assembler.role.RoleMapper;
import com.yue.chip.upms.infrastructure.dao.role.RoleDao;
import com.yue.chip.upms.infrastructure.dao.role.RoleResourcesDao;
import com.yue.chip.upms.infrastructure.po.role.RolePo;
import com.yue.chip.upms.interfaces.dto.role.RoleAddDto;
import com.yue.chip.upms.interfaces.dto.role.RoleUpdateDto;
import com.yue.chip.upms.interfaces.vo.role.RoleListVo;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Mr.Liu
 * @date 2023/1/14 上午10:48
 * @description RoleRepositoryImpl
 */
@Repository
public class RoleRepositoryImpl extends BaseRepositoryImpl<RolePo> implements RoleRepository {

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private RoleResourcesDao roleResourcesDao;

    @Resource
    private RoleMapper roleMapper;


    @Override
    public IPageResultData<List<RoleListVo>> list(String name, String code, YueChipPage pageable) {
        Page<RolePo> page = roleDao.list(name,code, pageable);
        return (IPageResultData<List<RoleListVo>>) PageResultData.convert(page,roleMapper.toRoleListVo(page.getContent()));
    }

    @Override
    public void save(RoleAddDto role) {
        save(roleMapper.toRolePo(role));
    }

    @Override
    public void update(RoleUpdateDto role) {
        update(roleMapper.toRolePo(role));
    }
}
