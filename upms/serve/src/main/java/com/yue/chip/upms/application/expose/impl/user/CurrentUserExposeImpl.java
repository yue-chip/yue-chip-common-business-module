package com.yue.chip.upms.application.expose.impl.user;

import com.yue.chip.core.ICurrentUser;
import com.yue.chip.upms.domain.aggregates.User;
import com.yue.chip.upms.domain.repository.user.UserRepository;
import com.yue.chip.utils.BeanToMapUtil;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;
import java.util.Optional;

/**
 * @description:
 * @author: Mr.Liu
 * @create: 2020-02-20 20:32
 */
@DubboService(interfaceClass = ICurrentUser.class)
public class CurrentUserExposeImpl implements ICurrentUser<User> {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Map<String, Object> findUserToMap(String username) {
        Optional<User> optional = userRepository.find(username);
        if (optional.isPresent()) {
            User user = optional.get();
            Map<String, Object> map = BeanToMapUtil.transBeanToMap(user);
            map.put("tenantId",user.getTenantId());
            return map;
        }
        return null;
    }


}