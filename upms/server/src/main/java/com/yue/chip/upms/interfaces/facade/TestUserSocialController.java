package com.yue.chip.upms.interfaces.facade;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.yue.chip.annotation.AuthorizationIgnore;
import com.yue.chip.core.IResultData;
import com.yue.chip.core.ResultData;
import com.yue.chip.core.common.enums.State;
import com.yue.chip.core.common.enums.UserType;
import com.yue.chip.security.YueChipUserDetails;
import com.yue.chip.upms.domain.aggregates.User;
import com.yue.chip.upms.domain.repository.social.UserSocialRepository;
import com.yue.chip.upms.domain.repository.upms.UpmsRepository;
import com.yue.chip.upms.infrastructure.po.user.UserPo;
import com.yue.chip.upms.infrastructure.po.user.UserSocialPo;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.java.Log;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;

/**
 * @author zak
 */
@RestController
@RequestMapping("user/social")
@Validated
@Tag(name = "测试第三方用户")
@Log
public class TestUserSocialController {

    @Resource
    private UpmsRepository upmsRepository;
    @Resource
    private UserSocialRepository userSocialRepository;

    @PostMapping("save")
    @AuthorizationIgnore
    public IResultData save(@RequestBody Map<String, Object> body){
        String socialType = MapUtil.getStr(body, "socialType");
        String socialUid = MapUtil.getStr(body, "socialUid");
        String socialAcc = MapUtil.getStr(body, "socialAcc");
        String socialNickname = MapUtil.getStr(body, "socialNickname");

        // 生成一个新的用户
        String name = StrUtil.format("{}_{}", socialType, RandomUtil.randomStringUpper(6));
        UserPo userPo = new UserPo();
        userPo.setTenantNumber(null);
        userPo.setName(name);
        userPo.setUsername(name);
        userPo.setState(State.NORMAL);
        userPo.setNickname(socialNickname);
        userPo.setUserType(UserType.ORDINARY);
        userPo.setPassword("123456");
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
        YueChipUserDetails userDetails = new YueChipUserDetails(currUser.getId(), currUser.getUsername(), currUser.getPassword(), currUser.getTenantNumber(), null);

        return ResultData.builder().data(userDetails).build();
    }


}
