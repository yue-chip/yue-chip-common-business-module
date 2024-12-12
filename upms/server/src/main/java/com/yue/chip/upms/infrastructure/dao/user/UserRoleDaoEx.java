package com.yue.chip.upms.infrastructure.dao.user;

import com.yue.chip.upms.infrastructure.po.user.UserPo;
import com.yue.chip.upms.interfaces.dto.user.UseRoleListDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author jiacheng.liao on 2024/12/10
 */
public interface UserRoleDaoEx {

    public Page<UserPo> roleUseList(UseRoleListDto useRoleListDto, Pageable pageable);

    public Page<UserPo> roleUseList(List<Long> userIds, UseRoleListDto useRoleListDto, Pageable pageable);

}
