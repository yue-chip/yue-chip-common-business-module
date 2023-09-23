package com.yue.chip.upms.interfaces.facade.app.upms;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Mr.Liu
 * @date 2023/1/12 下午3:18
 * @description UpmsController
 */
@RestController()
@RequestMapping("/app")
@Validated
@Tag(name = "角色&用户&资源-app端后台")
public class UpmsAppController {
}
