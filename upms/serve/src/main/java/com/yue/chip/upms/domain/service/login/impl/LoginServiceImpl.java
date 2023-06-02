package com.yue.chip.upms.domain.service.login.impl;

import com.yue.chip.exception.BusinessException;
import com.yue.chip.upms.domain.aggregates.User;
import com.yue.chip.upms.domain.repository.upms.UpmsRepository;
import com.yue.chip.upms.domain.service.login.LoginService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Service;
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

    @Override
    public String login(String username, String password) {
//        Optional<User> optional = upmsRepository.findUserByUsername(username);
//        if (optional.isEmpty()) {
//            BusinessException.throwException("改账号不存在");
//        }
//        HttpServletRequest request = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();
//        List<GrantedAuthority> authoritiesList = AuthorityUtils.createAuthorityList(authorities.toArray(new String[]{}));
//        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password, authoritiesList);
//        SecurityContextHolder.getContext().setAuthentication(token);
//        request.getSession().setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());
        return null;
    }
}
