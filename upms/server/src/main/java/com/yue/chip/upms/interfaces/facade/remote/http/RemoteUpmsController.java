package com.yue.chip.upms.interfaces.facade.remote.http;

import com.yue.chip.core.PageResultData;
import com.yue.chip.core.ResultData;
import com.yue.chip.core.YueChipPage;
import com.yue.chip.core.common.enums.UserType;
import com.yue.chip.upms.RemoteUpmsDefinition;
import com.yue.chip.upms.application.service.UpmsApplication;
import com.yue.chip.upms.assembler.organizational.GridMapper;
import com.yue.chip.upms.assembler.organizational.OrganizationalMapper;
import com.yue.chip.upms.assembler.organizational.OrganizationalUserMapper;
import com.yue.chip.upms.assembler.user.UserMapper;
import com.yue.chip.upms.domain.aggregates.User;
import com.yue.chip.upms.domain.repository.organizational.OrganizationalRepository;
import com.yue.chip.upms.domain.repository.upms.UpmsRepository;
import com.yue.chip.upms.vo.UserExposeVo;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.java.Log;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.PostExchange;
import org.springframework.web.service.annotation.PutExchange;

import java.util.List;
import java.util.Optional;

@RestController()
@RequestMapping("")
@Validated
@Tag(name = "")
@Log
public class RemoteUpmsController implements RemoteUpmsDefinition {

    @Resource
    private UserMapper userMapper;

    @Resource
    private UpmsRepository upmsRepository;

    @Resource
    private OrganizationalRepository organizationalRepository;

    @Resource
    private OrganizationalMapper organizationalMapper;

    @Resource
    private GridMapper gridMapper;
    @Resource
    private OrganizationalUserMapper organizationalUserMapper;
    @Resource
    private UpmsApplication upmsApplication;

    @Override
    @GetExchange(FIND)
    public ResultData<List<UserExposeVo>> find(List<Long> userIds) {
        ResultData.ResultDataBuilder<List<UserExposeVo>> builder = ResultData.builder();
        List<UserExposeVo> list = userMapper.toUserExposeVo(upmsRepository.findUserByIds(userIds));
        return builder.data(list).build();
    }

    @Override
    @GetExchange(FIND_1)
    public ResultData<UserExposeVo> find(Long userId) {
        ResultData.ResultDataBuilder<UserExposeVo> builder = ResultData.builder();
        Optional<User> optional = upmsRepository.findUserById(userId);
        if (optional.isPresent()) {
            return builder.data(userMapper.toUserExposeVo(optional.get())).build();
        }
        return builder.build();
    }

    @Override
    @GetExchange(FIND_2)
    public ResultData<UserExposeVo> find(String username) {
        ResultData.ResultDataBuilder<UserExposeVo> builder = ResultData.builder();
        Optional<User> optional = upmsRepository.findUserByUsername(username);
        if (optional.isPresent()) {
            return builder.data(userMapper.toUserExposeVo(optional.get())).build();
        }
        return builder.build();
    }

    @Override
    @GetExchange(FIND_4)
    public PageResultData<List<UserExposeVo>> find(String nameLike, UserType userType, YueChipPage yueChipPage) {
        Page<User> page = upmsRepository.findByUsernameOrPhoneNumberOrEmailAndUserType(nameLike, userType, yueChipPage);
        return (PageResultData<List<UserExposeVo>>) PageResultData.convert(page,userMapper.toUserExposeVo(page.getContent()));
    }

    @Override
    @GetExchange(FIND_3)
    public ResultData<UserExposeVo> find(Long id, Long tenantNumber) {
        ResultData.ResultDataBuilder<UserExposeVo> builder = ResultData.builder();
        Optional<User> optional = upmsRepository.findByIdAndTenantNumber(id,tenantNumber);
        if (optional.isPresent()) {
            return builder.data(userMapper.toUserExposeVo(optional.get())).build();
        }
        return builder.build();
    }

    @Override
    @GetExchange(FIND_PHONE)
    public ResultData<UserExposeVo> findPhoneNumber(String phoneNumber) {
        ResultData.ResultDataBuilder<UserExposeVo> builder = ResultData.builder();
        Optional<User> optional = upmsRepository.findUserByPhoneNumber(phoneNumber);
        if (optional.isPresent()) {
            return builder.data(userMapper.toUserExposeVo(optional.get())).build();
        }
        return builder.build();
    }

    @Override
    @GetExchange(FIND_EMAIL)
    public ResultData<UserExposeVo> findEmail(String email) {
        ResultData.ResultDataBuilder<UserExposeVo> builder = ResultData.builder();
        Optional<User> optional = upmsRepository.findUserByEmail(email);
        if (optional.isPresent()) {
            return builder.data(userMapper.toUserExposeVo(optional.get())).build();
        }
        return builder.build();
    }

    @Override
    @PostExchange(REGISTER)
    public ResultData register(String phoneNumber, String password, String name, Long id) {
        organizationalRepository.register(phoneNumber, password, name, id);
        return ResultData.builder().build();
    }

    @Override
    @PostExchange(REGISTER_EMAIL)
    public ResultData registerByEmail(String email, String password, String name, Long id) {
        organizationalRepository.registerByEmail(email, password, name, id);
        return ResultData.builder().build();
    }

    @Override
    @GetExchange(LOGOUT_USER)
    public ResultData logoutUser(Long userId) {
        upmsRepository.logoutUser(userId);
        return ResultData.builder().build();
    }

    @Override
    @PutExchange(UPDATE_PASSWORD)
    public ResultData updateUserPassword(Long userId, String password) {
        upmsRepository.updateUserPassword(userId,password);
        return ResultData.builder().build();
    }
}
