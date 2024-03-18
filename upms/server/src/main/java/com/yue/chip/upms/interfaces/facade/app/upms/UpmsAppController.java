package com.yue.chip.upms.interfaces.facade.app.upms;

import com.yue.chip.core.IResultData;
import com.yue.chip.core.ResultData;
import com.yue.chip.core.common.enums.State;
import com.yue.chip.core.persistence.Validator;
import com.yue.chip.upms.application.service.UpmsApplication;
import com.yue.chip.upms.assembler.organizational.OrganizationalMapper;
import com.yue.chip.upms.assembler.user.UserMapper;
import com.yue.chip.upms.domain.aggregates.User;
import com.yue.chip.upms.domain.repository.organizational.OrganizationalRepository;
import com.yue.chip.upms.domain.repository.upms.UpmsRepository;
import com.yue.chip.upms.interfaces.dto.user.UserAddOrUpdateDto;
import com.yue.chip.upms.interfaces.dto.user.UserUpdatePasswordDto;
import com.yue.chip.upms.interfaces.vo.organizational.OrganizationalTreeListVo;
import com.yue.chip.upms.interfaces.vo.organizational.OrganizationalTreeSelectVo;
import com.yue.chip.upms.interfaces.vo.user.UserVo;
import com.yue.chip.utils.CurrentUserUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * @author Mr.Liu
 * @date 2023/1/12 下午3:18
 * @description UpmsController
 */
@RestController()
@RequestMapping("/app")
@Validated
@Tag(name = "角色&用户&资源-app端后台")
public class UpmsAppController {

    @Resource
    private OrganizationalRepository organizationalRepository;

    @Resource
    private OrganizationalMapper organizationalMapper;

    @Resource
    private UpmsRepository upmsRepository;

    @Resource
    private UserMapper userMapper;

    @Resource
    private UpmsApplication upmsApplication;

    @GetMapping("/organizational/tree/select")
    @Operation(description = "组织机构-树形结构下拉框选择",summary = "组织机构-树形结构下拉框选择")
    public IResultData<List<OrganizationalTreeSelectVo>> organizationalTreeSelect(){
        List<OrganizationalTreeListVo> treeListVos = organizationalRepository.findTree(0L, State.NORMAL,null );
        return ResultData.builder().data(organizationalMapper.toOrganizationalTreeSelectVo(treeListVos)).build();
    }

    @GetMapping("/current/user/details")
    @Operation(summary = "用户-当前登录用户详情", description = "用户-当前登录用户详情")
    public IResultData<UserVo> userPermissions(){
        Optional<User> optional = upmsRepository.findUserById(CurrentUserUtil.getCurrentUserId());
        return ResultData.builder().data(optional.isPresent()?userMapper.toUserVo(optional.get()):null).build();
    }

    @PutMapping("/user/update")
    @Operation(description = "用户-修改用户",summary = "用户-修改用户")
    public IResultData updateUser(@RequestBody @Validated({Validator.Update.class}) UserAddOrUpdateDto userAddOrUpdateDto) {
        upmsApplication.updateUser(userAddOrUpdateDto);
        return ResultData.builder().build();
    }

    @PutMapping("/user/update/password")
    @Operation(description = "用户-修改用户密码",summary = "用户-修改用户密码")
    public IResultData updateUserPassword(@RequestBody @Validated UserUpdatePasswordDto userUpdatePasswordDto) {
        upmsApplication.updateUserPassword(userUpdatePasswordDto);
        return ResultData.builder().build();
    }

    @PostMapping("/user/register")
    @Operation(description = "用户-注册用户",summary = "用户-注册用户")
    public IResultData saveUser(@RequestBody @Validated({Validator.Insert.class}) UserAddOrUpdateDto userAddOrUpdateDto) {
        upmsApplication.saveAppUser(userAddOrUpdateDto);
        return ResultData.builder().build();
    }
}
