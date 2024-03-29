package com.yue.chip.upms.infrastructure.dao.user;

import com.yue.chip.core.persistence.curd.BaseDao;
import com.yue.chip.upms.domain.aggregates.User;
import com.yue.chip.upms.infrastructure.po.user.UserPo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * @author Mr.Liu
 * @date 2023/1/12 下午4:15
 * @description UserDao
 */
public interface UserDao extends BaseDao<UserPo>, UserDaoEx {

    /**
     * 根据用户id查询
     * @param id
     * @return
     */
    @Cacheable(value = User.CACHE_KEY,key = "#id")
    public Optional<UserPo> findFirstById(@NotNull Long id);

    /**
     * 更具登录帐号查询用户
     * @param username
     * @return
     */
    public Optional<UserPo> findFirstByUsername(@NotBlank String username);

    /**
     * 修改用户密码
     * @param id
     * @param password
     */
    @Modifying
    @Query("update UserPo set password=:password where id =:id ")
    @Transactional
    public void updatePassword(@NotNull @Param("id") Long id, @NotBlank @Param("password") String password);

    /**
     * 根据ids查询所有用户
     * @param userIds
     * @return
     */
    List<UserPo> findAllByIdIn(@NotNull @Size(min = 1) List<Long> userIds);

    List<UserPo> findAllByNameOrPhoneNumber(String name,String phoneNumber);

}
