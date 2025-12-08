package com.yue.chip.upms.domain.service.upms.impl;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.yue.chip.core.common.enums.State;
import com.yue.chip.core.common.enums.UserType;
import com.yue.chip.security.YueChipSimpleGrantedAuthority;
import com.yue.chip.security.YueChipUserDetails;
import com.yue.chip.upms.domain.aggregates.Organizational;
import com.yue.chip.upms.domain.aggregates.Resources;
import com.yue.chip.upms.domain.aggregates.Role;
import com.yue.chip.upms.domain.aggregates.User;
import com.yue.chip.upms.domain.repository.organizational.OrganizationalRepository;
import com.yue.chip.upms.domain.repository.social.UserSocialRepository;
import com.yue.chip.upms.domain.repository.upms.UpmsRepository;
import com.yue.chip.upms.domain.service.upms.UpmsDomainService;
import com.yue.chip.upms.infrastructure.po.organizational.OrganizationalUserPo;
import com.yue.chip.upms.infrastructure.po.role.RoleResourcesPo;
import com.yue.chip.upms.infrastructure.po.user.UserPo;
import com.yue.chip.upms.infrastructure.po.user.UserSocialPo;
import com.yue.chip.utils.AssertUtil;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author Mr.Liu
 * @date 2023/3/6 上午11:29
 */
@Service
public class UpmsDomainServiceImpl implements UpmsDomainService {

    @Resource
    private UpmsRepository upmsRepository;
    @Resource
    private UserSocialRepository userSocialRepository;

    @Resource
    private OrganizationalRepository organizationalRepository;

    @Override
    public void roleResources(Long roleId, Long[] resourcesIds) {
        List<RoleResourcesPo> list = new ArrayList<>();
        if (Objects.nonNull(resourcesIds)) {
            List<Long> newResourcesIds = new ArrayList<>();
            newResourcesIds.addAll(Arrays.stream(resourcesIds).toList());
            for (Long id : resourcesIds) {
                List<Long> allParentId = getAllParentId(id);
                newResourcesIds.forEach(i->{
                    if (allParentId.contains(i)) {
                        allParentId.remove(i);
                    }
                });
                newResourcesIds.addAll(allParentId);
            }

            newResourcesIds.forEach( i ->{
                RoleResourcesPo roleResourcesPo = RoleResourcesPo.builder()
                        .resourcesId(i)
                        .roleId(roleId)
                        .build();
                list.add(roleResourcesPo);
            });
            upmsRepository.saveAllRoleResources(list);
        }
    }

    @Override
    public void userOrganizational(Long userId, Long organizationalId) {
        Optional<Organizational> optional = organizationalRepository.findByUserId(userId);
        if (optional.isPresent()) {
            Organizational organizational = optional.get();
            if (!Objects.equals(organizationalId,organizational.getId()) ){
                //清楚机构负责人
                organizationalRepository.deleteLeader(userId);
            }
        }
        organizationalRepository.deleteOrganizationalByUserId(userId);
        if (Objects.nonNull(organizationalId)) {
            organizationalRepository.saveOrganizationalUser(
                    OrganizationalUserPo.builder()
                    .organizationalId(organizationalId)
                    .userId(userId)
                    .build()
            );
        }
    }

    @Override
    public void userOrganizationals(Long userId, List<Long> organizationalId) {
        List<Organizational> organizationalList = organizationalRepository.findAllByUserId(userId);
        if (!organizationalList.isEmpty()) {
            organizationalList.forEach(organizational -> {
                if (!Objects.equals(organizationalId,organizational.getId()) ){
                    //清楚机构负责人
                    organizationalRepository.deleteLeader(userId);
                }
            });
        }
        organizationalRepository.deleteOrganizationalByUserId(userId);
        if (Objects.nonNull(organizationalId) && !organizationalId.isEmpty()) {
            organizationalId.forEach(id -> {
                organizationalRepository.saveOrganizationalUser(
                        OrganizationalUserPo.builder()
                                .organizationalId(id)
                                .userId(userId)
                                .build()
                );
            });
        }
    }

    public void checkResourcesNameIsExist(String name, @NotNull Long parentId, Long id) {
        Resources resources = Resources.builder()
                .name(name)
                .id(id)
                .parentId(parentId)
                .build();
        Boolean isExist = resources.checkNameIsExist();
        AssertUtil.isFalse(isExist,"该名称已经存");
    }

    @Override
    public void checkResourcesCodeIsExist(String code, Long id) {
        Resources resources = Resources.builder()
                .code(code)
                .id(id)
                .build();
        Boolean isExist = resources.checkCodeIsExist();
        AssertUtil.isFalse(isExist,"该编码已经存");
    }

    @Override
    public void checkResourcesUrlIsExist(String url, Long id) {
        Resources resources = Resources.builder()
                .url(url)
                .id(id)
                .build();
        Boolean isExist = resources.checkUrlIsExist();
        AssertUtil.isFalse(isExist,"该url已经存");
    }

    @Override
    public YueChipUserDetails loadUserByUsername(String username) {
        Optional<User> optional = upmsRepository.findUserByUsername(username);
        if (!optional.isPresent()){
            return null;
        }
        User user = optional.get();
        YueChipUserDetails userDetails = new YueChipUserDetails(user.getId(),user.getUsername(),user.getPassword(),user.getTenantNumber(),getUserGrantedAuthority(user.getRoles()));
        return userDetails;
    }

    @Override
    public YueChipUserDetails loadUserByPhoneNumber(String phoneNumber) {
        Optional<User> optional = upmsRepository.findUserByPhoneNumber(phoneNumber);
        if (!optional.isPresent()){
            return null;
        }
        User user = optional.get();
        YueChipUserDetails userDetails = new YueChipUserDetails(user.getId(),user.getUsername(),user.getPassword(),user.getTenantNumber(),getUserGrantedAuthority(user.getRoles()));
        return userDetails;
    }

    @Override
    public YueChipUserDetails loadUserByEmail(String email) {
        Optional<User> userOptional = upmsRepository.findUserByEmail(email);
        if (userOptional.isEmpty()) {
            return null;
        }
        User user = userOptional.get();
        return new YueChipUserDetails(user.getId(),user.getUsername(),user.getPassword(),user.getTenantNumber(),getUserGrantedAuthority(user.getRoles()));
    }

    @Override
    public YueChipUserDetails loadUserByAccount(String account) {
        Optional<User> userOptional = upmsRepository.findUserByAccount(account);
        if (userOptional.isEmpty()) {
            return null;
        }
        User user = userOptional.get();
        return new YueChipUserDetails(user.getId(),user.getUsername(),user.getPassword(),user.getTenantNumber(),getUserGrantedAuthority(user.getRoles()));
    }

    @Override
    public YueChipUserDetails loadUserBySocialTypeAndSocialUid(String socialType, String socialUid) {
        Optional<User> optional = upmsRepository.findUserBySocialTypeAndSocialUid(socialType, socialUid);
        if (optional.isEmpty()){
            return null;
        }
        User user = optional.get();
        return new YueChipUserDetails(user.getId(), user.getUsername(), user.getPassword(), user.getTenantNumber(), getUserGrantedAuthority(user.getRoles()));
    }

    @Override
    public YueChipUserDetails saveUserSocial(String socialType, String socialUid, String socialAcc, String socialNickname) {
        // 生成一个新的用户
        String name = StrUtil.format("{}_{}", socialType.toUpperCase(), RandomUtil.randomStringUpper(6));
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
        return new YueChipUserDetails(currUser.getId(), currUser.getUsername(), currUser.getPassword(), currUser.getTenantNumber(), getUserGrantedAuthority(currUser.getRoles()));
    }

    private List<Long> getAllParentId(Long resourcesId) {
        List<Resources> list = new ArrayList<>();
        getParent(resourcesId,list);
        List<Long> returnList = new ArrayList<>();
        list.forEach(resources -> {
            returnList.add(resources.getId());
        });
        return returnList;
    }

    private void getParent(Long resourcesId,List<Resources> list) {
        Optional<Resources> optional = upmsRepository.findResourcesById(resourcesId);
        if (optional.isPresent()) {
            Optional<Resources> optionalResources = optional.get().getParent();
            if (optionalResources.isPresent()){
                list.add(optionalResources.get());
                getParent(optionalResources.get().getId(),list);
            }
        }
    }

    /**
     * 设置用户权限
     * @param roles
     * @return
     */
    private List<YueChipSimpleGrantedAuthority> getUserGrantedAuthority(List<Role> roles){
        List<YueChipSimpleGrantedAuthority> listGrantedAuthority = new ArrayList<YueChipSimpleGrantedAuthority>();
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


}
