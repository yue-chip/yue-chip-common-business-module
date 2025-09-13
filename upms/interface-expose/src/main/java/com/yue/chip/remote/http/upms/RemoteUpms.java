package com.yue.chip.remote.http.upms;

import com.yue.chip.core.ResultData;
import com.yue.chip.remote.http.upms.vo.UserExposeVo;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange("${http.exchange.host}")
public interface RemoteUpms extends RemoteUpmsDefinition {

    @Override
    @GetExchange(PREFIX+FIND_USERNAME)
    ResultData<UserExposeVo> findByUsername(@RequestParam("username") String username);

    @Override
    @GetExchange(PREFIX+FIND_PHONE)
    ResultData<UserExposeVo> findByPhoneNumber(@RequestParam("phoneNumber") String phoneNumber);

    @Override
    @GetExchange(PREFIX+FIND_EMAIL)
    ResultData<UserExposeVo> findByEmail(@RequestParam("email")String email);

    @Override
    @GetExchange(PREFIX+REGISTER)
    ResultData register(@RequestParam("phoneNumber")String phoneNumber, @RequestParam("password")String password,@RequestParam("name") String name,@RequestParam("id")  Long id);

    @Override
    @GetExchange(PREFIX+REGISTER_EMAIL)
    ResultData registerByEmail(@RequestParam("email")String email, @RequestParam("password")String password, @RequestParam("name")String name, @RequestParam("id")Long id);

    @Override
    @GetExchange(PREFIX+REVOKED)
    ResultData revoked(@RequestParam("userId")Long userId);

    @Override
    @GetExchange(PREFIX+UPDATE)
    ResultData updateUserPassword(@RequestParam("userId")Long userId, @RequestParam("password")String password);
}
