package com.yue.chip.upms.infrastructure.dao.user;

import com.yue.chip.core.persistence.curd.BaseDao;
import com.yue.chip.upms.domain.aggregates.User;
import com.yue.chip.upms.infrastructure.po.user.UserPo;
import org.springframework.cache.annotation.Cacheable;

import java.util.Optional;

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
    public Optional<UserPo> findFirstById(Long id);
}
