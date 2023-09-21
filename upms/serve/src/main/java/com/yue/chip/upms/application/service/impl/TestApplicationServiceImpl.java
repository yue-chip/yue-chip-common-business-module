package com.yue.chip.upms.application.service.impl;

import com.yue.chip.exception.BusinessException;
import com.yue.chip.upms.application.service.TestApplicationService;
import com.yue.chip.upms.infrastructure.dao.TestStateMachineDao;
import com.yue.chip.upms.infrastructure.po.TestStateMachinePo;
import com.yue.chip.upms.state.machine.test.Events;
import com.yue.chip.upms.state.machine.test.States;
import jakarta.annotation.Resource;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Mr.Liu
 * @date 2023/8/10 下午2:44
 */
@Service
public class TestApplicationServiceImpl implements TestApplicationService {

    @Resource
    private TestStateMachineDao testStateMachineDao;

    @Resource
    private StateMachine<States, Events> stateMachine;

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void testStateMachine() {
        testStateMachineDao.save(TestStateMachinePo.builder().build());
        stateMachine.start();
        stateMachine.sendEvent(Events.ONLINE);
        stateMachine.sendEvent(Events.PUBLISH);
        stateMachine.sendEvent(Events.ROLLBACK);

    }
}
