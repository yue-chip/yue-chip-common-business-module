package com.yue.chip.upms.infrastructure.dao.user;

import com.yue.chip.upms.infrastructure.po.user.UserPo;
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

    /**
     * 根据登录账号查询
     * @param username
     * @return
     */
    public Optional<UserPo> find(String username);

    /**
     * 分页类表
     * @param name
     * @param username
     * @param pageable
     * @return
     */
    public Page<UserPo> find(String name, String username, Pageable pageable);

    /**
     * 根据角色查询关联的用户
     * @param roleId
     * @return
     */
    public List<UserPo> findByRoleId(Long roleId);

}
