package com.yue.chip.upms.application.expose.impl.user;

import com.yue.chip.exception.BusinessException;
import com.yue.chip.security.YueChipSimpleGrantedAuthority;
import com.yue.chip.security.YueChipUserDetails;
import com.yue.chip.upms.definition.aggregates.RoleARVODefinition;
import com.yue.chip.upms.domain.aggregates.Role;
import com.yue.chip.upms.domain.aggregates.User;
import com.yue.chip.upms.domain.repository.user.UserRepository;
import com.yue.chip.upms.infrastructure.po.user.UserPo;
import jakarta.annotation.Resource;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Mr.Liu
 * @date 2023/2/16 下午1:50
 */
@DubboService(interfaceClass = UserDetailsService.class)
public class UserDetailsServiceImpl implements UserDetailsService {

    @Resource
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optional = userRepository.find(username);
        if (!optional.isPresent()){
            BusinessException.throwException("用户不存在");
        }
        User user = optional.get();
        YueChipUserDetails userDetails = new YueChipUserDetails(user.getUsername(),user.getPassword(),getUserGrantedAuthority(user.getRoles()));
        return userDetails;
    }
    /**
     * 设置用户权限
     * @param roles
     * @return
     */
    private List<GrantedAuthority> getUserGrantedAuthority(List<RoleARVODefinition> roles){
        List<GrantedAuthority> listGrantedAuthority = new ArrayList<GrantedAuthority>();
        roles.forEach(roleARVODefinition -> {
            roleARVODefinition.getResources().forEach(resourcesVODefinition -> {
                YueChipSimpleGrantedAuthority grantedAuthority = new YueChipSimpleGrantedAuthority();
                grantedAuthority.setAuthority(resourcesVODefinition.getCode());
                listGrantedAuthority.add(grantedAuthority);
            });
        });
        return listGrantedAuthority;
    }
}
