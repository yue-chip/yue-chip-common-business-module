package com.yue.chip.upms.domain.service.login.impl;

//import com.yue.chip.authentication.YueChipAuthenticationToken;

import com.yue.chip.upms.domain.service.login.LoginService;
import org.springframework.stereotype.Service;

/**
 * @author Mr.Liu
 * @date 2023/5/25 下午2:05
 */
@Service
public class LoginServiceImpl implements LoginService {
//    @Resource
//    private UpmsRepository upmsRepository;
//
//    @Resource
//    private TenantRepository tenantRepository;
//
//    @Resource
//    private UserWeiXinRepository userWeiXinRepository;
//
//    @Resource
//    private PasswordEncoder passwordEncoder;
//
//    @Resource
//    private UserWeiXinMapper userWeiXinMapper;
//
//
//    @Override
//    public String login(String username, String password) {
//        //检查租户状态
//        checkTenantState();
//        Optional<User> optional = upmsRepository.findUserByUsername(username);
//        if (optional.isEmpty()) {
//            throw new AuthenticationServiceException("该账号不存在");
//        }
//        User user = optional.get();
//        if (!passwordEncoder.matches(password,user.getPassword()) ) {
//            throw new AuthenticationServiceException("密码错误");
//        }
//        return authority(user.getResources(),user.getId(),user.getUsername(),user.getPassword(),user.getTenantNumber());
//    }
//
//    @Override
//    public String login1(String phoneNumber, String openId) {
//        Optional<UserWeixin> optional = userWeiXinRepository.findByOpenId(openId);
//        if (optional.isEmpty()) {
//            UserWeiXinPo userWeiXinPo = userWeiXinRepository.saveUserWeiXin(
//                    UserWeiXinPo.builder()
//                            .openId(openId)
//                            .phoneNumber(StringUtils.hasText(phoneNumber)?phoneNumber:null)
//                            .tenantNumber(TenantNumberUtil.getTenantNumber())
//                            .build());
//            optional = Optional.ofNullable(userWeiXinMapper.toUserWeiXin(userWeiXinPo));
//        }
//
//        UserWeixin userWeixin = optional.get();
//        if (!StringUtils.hasText(userWeixin.getPhoneNumber())) {
//            if (!StringUtils.hasText(phoneNumber)) {
//                throw new AuthenticationServiceException("请绑定手机号码！");
//            }
//        }
//        if (StringUtils.hasText(phoneNumber) && !Objects.equals(phoneNumber,userWeixin.getPhoneNumber()) ){
//            userWeixin.setPhoneNumber(phoneNumber);
//            userWeiXinRepository.updateUserWeiXin(userWeiXinMapper.toUserWeiXinPo(userWeixin));
//        }
//
//        if (Objects.isNull(userWeixin)) {
//            throw new AuthenticationServiceException("用户鉴权失败！");
//        }
//        return authority(new ArrayList<Resources>(),userWeixin.getId(),userWeixin.getPhoneNumber(),"",userWeixin.getTenantNumber());
//    }
//
//    @Override
//    public void loginOut() {
//        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
//        if (Objects.nonNull(requestAttributes)) {
//            HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
//            if (Objects.nonNull(request)) {
//                Object obj = request.getHeader("token");
//                if (Objects.nonNull(obj)) {
//                    String token = String.valueOf(obj);
//                    if (StringUtils.hasText(token)) {
//                        YueChipRedisTokenStoreUtil.clean(token);
//                    }
//                }
//            }
//        }
//    }
//
//    private String authority(List<Resources> resourcesList,Long id, String username ,String password,Long tenantNumber) {
//        List<GrantedAuthority> authoritiesList = AuthorityUtils.createAuthorityList();
//        resourcesList.forEach(resources -> {
//            YueChipSimpleGrantedAuthority grantedAuthority = new YueChipSimpleGrantedAuthority();
//            grantedAuthority.setAuthority(resources.getCode());
//            authoritiesList.add(grantedAuthority);
//        });
//        YueChipAuthenticationToken token = new YueChipAuthenticationToken(username, authoritiesList);
//        SecurityContextHolder.getContext().setAuthentication(token);
//        YueChipUserDetails userDetails = new YueChipUserDetails(id,username,password, tenantNumber,authoritiesList);
//        YueChipRedisTokenStoreUtil.store(userDetails,token.getToken());
//        return token.getToken();
//    }
//
//    private void checkTenantState() {
//        Optional<TenantStatePo> optional = tenantRepository.findTenantStateFirst();
//        if (optional.isEmpty()) {
//            BusinessException.throwException("该租户状态不可用");
//        }else {
//            TenantStatePo tenantStatePo = optional.get();
//            if (Objects.equals(tenantStatePo.getState(), State.DISABLE)) {
//                BusinessException.throwException("该租户状态不可用");
//            }
//        }
//    }
}
