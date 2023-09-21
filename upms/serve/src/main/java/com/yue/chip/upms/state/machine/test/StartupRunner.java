package com.yue.chip.upms.state.machine.test;

import jakarta.annotation.Resource;
import org.springframework.boot.CommandLineRunner;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Component;

/**
 * @author Mr.Liu
 * @date 2023/8/10 下午2:26
 */
//@Component
public class StartupRunner implements CommandLineRunner {

    @Resource
    StateMachine<States, Events> stateMachine;

    @Override
    public void run(String... args) throws Exception {
        stateMachine.start();
        stateMachine.sendEvent(Events.ONLINE);
        stateMachine.sendEvent(Events.PUBLISH);
        stateMachine.sendEvent(Events.ROLLBACK);
    }
}
