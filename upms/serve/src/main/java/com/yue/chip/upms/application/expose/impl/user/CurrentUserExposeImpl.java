package com.yue.chip.upms.application.expose.impl.user;

import com.yue.chip.core.ICurrentUser;
import com.yue.chip.upms.domain.aggregates.User;
import com.yue.chip.upms.domain.repository.upms.UpmsRepository;
import com.yue.chip.upms.infrastructure.assembler.user.UserMapper;
import jakarta.annotation.Resource;
import org.apache.dubbo.config.annotation.DubboService;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @description:
 * @author: Mr.Liu
 * @create: 2020-02-20 20:32
 */
@DubboService(interfaceClass = ICurrentUser.class)
public class CurrentUserExposeImpl implements ICurrentUser<User> {

    @Resource
    private UpmsRepository upmsRepository;

    @Resource
    private UserMapper userMapper;

    @Override
    public Map<String, Object> findUserToMap(String username) {
        Optional<User> optional = upmsRepository.findUserByUsername(username);
        if (optional.isPresent()) {
            User user = optional.get();
            Map<String, Object> map = new HashMap<>();
            map.put("id",user.getId());
            map.put("tenantId",user.getTenantId());
            return map;
        }
        return null;
    }


}