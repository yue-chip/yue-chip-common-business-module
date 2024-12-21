package com.yue.chip.upms.util;

import com.yue.chip.security.YueChipSimpleGrantedAuthority;
import com.yue.chip.security.YueChipUserDetails;
import com.yue.chip.upms.domain.aggregates.User;
import com.yue.chip.upms.domain.repository.upms.UpmsRepository;
import com.yue.chip.upms.infrastructure.dao.user.SafetyDao;
import com.yue.chip.upms.infrastructure.po.user.SafetyPo;
import com.yue.chip.utils.CurrentUserUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author jiacheng.liao on 2024/12/20
 */
@Component
public class TokenUpdateInterceptor implements HandlerInterceptor {

    @Resource
    private SafetyDao safetyDao;

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        updateToken();
    }

    public void updateToken() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (Objects.nonNull(requestAttributes)) {
            HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
            if (Objects.nonNull(request)) {
                Object obj = request.getHeader("token");
                if (Objects.nonNull(obj)) {
                    String token = String.valueOf(obj);
                    if (StringUtils.hasText(token)) {
                        Optional<SafetyPo> optionalSafetyPo = safetyDao.findById(1L);
                        Long time = optionalSafetyPo.get().getTimeout();
                        TestYueChipRedisTokenStoreUtil.renewal(token, time);
                    }
                }
            }
        }
    }
}
