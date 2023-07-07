package com.yue.chip.upms.interfaces.facade.console.upms;

import com.yue.chip.annotation.AuthorizationIgnore;
import com.yue.chip.core.IResultData;
import com.yue.chip.core.ResultData;
import com.yue.chip.core.controller.BaseController;
import com.yue.chip.core.controller.impl.BaseControllerImpl;
import com.yue.chip.upms.application.service.UpmsApplication;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import lombok.extern.java.Log;
import org.apache.skywalking.apm.toolkit.trace.Tags;
import org.apache.skywalking.apm.toolkit.trace.Trace;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Mr.Liu
 * @date 2023/3/17 上午10:45
 */
@RestController()
@RequestMapping("/test")
@Validated
@Tag(name = "测试")
@Log
public class TestController extends BaseControllerImpl implements BaseController {

    @Resource
    private UpmsApplication upmsApplication;

    @GetMapping("/test")
//    @PreAuthorize("@aps.hasPermission('ADD')")
    @AuthorizationIgnore
    public IResultData test(String name){
        log.info("test");
        upmsApplication.test("刘方");
        return ResultData.builder().build();
    }

    @PostMapping(value = "/test1")
    @AuthorizationIgnore
    @PreAuthorize("@aps.hasPermission('test')")
    public IResultData<String> dfd(String username,String password) {

        Map<String,String> map = new HashMap<>();
        return ResultData.builder().data(map).build();
    }
}
