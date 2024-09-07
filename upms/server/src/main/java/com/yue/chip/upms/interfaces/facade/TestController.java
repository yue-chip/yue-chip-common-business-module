package com.yue.chip.upms.interfaces.facade;

import com.yue.chip.annotation.AuthorizationIgnore;
import com.yue.chip.common.business.expose.file.FileExposeService;
import com.yue.chip.common.business.expose.sms.SmsExposeService;
import com.yue.chip.core.IPageResultData;
import com.yue.chip.core.IResultData;
import com.yue.chip.core.ResultData;
import com.yue.chip.upms.application.service.TestApplicationService;
import com.yue.chip.upms.application.service.UpmsApplication;
import com.yue.chip.upms.domain.aggregates.User;
import com.yue.chip.upms.domain.repository.upms.UpmsRepository;
import com.yue.chip.upms.interfaces.vo.user.UserVo;
import com.yue.chip.utils.Sm4Util;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Mr.Liu
 * @date 2023/3/17 上午10:45
 */
@RestController()
@RequestMapping()
@Validated
//@Tag(name = "测试")
//@Log
public class TestController  {

    @Resource
    private UpmsApplication upmsApplication;

    @Resource
    private TestApplicationService testApplicationService;

    @DubboReference
    private SmsExposeService smsExposeService;

    @DubboReference
    private FileExposeService fileExposeService;

    @Resource
    private UpmsRepository upmsRepository;


    @GetMapping("/sms")
//    @PreAuthorize("@aps.hasPermission('ADD')")
    @AuthorizationIgnore
    //@Operation(summary = "测试-sms", description = "测试-sms")
    public IResultData sms(){
        smsExposeService.sendSms("1400813276", "小未科技", "1795028", "test^test^test", "+8618928025540");
        return ResultData.builder().build();
    }

    @GetMapping("/test")
//    @PreAuthorize("@aps.hasPermission('ADD')")
    @AuthorizationIgnore
    //@Operation(summary = "测试-1", description = "测试-1")
    public IResultData test(String name){
//        log.info("test");
        upmsApplication.test("刘方");
        return ResultData.builder().build();
    }

    @GetMapping("/test/file")
//    @PreAuthorize("@aps.hasPermission('ADD')")
    @AuthorizationIgnore
    //@Operation(summary = "测试文件", description = "测试文件")
    public IResultData testFile(String name){
        Map map = fileExposeService.getUrl(14L,"storePhoto","store",null);
//        log.info(map.toString());
        return ResultData.builder().build();
    }

    @PostMapping(value = "/test1")
    @AuthorizationIgnore
    @PreAuthorize("@aps.hasPermission('test')")
    //@Operation(summary = "测试-权限测试", description = "测试-权限测试")
    public IResultData<String> test1(String username,String password) {

        Map<String,String> map = new HashMap<>();
        return ResultData.builder().data(map).build();
    }

    @GetMapping("/mock")
//    @PreAuthorize("@aps.hasPermission('ADD')")
    @AuthorizationIgnore
    //@Operation(summary = "测试-接口mock测试", description = "测试-接口mock测试")
    public IResultData<List<UserVo>> testMock(String name){
        return ResultData.builder().data(new PodamFactoryImpl().manufacturePojo(List.class,UserVo.class)).build();
    }

    @GetMapping("/mock1")
//    @PreAuthorize("@aps.hasPermission('ADD')")
    @AuthorizationIgnore
    //@Operation(summary = "测试-接口mock测试1", description = "测试-接口mock测试1")
    public IPageResultData<List<UserVo>> testMock1(String name){
//        return PageResultData.builder().data(JMockData.mock(new TypeReference<List<UserVo>>(){})).build();
//        List<User> listGrid = new PodamFactoryImpl().manufacturePojo(List.class, User.class);
//        return PageResultData.builder().data(new PodamFactoryImpl().manufacturePojo(List.class,UserVo.class)).build();
        return null;
    }

    @GetMapping("/jiami")
    @AuthorizationIgnore
    public IResultData jiami(){
        List<User> list = upmsRepository.findAll();
        list.forEach(user -> {
            upmsRepository.updateUserPassword(user.getId(), Sm4Util.encryptEcb("", user.getPassword()));
        });
        return ResultData.builder().build();
    }

}
