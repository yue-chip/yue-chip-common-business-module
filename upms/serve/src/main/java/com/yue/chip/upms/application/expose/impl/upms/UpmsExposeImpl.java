package com.yue.chip.upms.application.expose.impl.upms;

import com.yue.chip.upms.UpmsExpose;
import com.yue.chip.upms.definition.organizational.OrganizationalDefinition;
import com.yue.chip.upms.domain.repository.upms.UpmsRepository;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author xianming.chen
 * @description: TODO
 * @date 2023-10-28
 */
@DubboService(interfaceClass = UpmsExpose.class)
public class UpmsExposeImpl implements UpmsExpose {

    @Resource
    private UpmsRepository upmsRepository;

}
