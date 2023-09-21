package com.yue.chip.upms.state.machine.test;

import com.yue.chip.exception.BusinessException;
import com.yue.chip.upms.infrastructure.dao.TestStateMachineDao;
import com.yue.chip.upms.infrastructure.po.TestStateMachinePo;
import jakarta.annotation.Resource;
import lombok.extern.log4j.Log4j2;
import org.springframework.statemachine.annotation.OnTransition;
import org.springframework.statemachine.annotation.WithStateMachine;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Mr.Liu
 * @date 2023/8/10 下午2:22
 */
@WithStateMachine
@Log4j2
public class BizBean {
    @Resource
    private TestStateMachineDao testStateMachineDao;
    @OnTransition(target = "ONLINE")
    @Transactional(rollbackFor = {Exception.class})
    public void online() {
        log.info("操作上线，待发布. target status:{}", States.PUBLISH_TODO.name());
    }

    @OnTransition(target = "PUBLISH")
    @Transactional(rollbackFor = {Exception.class})
    public void publish() {
        log.info("操作发布,发布完成. target status:{}", States.PUBLISH_DONE.name());
        testStateMachineDao.save(TestStateMachinePo.builder().build());
        BusinessException.throwException("测试状态机回滚事物");
    }

    @OnTransition(target = "ROLLBACK")
    @Transactional(rollbackFor = {Exception.class})
    public void rollback() {
        log.info("操作回滚,回到草稿状态. target status:{}", States.DRAFT.name());
    }
}
