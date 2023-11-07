package com.yue.chip.upms.application.expose.impl.upms;

import com.yue.chip.upms.UpmsExposeService;
import com.yue.chip.upms.assembler.user.UserMapper;
import com.yue.chip.upms.domain.repository.upms.UpmsRepository;
import com.yue.chip.upms.vo.UserExposeVo;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author xianming.chen
 * @description: TODO
 * @date 2023-10-28
 */
@DubboService(interfaceClass = UpmsExposeService.class)
public class UpmsExposeServiceImpl implements UpmsExposeService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private UpmsRepository upmsRepository;

    @Override
    public List<UserExposeVo> findAllByIdIn(List<Long> userIds) {
        return userMapper.toUserExposeVo(upmsRepository.findUserByIds(userIds)) ;
    }

    @Override
    public List<UserExposeVo> findAllByOrganizationalId(List<Long> organizationalIds) {
        return userMapper.toUserExposeVo(upmsRepository.findUserByOrganizationalId(organizationalIds));
    }


}
