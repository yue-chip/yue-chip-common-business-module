package com.yue.chip.upms.interfaces.facade.console.user;

import com.yue.chip.annotation.AuthorizationIgnore;
import com.yue.chip.constant.GlobalConstant;
import com.yue.chip.core.controller.BaseController;
import com.yue.chip.core.controller.impl.BaseControllerImpl;
import com.yue.chip.upms.domain.aggregates.User;
import com.yue.chip.upms.domain.repository.user.UserRepository;
import com.yue.chip.upms.infrastructure.assembler.user.UserMapper;
import com.yue.chip.upms.infrastructure.po.user.UserPo;
import com.yue.chip.utils.CurrentUserUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Optional;

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


    @GetMapping("/test")
    @PreAuthorize("hasAnyAuthority('"+ GlobalConstant.DEFAULT_ROLE_PREFIX +"test')")
    public User test() {
        Optional<User> optional = userRepository.find("admin");
        CurrentUserUtil.getCurrentUser();
        return optional.isPresent()?optional.get():null;
    }
}
