package com.yue.chip.upms.application.service.impl;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.yue.chip.core.common.enums.State;
import com.yue.chip.core.common.enums.UserType;
import com.yue.chip.security.YueChipSimpleGrantedAuthority;
import com.yue.chip.security.YueChipUserDetails;
import com.yue.chip.security.oauth2.YueChipUserDetailsService;
import com.yue.chip.upms.domain.aggregates.Role;
import com.yue.chip.upms.domain.aggregates.User;
import com.yue.chip.upms.domain.repository.social.UserSocialRepository;
import com.yue.chip.upms.domain.repository.upms.UpmsRepository;
import com.yue.chip.upms.infrastructure.po.user.UserPo;
import com.yue.chip.upms.infrastructure.po.user.UserSocialPo;
import jakarta.annotation.Resource;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author Mr.Liu
 * @date 2023/2/16 下午1:50
 */
@DubboService(interfaceClass = YueChipUserDetailsService.class)
@Service
public class UserDetailsServiceImpl implements YueChipUserDetailsService {

    @Resource
    private UpmsRepository upmsRepository;
    @Resource
    private UserSocialRepository userSocialRepository;
    @Resource
    private RedisTemplate redisTemplate;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optional = upmsRepository.findUserByUsername(username);
        if (!optional.isPresent()){
            return null;
        }
        User user = optional.get();
        YueChipUserDetails userDetails = new YueChipUserDetails(user.getId(),user.getUsername(),user.getPassword(),user.getTenantNumber(),getUserGrantedAuthority(user.getRoles()));
        return userDetails;
    }
    /**
     * 设置用户权限
     * @param roles
     * @return
     */
    private List<GrantedAuthority> getUserGrantedAuthority(List<Role> roles){
        List<GrantedAuthority> listGrantedAuthority = new ArrayList<GrantedAuthority>();
        if (Objects.nonNull(roles)) {
            roles.forEach(role -> {
                role.getResources().forEach(resourcesVODefinition -> {
                    YueChipSimpleGrantedAuthority grantedAuthority = new YueChipSimpleGrantedAuthority();
                    grantedAuthority.setAuthority(resourcesVODefinition.getCode());
                    listGrantedAuthority.add(grantedAuthority);
                });
            });
        }
        return listGrantedAuthority;
    }

    @Override
    public UserDetails loadUserByPhoneNumber(String phoneNumber) {
        User user = upmsRepository.findByPhoneNumber(phoneNumber);
        if (Objects.isNull(user)) {
            return null;
        }
        YueChipUserDetails userDetails = new YueChipUserDetails(user.getId(),user.getUsername(),user.getPassword(),user.getTenantNumber(),getUserGrantedAuthority(user.getRoles()));
        return userDetails;
    }

    @Override
    public UserDetails loadUserBySocialTypeAndSocialUid(String socialType, String socialUid) {
        Optional<User> optional = upmsRepository.findUserBySocialTypeAndSocialUid(socialType, socialUid);
        if (!optional.isPresent()){
            return null;
        }
        User user = optional.get();
        YueChipUserDetails userDetails = new YueChipUserDetails(user.getId(), user.getUsername(), user.getPassword(), user.getTenantNumber(), getUserGrantedAuthority(user.getRoles()));
        return userDetails;
    }

    @Override
    public UserDetails saveUserSocial(String socialType, String socialUid, String socialAcc, String socialNickname) {
        // 生成一个新的用户
        String name = StrUtil.format("{}_{}", socialType, RandomUtil.randomStringUpper(6));
        UserPo userPo = new UserPo();
        userPo.setTenantNumber(null);
        userPo.setName(name);
        userPo.setUsername(name);
        userPo.setNickname(socialNickname);
        userPo.setState(State.NORMAL);
        userPo.setUserType(UserType.ORDINARY);
        userPo.setPassword("123456"); // 默认密码
        User user = upmsRepository.saveUser(userPo);
        // 绑定第三方用户信息
        UserSocialPo userSocialPo = new UserSocialPo();
        userSocialPo.setUserId(user.getId());
        userSocialPo.setTenantNumber(null);
        userSocialPo.setType(socialType);
        userSocialPo.setUid(socialUid);
        userSocialPo.setAcc(socialAcc);
        userSocialPo = userSocialRepository.saveUserSocial(userSocialPo);

        // 重新获取用户信息
        Optional<User> currUserOptional = upmsRepository.findUserBySocialTypeAndSocialUid(socialType, socialUid);
        User currUser = currUserOptional.get();
        YueChipUserDetails userDetails = new YueChipUserDetails(currUser.getId(), currUser.getUsername(), currUser.getPassword(), currUser.getTenantNumber(), getUserGrantedAuthority(currUser.getRoles()));
        return userDetails;
    }
}
