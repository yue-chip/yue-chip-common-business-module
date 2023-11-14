package com.yue.chip.upms.assembler.role;

import com.yue.chip.upms.domain.aggregates.Role;
import com.yue.chip.upms.infrastructure.po.role.RolePo;
import com.yue.chip.upms.interfaces.dto.role.RoleAUDto;
import com.yue.chip.upms.interfaces.vo.role.RoleVo;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author Mr.Liu
 * @date 2023/3/3 下午2:51
 */
@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RoleMapper {

    RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);

    public List<RoleVo> toRoleListVo(List<RolePo> list);

    public RolePo toRolePo(RoleAUDto role);

    public Role toRole(RolePo rolePo);

    public List<Role> toRoleList(List<RolePo> list);

    public RoleVo toRoleVo(Role role);

    public List<Role> listRoleARVODefinitionToRoleList(List<Role> list);

}
