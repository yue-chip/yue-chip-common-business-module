package com.yue.chip.upms.interfaces.facade.console.upms;

import com.yue.chip.annotation.AuthorizationIgnore;
import com.yue.chip.core.IResultData;
import com.yue.chip.core.ResultData;
import com.yue.chip.upms.application.service.UpmsApplication;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.java.Log;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Mr.Liu
 * @date 2023/3/17 上午10:45
 */
@RestController()
@RequestMapping("/test")
@Validated
@Tag(name = "")
@Log
public class TestController {

    @Resource
    private UpmsApplication upmsApplication;

    @GetMapping("/test")
    @AuthorizationIgnore
    public IResultData test(String name){
        log.info("test");
        upmsApplication.test("刘方");
        return ResultData.builder().build();
    }
}
