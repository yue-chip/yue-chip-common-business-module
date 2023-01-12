package com.yue.chip.upms.expose.impl;

import com.yue.chip.security.YueChipUserDetails;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;

@DubboService(interfaceClass = UserDetailsService.class)
public class UserDetailsServiceExposeImpl implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        return new User("user","{noop}123456",getUserGrantedAuthority(111L));
        return new YueChipUserDetails("user","{noop}123456",getUserGrantedAuthority(111L));
    }

    private List<GrantedAuthority> getUserGrantedAuthority(Long userId){
        List<GrantedAuthority> listGrantedAuthority = new ArrayList<GrantedAuthority>();
        return listGrantedAuthority;
    }
}
