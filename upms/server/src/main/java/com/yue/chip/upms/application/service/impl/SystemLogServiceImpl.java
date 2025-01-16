//package com.yue.chip.upms.application.service.impl;
//
//import com.security.log.LogExposeService;
//import com.yue.chip.core.SystemLogService;
//import org.apache.dubbo.config.annotation.DubboReference;
//import org.springframework.context.annotation.Primary;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDateTime;
//
///**
// * @author jiacheng.liao on 2025/1/8
// */
//@Service(value = "SecurityLogService")
//@Primary
//public class SystemLogServiceImpl implements SystemLogService {
//
//    @DubboReference
//    private LogExposeService logExposeService;
//
//    @Override
//    public void save(String actionName) {
//        logExposeService.saveLog(actionName);
//    }
//
//    @Override
//    public void saveLog(String actionName, Long userId, String type) {
//        logExposeService.saveLogId(actionName, userId, type);
//    }
//
//    @Override
//    public Page<? extends Object> list(LocalDateTime localDateTime, LocalDateTime localDateTime1, Pageable pageable) {
//        return null;
//    }
//}
