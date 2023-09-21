package com.yue.chip.upms.domain.service.login.impl;

import com.yue.chip.authentication.YueChipAuthenticationToken;
import com.yue.chip.exception.BusinessException;
import com.yue.chip.security.YueChipSimpleGrantedAuthority;
import com.yue.chip.security.YueChipUserDetails;
import com.yue.chip.upms.domain.aggregates.Resources;
import com.yue.chip.upms.domain.aggregates.User;
import com.yue.chip.upms.domain.repository.upms.UpmsRepository;
import com.yue.chip.upms.domain.service.login.LoginService;
import com.yue.chip.utils.YueChipRedisTokenStoreUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author Mr.Liu
 * @date 2023/5/25 下午2:05
 */
@Service
public class LoginServiceImpl implements LoginService {
    @Resource
    private UpmsRepository upmsRepository;

    @Resource
    private PasswordEncoder passwordEncoder;


    @Override
    public String login(String username, String password) {
        Optional<User> optional = upmsRepository.findUserByUsername(username);
        if (optional.isEmpty()) {
            BusinessException.throwException("该账号不存在");
        }
        User user = optional.get();
        if (!passwordEncoder.matches(password,user.getPassword()) ) {
            throw new AuthenticationServiceException("密码错误");
        }
        List<Resources> resourcesList = user.getResources();
        List<GrantedAuthority> authoritiesList = AuthorityUtils.createAuthorityList();
        resourcesList.forEach(resources -> {
            YueChipSimpleGrantedAuthority grantedAuthority = new YueChipSimpleGrantedAuthority();
            grantedAuthority.setAuthority(resources.getCode());
            authoritiesList.add(grantedAuthority);
        });
        YueChipAuthenticationToken token = new YueChipAuthenticationToken(username, authoritiesList);
        SecurityContextHolder.getContext().setAuthentication(token);
        YueChipUserDetails userDetails = new YueChipUserDetails(user.getId(),user.getUsername(),user.getPassword(),user.getTenantId(),authoritiesList);
        YueChipRedisTokenStoreUtil.store(userDetails,token.getToken());
        return token.getToken();
    }
}
