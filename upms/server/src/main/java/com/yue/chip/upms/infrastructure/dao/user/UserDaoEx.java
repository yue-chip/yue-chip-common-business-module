package com.yue.chip.upms.infrastructure.dao.user;

import com.yue.chip.core.common.enums.State;
import com.yue.chip.upms.infrastructure.po.user.UserPo;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * @author Mr.Liu
 * @date 2023/1/14 上午10:33
 * @description UserDaoEx
 */
public interface UserDaoEx {

//    /**
//     * 根据登录账号查询
//     * @param username
//     * @return
//     */
//    public Optional<UserPo> find(@NotBlank String username);

    /**
     * 分页类表
     * @param name
     * @param username
     * @param pageable
     * @return
     */
    public Page<UserPo> find( String name, String username,@NotNull Pageable pageable);

    /**
     * 根据角色查询关联的用户
     * @param roleId
     * @return
     */
    public List<UserPo> findByRoleId(@NotNull Long roleId);

    /**
     * 根据机构获取用户
     *
     * @param organizationalId
     * @param state
     * @return
     */
    public List<UserPo> findUserByOrganizationalId(@NotNull Long organizationalId, @NotNull State state);

    /**
     * 根据机构获取用户
     *
     * @param organizationalIds
     * @param state
     * @return
     */
    public List<UserPo> findUserByOrganizationalId(@NotNull @Size(min = 1) List<Long> organizationalIds, @NotNull State state);

    /**
     * 根据网格id查寻用户
     *
     * @param gridId
     * @return
     */
    public List<UserPo> findUserByGridId(@NotNull Long gridId);

    /**
     * 根据用id和租户编码查寻用户
     *
     * @param id
     * @param tenantNumber
     * @return
     */
    Optional<UserPo> findByIdAndTenantNumber(@NotNull Long id, Long tenantNumber);

}
