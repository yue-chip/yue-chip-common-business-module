package com.yue.chip.upms.application.expose.impl.test;

import com.yue.chip.test.TestExpose;
import com.yue.chip.upms.assembler.role.RoleMapper;
import com.yue.chip.upms.definition.user.UserDefinition;
import com.yue.chip.upms.domain.repository.upms.UpmsRepository;
import com.yue.chip.upms.interfaces.dto.role.RoleAddDto;
import javax.annotation.Resource;
import lombok.extern.java.Log;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.skywalking.apm.toolkit.trace.Tag;
import org.apache.skywalking.apm.toolkit.trace.Tags;
import org.apache.skywalking.apm.toolkit.trace.Trace;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author Mr.Liu
 * @date 2023/3/17 下午1:54
 */
@DubboService()
@Log
public class TestExposeImpl implements TestExpose {

    @Resource
    private UpmsRepository upmsRepository;

    @Resource
    private RoleMapper roleMapper;

    @Override
    @Trace
    //@Tags({//@Tag(key = "code",value = "arg[0]"),//@Tag(key = "return",value = "returnedObj")})
    public Map<String, String> test(String code) {
        log.info(code);
        Map<String,String> map = new HashMap<>();
        map.put("code",code);
        RoleAddDto roleAddDto = RoleAddDto.builder()
                .name(UUID.randomUUID().toString())
                .code(UUID.randomUUID().toString())
                .build();
        upmsRepository.saveRole(roleMapper.toRolePo(roleAddDto));
        return map;
    }

    @Override
    public UserDefinition test1(String code) {
        return UserDefinition.builder().name("test").build();
    }
}
