package com.yue.chip.upms.application.expose.impl.user;

import com.yue.chip.security.YueChipSimpleGrantedAuthority;
import com.yue.chip.security.YueChipUserDetails;
import com.yue.chip.upms.definition.aggregates.RoleARVODefinition;
import com.yue.chip.upms.domain.aggregates.User;
import com.yue.chip.upms.domain.repository.upms.UpmsRepository;
import com.yue.chip.utils.CurrentUserUtil;
import jakarta.annotation.Resource;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author Mr.Liu
 * @date 2023/2/16 下午1:50
 */
@DubboService(interfaceClass = UserDetailsService.class)
public class UserDetailsServiceImpl implements UserDetailsService {

    @Resource
    private UpmsRepository upmsRepository;
    @Resource
    private RedisTemplate redisTemplate;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optional = upmsRepository.findUserByName(username);
        if (!optional.isPresent()){
            return null;
        }
        User user = optional.get();
        YueChipUserDetails userDetails = new YueChipUserDetails(user.getUsername(),user.getPassword(),getUserGrantedAuthority(user.getRoles()));
        redisTemplate.opsForValue().set(CurrentUserUtil.TENANT_ID + username,user.getTenantId());
        redisTemplate.opsForValue().set(CurrentUserUtil.USER_ID + username,user.getId());
        return userDetails;
    }
    /**
     * 设置用户权限
     * @param roles
     * @return
     */
    private List<GrantedAuthority> getUserGrantedAuthority(List<RoleARVODefinition> roles){
        List<GrantedAuthority> listGrantedAuthority = new ArrayList<GrantedAuthority>();
        if (Objects.nonNull(roles)) {
            roles.forEach(roleARVODefinition -> {
                roleARVODefinition.getResources().forEach(resourcesVODefinition -> {
                    YueChipSimpleGrantedAuthority grantedAuthority = new YueChipSimpleGrantedAuthority();
                    grantedAuthority.setAuthority(resourcesVODefinition.getCode());
                    listGrantedAuthority.add(grantedAuthority);
                });
            });
        }
        return listGrantedAuthority;
    }
}
