package com.yue.chip.upms.application.expose.impl.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yue.chip.core.ICurrentUser;
import com.yue.chip.upms.assembler.user.UserMapper;
import com.yue.chip.upms.domain.aggregates.User;
import com.yue.chip.upms.domain.repository.upms.UpmsRepository;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotBlank;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @description:
 * @author: Mr.Liu
 * @create: 2020-02-20 20:32
 */
@DubboService(interfaceClass = ICurrentUser.class)
@Service
public class CurrentUserExposeImpl implements ICurrentUser<User> {

    @Resource
    private UpmsRepository upmsRepository;

    @Resource
    private UserMapper userMapper;

    @Override
    public Map<String, Object> findUserToMap(@NotBlank String username) {
        Optional<User> optional = upmsRepository.findUserByUsername(username);
        if (optional.isPresent()) {
            User user = optional.get();
            Map<String, Object> map = new ObjectMapper().convertValue(user,Map.class);
            return map;
        }
        return null;
    }


}