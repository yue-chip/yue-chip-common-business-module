package com.yue.chip.upms;

import com.yue.chip.core.PageResultData;
import com.yue.chip.core.ResultData;
import com.yue.chip.core.YueChipPage;
import com.yue.chip.core.common.enums.State;
import com.yue.chip.core.common.enums.UserType;
import com.yue.chip.upms.vo.UserExposeVo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;
import org.springframework.web.service.annotation.PutExchange;

import java.util.List;
import java.util.Map;

@HttpExchange("${http.exchange.host.host-business}")
public interface RemoteUpms extends RemoteUpmsDefinition{

    @Override
    @GetExchange(PREFIX+FIND)
    ResultData<List<UserExposeVo>> find(@RequestParam(value = "userIds",required = false) List<Long> userIds);

    @Override
    @GetExchange(PREFIX+FIND_1)
    ResultData<UserExposeVo> find(@RequestParam(value = "userId",required = false)Long userId);

    @Override
    @GetExchange(PREFIX+FIND_2)
    ResultData<UserExposeVo> find(@RequestParam(value = "username",required = false)String username);

    @Override
    @GetExchange(PREFIX+FIND_3)
    ResultData<UserExposeVo> find(@RequestParam(value = "id",required = false)Long id,@RequestParam(value = "tenantNumber",required = false) Long tenantNumber);

    @Override
    @GetExchange(PREFIX+FIND_4)
    PageResultData<List<UserExposeVo>> find(@RequestParam(value = "nameLike",required = false)String nameLike,
                                            @RequestParam(value = "userType",required = false) UserType userType,
                                            @RequestAttribute(required = false) YueChipPage yueChipPage,
                                            @RequestParam(required = false) Map<String, Object> params);

    @Override
    @GetExchange(PREFIX+FIND_5)
    PageResultData<List<UserExposeVo>> find(@RequestAttribute(required = false) YueChipPage yueChipPage,
                                            @RequestParam(required = false) Map<String, Object> params);

    @Override
    @GetExchange(PREFIX+FIND_6)
    PageResultData<List<UserExposeVo>> find(@RequestParam(value = "name",required = false)String name,
                                            @RequestParam(value = "nickname",required = false)String nickname,
                                            @RequestParam(value = "username",required = false)String username,
                                            @RequestParam(value = "phoneNumber",required = false) String phoneNumber,
                                            @RequestParam(value = "email",required = false)String email,
                                            @RequestParam(value = "state",required = false)State state,
                                            @RequestParam(value = "nameLike",required = false)String nameLike,
                                            @RequestAttribute(required = false) YueChipPage yueChipPage,
                                            @RequestParam(required = false) Map<String, Object> params);

    @Override
    @GetExchange(PREFIX+FIND_PHONE)
    ResultData<UserExposeVo> findPhoneNumber(@RequestParam(value = "phoneNumber",required = false)String phoneNumber);

    @Override
    @GetExchange(PREFIX+FIND_EMAIL)
    ResultData<UserExposeVo> findEmail(@RequestParam(value = "email",required = false)String email);

    @Override
    @PostExchange(PREFIX+REGISTER)
    ResultData register(@RequestParam(value = "phoneNumber",required = false)String phoneNumber,
                        @RequestParam(value = "password",required = false)String password,
                        @RequestParam(value = "name",required = false) String name,
                        @RequestParam(value = "id",required = false)Long id);

    @Override
    @PostExchange(PREFIX+REGISTER_EMAIL)
    ResultData registerByEmail(@RequestParam(value = "email",required = false)String email,
                               @RequestParam(value = "password",required = false)String password,
                               @RequestParam(value = "name",required = false)String name,
                               @RequestParam(value = "id",required = false) Long id);

    @Override
    @GetExchange(PREFIX+LOGOUT_USER)
    ResultData logoutUser(@RequestParam(value = "userId",required = false)Long userId);

    @Override
    @PutExchange(PREFIX+UPDATE_PASSWORD)
    ResultData updateUserPassword(@RequestParam(value = "userId",required = false)Long userId,
                                  @RequestParam(value = "password",required = false)String password);

    @Override
    @GetExchange(PREFIX + VERIFY_USERIDS)
    ResultData<List<Long>> verifyUserIds(@RequestParam(value = "userIds") List<Long> userIds);
}
