package com.yue.chip.upms.domain.service.login.impl;

import com.yue.chip.authentication.YueChipAuthenticationToken;
import com.yue.chip.core.common.enums.State;
import com.yue.chip.core.tenant.TenantUtil;
import com.yue.chip.exception.BusinessException;
import com.yue.chip.security.YueChipSimpleGrantedAuthority;
import com.yue.chip.security.YueChipUserDetails;
import com.yue.chip.upms.assembler.weixin.UserWeiXinMapper;
import com.yue.chip.upms.domain.aggregates.Resources;
import com.yue.chip.upms.domain.aggregates.User;
import com.yue.chip.upms.domain.aggregates.UserWeixin;
import com.yue.chip.upms.domain.repository.tenant.TenantRepository;
import com.yue.chip.upms.domain.repository.upms.UpmsRepository;
import com.yue.chip.upms.domain.repository.weixin.UserWeiXinRepository;
import com.yue.chip.upms.domain.service.login.LoginService;
import com.yue.chip.upms.infrastructure.po.tenant.TenantStatePo;
import com.yue.chip.upms.infrastructure.po.user.UserWeiXinPo;
import com.yue.chip.utils.YueChipRedisTokenStoreUtil;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
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
    private TenantRepository tenantRepository;

    @Resource
    private UserWeiXinRepository userWeiXinRepository;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private UserWeiXinMapper userWeiXinMapper;


    @Override
    public String login(String username, String password) {
        //检查租户状态
        checkTenantState();
        Optional<User> optional = upmsRepository.findUserByUsername(username);
        if (!optional.isPresent()) {
            throw new AuthenticationServiceException("该账号不存在");
        }
        User user = optional.get();
        if (!passwordEncoder.matches(password,user.getPassword()) ) {
            throw new AuthenticationServiceException("密码错误");
        }
        return authority(user.getResources(),user.getId(),user.getUsername(),user.getPassword(),user.getTenantNumber());
    }

    @Override
    public String login1(String phoneNumber, String openId) {
        Optional<UserWeixin> optional = userWeiXinRepository.findByOpenId(openId);
        if (!optional.isPresent()) {
            UserWeiXinPo userWeiXinPo = userWeiXinRepository.saveUserWeiXin(
                    UserWeiXinPo.builder()
                            .openId(openId)
                            .phoneNumber(StringUtils.hasText(phoneNumber)?phoneNumber:null)
                            .tenantNumber(TenantUtil.getTenantNumber())
                            .build());
            optional = Optional.ofNullable(userWeiXinMapper.toUserWeiXin(userWeiXinPo));
        }

        UserWeixin userWeixin = optional.get();
        if (!StringUtils.hasText(userWeixin.getPhoneNumber())) {
            if (!StringUtils.hasText(phoneNumber)) {
                throw new AuthenticationServiceException("请绑定手机号码！");
            }
        }
        if (StringUtils.hasText(phoneNumber) && !Objects.equals(phoneNumber,userWeixin.getPhoneNumber()) ){
            userWeixin.setPhoneNumber(phoneNumber);
            userWeiXinRepository.updateUserWeiXin(userWeiXinMapper.toUserWeiXinPo(userWeixin));
        }

        if (Objects.isNull(userWeixin)) {
            throw new AuthenticationServiceException("用户鉴权失败！");
        }
        return authority(new ArrayList<Resources>(),userWeixin.getId(),userWeixin.getPhoneNumber(),"",userWeixin.getTenantNumber());
    }

    @Override
    public void loginOut() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (Objects.nonNull(requestAttributes)) {
            HttpServletRequest request = (HttpServletRequest) ((ServletRequestAttributes) requestAttributes).getRequest();
            if (Objects.nonNull(request)) {
                Object obj = request.getHeader("token");
                if (Objects.nonNull(obj)) {
                    String token = String.valueOf(obj);
                    if (StringUtils.hasText(token)) {
                        YueChipRedisTokenStoreUtil.clean(token);
                    }
                }
            }
        }
    }

    private String authority(List<Resources> resourcesList,Long id, String username ,String password,Long tenantNumber) {
        List<GrantedAuthority> authoritiesList = AuthorityUtils.createAuthorityList();
        resourcesList.forEach(resources -> {
            YueChipSimpleGrantedAuthority grantedAuthority = new YueChipSimpleGrantedAuthority();
            grantedAuthority.setAuthority(resources.getCode());
            authoritiesList.add(grantedAuthority);
        });
        YueChipAuthenticationToken token = new YueChipAuthenticationToken(username, authoritiesList);
        SecurityContextHolder.getContext().setAuthentication(token);
        YueChipUserDetails userDetails = new YueChipUserDetails(id,username,password, tenantNumber,authoritiesList);
        YueChipRedisTokenStoreUtil.store(userDetails,token.getToken());
        return token.getToken();
    }

    private void checkTenantState() {
        Optional<TenantStatePo> optional = tenantRepository.findTenantStateFirst();
        if (!optional.isPresent()) {
            BusinessException.throwException("该租户状态不可用");
        }else {
            TenantStatePo tenantStatePo = optional.get();
            if (Objects.equals(tenantStatePo.getState(), State.DISABLE)) {
                BusinessException.throwException("该租户状态不可用");
            }
        }
    }
}
