package com.yue.chip.upms.infrastructure.repository.user.impl;

import com.yue.chip.upms.assembler.user.UserMapper;
import com.yue.chip.upms.domain.repository.organizational.UserRepository;
import com.yue.chip.upms.infrastructure.dao.user.UserDao;
import com.yue.chip.upms.infrastructure.po.user.UserPo;
import com.yue.chip.upms.interfaces.vo.user.UserVo;
import com.yue.chip.upms.vo.UserExposeVo;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author xianming.chen
 * @description: TODO
 * @date 2023-10-31
 */

@Component
public class UserRepositoryImpl implements UserRepository {

    @Resource
    private UserDao userDao;

    @Resource
    private UserMapper userMapper;

    @Override
    public List<UserExposeVo> findAllByIdIn(Set<Long> userIds) {
        List<UserExposeVo> userExposeVoList = new ArrayList<>();
        List<UserPo> userPoList = userDao.findAllByIdIn(userIds);
        if (!CollectionUtils.isEmpty(userPoList)) {
            userPoList.forEach(userPo -> {
                UserExposeVo userExposeVo = userMapper.toUserExposeVo(userPo);
                userExposeVoList.add(userExposeVo);
            });
        }
        return userExposeVoList;
    }
}
