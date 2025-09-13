package com.yue.chip.upms.interfaces.facade.remote.http;

import com.yue.chip.core.ResultData;
import com.yue.chip.remote.http.upms.RemoteUpmsDefinition;
import com.yue.chip.remote.http.upms.vo.UserExposeVo;
import com.yue.chip.upms.application.service.UpmsApplication;
import com.yue.chip.upms.assembler.organizational.GridMapper;
import com.yue.chip.upms.assembler.organizational.OrganizationalMapper;
import com.yue.chip.upms.assembler.organizational.OrganizationalUserMapper;
import com.yue.chip.upms.assembler.user.UserMapper;
import com.yue.chip.upms.domain.aggregates.User;
import com.yue.chip.upms.domain.repository.organizational.OrganizationalRepository;
import com.yue.chip.upms.domain.repository.upms.UpmsRepository;
import com.yue.chip.upms.interfaces.dto.user.UserUpdatePasswordDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotBlank;
import lombok.extern.java.Log;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @GetMapping(FIND_USERNAME)
    public ResultData<UserExposeVo> findByUsername(@NotBlank(message = "登陆账号不能为空") String username) {
        ResultData.ResultDataBuilder<UserExposeVo> builder = ResultData.builder();
        Optional<User> userOptional = upmsRepository.findUserByUsername(username);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return builder.data(userMapper.toUserExposeVo(user)).build();
        }
        return builder.build();
    }

    @Override
    @GetMapping(FIND_PHONE)
    public ResultData<UserExposeVo> findByPhoneNumber(@NotBlank(message = "电话号码不能为空") String phoneNumber) {
        ResultData.ResultDataBuilder<UserExposeVo> builder = ResultData.builder();
        Optional<User> userOptional = upmsRepository.findUserByPhoneNumber(phoneNumber);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return builder.data(userMapper.toUserExposeVo(user)).build();
        }
        return builder.build();
    }

    @Override
    @GetMapping(FIND_EMAIL)
    public ResultData<UserExposeVo> findByEmail(@NotBlank(message = "email不能为空")String email) {
        ResultData.ResultDataBuilder<UserExposeVo> builder = ResultData.builder();
        Optional<User> userOptional = upmsRepository.findUserByEmail(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return builder.data(userMapper.toUserExposeVo(user)).build();
        }
        return builder.build();
    }

    @Override
    @GetMapping(REGISTER)
    public ResultData register(String phoneNumber, String password, String name, Long id) {
        organizationalRepository.register(phoneNumber, password, name, id);
        return ResultData.builder().build();
    }

    @Override
    @GetMapping(REGISTER_EMAIL)
    public ResultData registerByEmail(String email, String password, String name, Long id) {
        organizationalRepository.registerByEmail(email, password, name, id);
        return ResultData.builder().build();
    }

    @Override
    @GetMapping(REVOKED)
    public ResultData revoked(Long userId) {
        upmsRepository.logoutUser(userId);
        return ResultData.builder().build();
    }

    @Override
    @GetMapping(UPDATE)
    public ResultData updateUserPassword(Long userId, String password) {
        UserUpdatePasswordDto userUpdatePasswordDto = new UserUpdatePasswordDto();
        userUpdatePasswordDto.setPassword(password);
        userUpdatePasswordDto.setUserId(userId);
        upmsApplication.updateUserPassword(userUpdatePasswordDto);
        return ResultData.builder().build();
    }
}
