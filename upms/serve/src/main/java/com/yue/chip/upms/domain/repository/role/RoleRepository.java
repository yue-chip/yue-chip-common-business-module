package com.yue.chip.upms.domain.repository.role;

import com.yue.chip.core.IPageResultData;
import com.yue.chip.core.YueChipPage;
import com.yue.chip.core.repository.BaseRepository;
import com.yue.chip.upms.domain.aggregates.Role;
import com.yue.chip.upms.infrastructure.po.role.RolePo;
import com.yue.chip.upms.interfaces.dto.role.RoleAddDto;
import com.yue.chip.upms.interfaces.dto.role.RoleUpdateDto;
import com.yue.chip.upms.interfaces.vo.role.RoleListVo;

import java.util.List;

/**
 * @author Mr.Liu
 * @date 2023/1/14 上午10:46
 * @description RoleRepository
 */
public interface RoleRepository extends BaseRepository<RolePo> {

    /**
     * 角色列表
     *
     * @param name
     * @param code
     * @param pageable
     * @return
     */
    public IPageResultData<List<RoleListVo>> list(String name, String code, YueChipPage pageable);

    /**
     * 新增
     * @param role
     * @return
     */
    public void save(RoleAddDto role);

    /**
     * 修改
     * @param role
     */
    public void update(RoleUpdateDto role);
}
