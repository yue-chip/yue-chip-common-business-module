package com.yue.chip.upms.interfaces.facade.console.user;

import com.yue.chip.constant.GlobalConstant;
import com.yue.chip.core.IResultData;
import com.yue.chip.core.ResultData;
import com.yue.chip.core.controller.BaseController;
import com.yue.chip.core.controller.impl.BaseControllerImpl;
import com.yue.chip.upms.domain.aggregates.Resources;
import com.yue.chip.upms.domain.aggregates.User;
import com.yue.chip.upms.domain.repository.resources.ResourcesRepository;
import com.yue.chip.upms.domain.repository.user.UserRepository;
import com.yue.chip.upms.enums.Scope;
import com.yue.chip.upms.infrastructure.po.user.UserPo;
import com.yue.chip.utils.CurrentUserUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Mr.Liu
 * @date 2023/1/12 下午3:18
 * @description UserController
 */
@RestController("userConsoleController")
@RequestMapping("/user/console")
@Validated
@Tag(name = "用户管理-pc端后台")
public class UserController extends BaseControllerImpl implements BaseController {

    @Resource
    private UserRepository userRepository;

    @DubboReference
    private UserDetailsService userDetailsService;

    @Resource
    private ResourcesRepository resourcesRepository;


    @GetMapping("/test")
    @PreAuthorize("hasAnyAuthority('"+ GlobalConstant.DEFAULT_ROLE_PREFIX +"test')")
    public User test() {
//        Optional<User> optional = userRepository.find("admin");
//        CurrentUserUtil.getCurrentUser();
//        return optional.isPresent()?optional.get():null;
        userRepository.save(UserPo.builder().username(RandomStringUtils.random(5)).password(RandomStringUtils.random(5)).name(RandomStringUtils.random(5)).build());
        return null;
    }

    @GetMapping("/currentUser/permissions")
    @Operation(summary = "获取当前用户的权限(菜单，资源)", description = "获取当前用户的权限(菜单，资源)")
    public IResultData<List<Resources>> userPermissions(){
        Long userId = CurrentUserUtil.getCurrentUserId();
        ResultData resultData = ResultData.builder()
            .data(resourcesRepository.findResourcesToTreeList(userId,0L, Scope.CONSOLE))
            .build();
        return resultData;
    }

}
