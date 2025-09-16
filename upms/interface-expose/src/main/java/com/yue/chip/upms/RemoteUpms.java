package com.yue.chip.upms;

import com.yue.chip.core.ResultData;
import com.yue.chip.upms.vo.UserExposeVo;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;
import org.springframework.web.service.annotation.PutExchange;

import java.util.List;

@HttpExchange("${http.exchange.host}")
public interface RemoteUpms extends RemoteUpmsDefinition{

    @Override
    @GetExchange(PREFIX+FIND)
    ResultData<List<UserExposeVo>> find(@RequestParam(value = "userIds") List<Long> userIds);

    @Override
    @GetExchange(PREFIX+FIND_1)
    ResultData<UserExposeVo> find(@RequestParam(value = "userId")Long userId);

    @Override
    @GetExchange(PREFIX+FIND_2)
    ResultData<UserExposeVo> find(@RequestParam(value = "username")String username);

    @Override
    @GetExchange(PREFIX+FIND_3)
    ResultData<UserExposeVo> find(@RequestParam(value = "id")Long id,@RequestParam(value = "tenantNumber") Long tenantNumber);

    @Override
    @GetExchange(PREFIX+FIND_PHONE)
    ResultData<UserExposeVo> findPhoneNumber(@RequestParam(value = "phoneNumber")String phoneNumber);

    @Override
    @GetExchange(PREFIX+FIND_EMAIL)
    ResultData<UserExposeVo> findEmail(@RequestParam(value = "email")String email);

    @Override
    @PostExchange(PREFIX+REGISTER)
    ResultData register(@RequestParam(value = "phoneNumber")String phoneNumber,
                        @RequestParam(value = "password")String password,
                        @RequestParam(value = "name") String name,
                        @RequestParam(value = "id")Long id);

    @Override
    @PostExchange(PREFIX+REGISTER_EMAIL)
    ResultData registerByEmail(@RequestParam(value = "email")String email,
                               @RequestParam(value = "password")String password,
                               @RequestParam(value = "name")String name,
                               @RequestParam(value = "id") Long id);

    @Override
    @GetExchange(PREFIX+LOGOUT_USER)
    ResultData logoutUser(@RequestParam(value = "userId")Long userId);

    @Override
    @PutExchange(PREFIX+UPDATE_PASSWORD)
    ResultData updateUserPassword(@RequestParam(value = "userId")Long userId, @RequestParam(value = "password")String password);
}
