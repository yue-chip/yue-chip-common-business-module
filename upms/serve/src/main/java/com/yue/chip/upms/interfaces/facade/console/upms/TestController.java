package com.yue.chip.upms.interfaces.facade.console.upms;

import com.yue.chip.annotation.AuthorizationIgnore;
import com.yue.chip.core.IPageResultData;
import com.yue.chip.core.IResultData;
import com.yue.chip.core.PageResultData;
import com.yue.chip.core.ResultData;
import com.yue.chip.core.controller.BaseController;
import com.yue.chip.core.controller.impl.BaseControllerImpl;
import com.yue.chip.upms.application.service.UpmsApplication;
import com.yue.chip.upms.interfaces.vo.user.UserVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.java.Log;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import java.util.HashMap;
import java.util.List;
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
    @Operation(summary = "测试-1", description = "测试-1")
    public IResultData test(String name){
        log.info("test");
        upmsApplication.test("刘方");
        return ResultData.builder().build();
    }

    @PostMapping(value = "/test1")
    @AuthorizationIgnore
    @PreAuthorize("@aps.hasPermission('test')")
    @Operation(summary = "测试-权限测试", description = "测试-权限测试")
    public IResultData<String> dfd(String username,String password) {

        Map<String,String> map = new HashMap<>();
        return ResultData.builder().data(map).build();
    }

    @GetMapping("/test/mock")
//    @PreAuthorize("@aps.hasPermission('ADD')")
    @AuthorizationIgnore
    @Operation(summary = "测试-接口mock测试", description = "测试-接口mock测试")
    public IResultData<List<UserVo>> testMock(String name){
        return ResultData.builder().data(new PodamFactoryImpl().manufacturePojo(List.class,UserVo.class)).build();
    }

    @GetMapping("/test/mock1")
//    @PreAuthorize("@aps.hasPermission('ADD')")
    @AuthorizationIgnore
    @Operation(summary = "测试-接口mock测试1", description = "测试-接口mock测试1")
    public IPageResultData<List<UserVo>> testMock1(String name){
//        return PageResultData.builder().data(JMockData.mock(new TypeReference<List<UserVo>>(){})).build();
//        List<User> list = new PodamFactoryImpl().manufacturePojo(List.class, User.class);
        return PageResultData.builder().data(new PodamFactoryImpl().manufacturePojo(List.class,UserVo.class)).build();

    }

}
