package com.yue.chip.upms.application.expose.impl.user;

import com.yue.chip.upms.UserExpose;
import com.yue.chip.upms.domain.repository.organizational.UserRepository;
import com.yue.chip.upms.vo.UserExposeVo;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

/**
 * @author xianming.chen
 * @description: TODO
 * @date 2023-10-31
 */
@DubboService(interfaceClass = UserExpose.class)
public class UserExposeImpl implements UserExpose {

    @Resource
    private UserRepository userRepository;

    @Override
    public List<UserExposeVo> findAllByIdIn(Set<Long> userIds) {
        return userRepository.findAllByIdIn(userIds);
    }
}
