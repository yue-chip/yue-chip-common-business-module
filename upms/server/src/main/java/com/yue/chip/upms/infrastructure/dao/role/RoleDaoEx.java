package com.yue.chip.upms.infrastructure.dao.role;

import com.yue.chip.upms.infrastructure.po.role.RolePo;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Mr.Liu
 * @date 2023/1/14 上午10:42
 * @description RoleDaoEx
 */
public interface RoleDaoEx {

    /**
     * 列表分页
     * @param name
     * @param code
     * @param pageable
     * @return
     */
    public Page<RolePo> list(String name, String code,@NotNull Pageable pageable);

    /**
     * 根据用户id查询关联的角色
     * @param userId
     * @return
     */
    public List<RolePo> list(@NotNull Long userId);
}
