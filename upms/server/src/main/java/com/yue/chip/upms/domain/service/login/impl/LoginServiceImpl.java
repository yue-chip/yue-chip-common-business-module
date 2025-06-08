package com.yue.chip.upms.domain.service.login.impl;

import com.yue.chip.authentication.YueChipAuthenticationToken;
import com.yue.chip.core.TenantNumber;
import com.yue.chip.core.common.enums.State;
import com.yue.chip.core.tenant.TenantExposeService;
import com.yue.chip.security.YueChipSimpleGrantedAuthority;
import com.yue.chip.security.YueChipUserDetails;
import com.yue.chip.upms.application.service.UpmsApplication;
import com.yue.chip.upms.assembler.weixin.UserWeiXinMapper;
import com.yue.chip.upms.domain.aggregates.Resources;
import com.yue.chip.upms.domain.aggregates.User;
import com.yue.chip.upms.domain.aggregates.UserWeixin;
import com.yue.chip.upms.domain.repository.upms.UpmsRepository;
import com.yue.chip.upms.domain.repository.weixin.UserWeiXinRepository;
import com.yue.chip.upms.domain.service.login.LoginService;
import com.yue.chip.upms.infrastructure.dao.user.SafetyDao;
import com.yue.chip.upms.infrastructure.po.user.SafetyPo;
import com.yue.chip.upms.infrastructure.po.user.UserWeiXinPo;
import com.yue.chip.upms.interfaces.dto.user.UserAddOrUpdateDto;
import com.yue.chip.utils.TenantNumberUtil;
import com.yue.chip.utils.YueChipRedisTokenStoreUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.dubbo.config.annotation.DubboReference;
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

import java.security.MessageDigest;
import java.time.LocalDateTime;
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

    @DubboReference
    private TenantExposeService tenantExposeService;

    @Resource
    private UserWeiXinRepository userWeiXinRepository;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private UserWeiXinMapper userWeiXinMapper;

    @Resource
    private UpmsApplication upmsApplication;

    @Resource
    private SafetyDao safetyDao;


    @Override
    public String login(String username, String password) {
        //检查租户状态
        Optional<User> optional = upmsRepository.findUserByUsername(username);
        if (optional.isEmpty()) {
            throw new AuthenticationServiceException("该账号不存在");
        }
        User user = optional.get();
        if (Objects.nonNull(user.getState())) {
            if (Objects.equals(user.getState(),State.DISABLE)) {
                throw new AuthenticationServiceException("该账号已被禁用！请联系管理员！");
            }
//            else {
//                upmsRepository.updateUserState(user.getId(), State.NORMAL);
//            }
        }
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new AuthenticationServiceException("密码错误！");
        }
//        if (!passwordEncoder.matches(password, user.getPassword())) {
//            Long failNum = user.getFailNum();
//            if (Objects.isNull(failNum)) {
//                failNum = 5L;
//            }
//            if (failNum > 5) {
//                failNum = 5L;
//            }
//            if (failNum - 1 > 0) {
//                upmsRepository.updateLoginFail(user.getId(), failNum - 1);
//                throw new AuthenticationServiceException("登录失败，您还剩" + (failNum - 1) + "次机会！");
//            } else {
//                upmsRepository.updateUserState(user.getId(), State.DISABLE);
//                throw new AuthenticationServiceException("该账号已被禁用！请联系管理员！");
//            }
//        }
//        if (Objects.nonNull(user.getLastPasswordTime())) {
//            Optional<SafetyPo> optionalSafetyPo = safetyDao.findById(1L);
//            if (optionalSafetyPo.isPresent()) {
//                if (LocalDateTime.now().minusDays(optionalSafetyPo.get().getPasswordTime()).isAfter(user.getLastPasswordTime())) {
//                    upmsRepository.updateUserState(user.getId(), State.DISABLE);
//                    throw new AuthenticationServiceException("密码超过"+optionalSafetyPo.get().getPasswordTime()+"天未修改！该账号已被禁用！请联系管理员！");
//                }
//            }
//        }
//        upmsRepository.updateLoginFail(user.getId(), 5L);
        return authority(user.getResources(), user.getId(), user.getUsername(), user.getPassword(), user.getTenantNumber());
    }

    @Override
    public String loginGrid(String username) {
        //检查租户状态
//        checkTenantState();
        upmsApplication.saveUser1( UserAddOrUpdateDto.builder().name(username).username(username).password(getMD5Hash(username)).passwordI(getMD5Hash(username)).build());
        Optional<User> optional = upmsRepository.findUserByUsername(username);
        if (optional.isEmpty()) {
            throw new AuthenticationServiceException("该账号不存在");
        }
        User user = optional.get();
        if (Objects.nonNull(user.getState())) {
            if (user.getState() == State.DISABLE) {
                throw new AuthenticationServiceException("该账号已被禁用！请联系管理员！");
            } else {
                upmsRepository.updateUserState(user.getId(), State.NORMAL);
            }
        }
        if (Objects.nonNull(user.getLastPasswordTime())) {
            Optional<SafetyPo> optionalSafetyPo = safetyDao.findById(1L);
            if (optionalSafetyPo.isPresent()) {
                if (LocalDateTime.now().minusDays(optionalSafetyPo.get().getPasswordTime()).isAfter(user.getLastPasswordTime())) {
                    upmsRepository.updateUserState(user.getId(), State.DISABLE);
                    throw new AuthenticationServiceException("密码超过"+optionalSafetyPo.get().getPasswordTime()+"天未修改！该账号已被禁用！请联系管理员！");
                }
            }
        }
        upmsRepository.updateLoginFail(user.getId(), 5L);
        return authority(user.getResources(), user.getId(), user.getUsername(), user.getPassword(), user.getTenantNumber());
    }

    @Override
    public String loginYst(String phoneNumber) {
        //检查租户状态
        //checkTenantState();
        Optional<User> optional = upmsRepository.findUserByPhoneNumber(phoneNumber);
        if (optional.isEmpty()) {
            throw new AuthenticationServiceException("未查询到此号码");
        }
        User user = optional.get();
        if (Objects.nonNull(user.getState())) {
            if (user.getState() == State.DISABLE) {
                throw new AuthenticationServiceException("该账号已被禁用！请联系管理员！");
            } else {
                upmsRepository.updateUserState(user.getId(), State.NORMAL);
            }
        }
        if (Objects.nonNull(user.getLastPasswordTime())) {
            Optional<SafetyPo> optionalSafetyPo = safetyDao.findById(1L);
            if (optionalSafetyPo.isPresent()) {
                if (LocalDateTime.now().minusDays(optionalSafetyPo.get().getPasswordTime()).isAfter(user.getLastPasswordTime())) {
                    upmsRepository.updateUserState(user.getId(), State.DISABLE);
                    throw new AuthenticationServiceException("密码超过"+optionalSafetyPo.get().getPasswordTime()+"天未修改！该账号已被禁用！请联系管理员！");
                }
            }
        }
        upmsRepository.updateLoginFail(user.getId(), 5L);
        return authority(user.getResources(), user.getId(), user.getUsername(), user.getPassword(), user.getTenantNumber());
    }

    public static String getMD5Hash(String input) {
        try {
            // 创建MessageDigest实例，并指定使用MD5算法
            MessageDigest md = MessageDigest.getInstance("MD5");

            // 使用指定的字节更新摘要
            md.update(input.getBytes());

            // 完成哈希计算并返回结果
            byte[] digest = md.digest();

            // 将字节转换为十六进制字符串
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (Exception e) {
            // 当JVM不支持MD5算法时，会抛出此异常
            throw new RuntimeException("MD5 algorithm not available!", e);
        }
    }

    @Override
    public String login1(String phoneNumber, String openId) {
        Optional<UserWeixin> optional = userWeiXinRepository.findByOpenId(openId);
        if (optional.isEmpty()) {
            Optional<TenantNumber> optionalTenantNumber = TenantNumberUtil.getTenantNumber();
            UserWeiXinPo userWeiXinPo = userWeiXinRepository.saveUserWeiXin(
                    UserWeiXinPo.builder()
                            .openId(openId)
                            .phoneNumber(StringUtils.hasText(phoneNumber) ? phoneNumber : null)
                            .tenantNumber(optionalTenantNumber.isPresent()?optionalTenantNumber.get().getTenantNumber():null)
                            .build());
            optional = Optional.ofNullable(userWeiXinMapper.toUserWeiXin(userWeiXinPo));
        }

        UserWeixin userWeixin = optional.get();
        if (!StringUtils.hasText(userWeixin.getPhoneNumber())) {
            if (!StringUtils.hasText(phoneNumber)) {
                throw new AuthenticationServiceException("请绑定手机号码！");
            }
        }
        if (StringUtils.hasText(phoneNumber) && !Objects.equals(phoneNumber, userWeixin.getPhoneNumber())) {
            userWeixin.setPhoneNumber(phoneNumber);
            userWeiXinRepository.updateUserWeiXin(userWeiXinMapper.toUserWeiXinPo(userWeixin));
        }

        if (Objects.isNull(userWeixin)) {
            throw new AuthenticationServiceException("用户鉴权失败！");
        }
        return authority(new ArrayList<Resources>(), userWeixin.getId(), userWeixin.getPhoneNumber(), "", userWeixin.getTenantNumber());
    }

    @Override
    public void loginOut() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (Objects.nonNull(requestAttributes)) {
            HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
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

    private String authority(List<Resources> resourcesList, Long id, String username, String password, Long tenantNumber) {
        upmsRepository.updateLastLoginTime(username);
        List<GrantedAuthority> authoritiesList = AuthorityUtils.createAuthorityList();
        resourcesList.forEach(resources -> {
            YueChipSimpleGrantedAuthority grantedAuthority = new YueChipSimpleGrantedAuthority();
            grantedAuthority.setAuthority(resources.getCode());
            authoritiesList.add(grantedAuthority);
        });
        YueChipAuthenticationToken token = new YueChipAuthenticationToken(username, authoritiesList);
        SecurityContextHolder.getContext().setAuthentication(token);
        YueChipUserDetails userDetails = new YueChipUserDetails(id, username, password, tenantNumber, authoritiesList);
        YueChipRedisTokenStoreUtil.store(userDetails, token.getToken());
        return token.getToken();
    }
}


