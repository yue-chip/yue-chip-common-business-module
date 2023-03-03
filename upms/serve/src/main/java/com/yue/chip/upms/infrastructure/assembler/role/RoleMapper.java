package com.yue.chip.upms.infrastructure.assembler.role;

import com.yue.chip.upms.infrastructure.po.role.RolePo;
import com.yue.chip.upms.interfaces.dto.role.RoleAUDto;
import com.yue.chip.upms.interfaces.vo.role.RoleListVo;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

/**
 * @author Mr.Liu
 * @date 2023/3/3 下午2:51
 */
@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RoleMapper {

    public List<RoleListVo> toRoleListVo(List<RolePo> list);

    public RolePo toRolePo(RoleAUDto role);
}
