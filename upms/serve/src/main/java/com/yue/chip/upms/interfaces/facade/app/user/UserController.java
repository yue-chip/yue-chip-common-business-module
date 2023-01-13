package com.yue.chip.upms.interfaces.facade.app.user;

import com.yue.chip.core.controller.BaseController;
import com.yue.chip.core.controller.impl.BaseControllerImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Mr.Liu
 * @date 2023/1/12 下午3:18
 * @description UserController
 */
@RestController("userAppController")
@RequestMapping("/user/app")
@Validated
@Tag(name = "用户管理-app端后台")
public class UserController extends BaseControllerImpl implements BaseController {
}
