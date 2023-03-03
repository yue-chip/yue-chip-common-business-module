package com.yue.chip.upms.interfaces.facade.console.role;

import com.yue.chip.core.IPageResultData;
import com.yue.chip.core.IResultData;
import com.yue.chip.core.ResultData;
import com.yue.chip.core.YueChipPage;
import com.yue.chip.core.controller.BaseController;
import com.yue.chip.core.controller.impl.BaseControllerImpl;
import com.yue.chip.core.persistence.Validator;
import com.yue.chip.upms.domain.aggregates.Role;
import com.yue.chip.upms.domain.repository.role.RoleRepository;
import com.yue.chip.upms.interfaces.dto.resources.ResourcesAddDto;
import com.yue.chip.upms.interfaces.dto.resources.ResourcesUpdateDto;
import com.yue.chip.upms.interfaces.dto.role.RoleAddDto;
import com.yue.chip.upms.interfaces.dto.role.RoleUpdateDto;
import com.yue.chip.upms.interfaces.vo.role.RoleListVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Mr.Liu
 * @date 2023/3/3 下午2:27
 */
@RestController("roleConsoleController")
@RequestMapping("/role/console")
@Validated
@Tag(name = "角色管理-pc端后台")
public class RoleController extends BaseControllerImpl implements BaseController {

    @Resource
    private RoleRepository roleRepository;

    @GetMapping("/list")
    @Operation(description = "列表",summary = "列表")
    public IPageResultData<List<RoleListVo>> list(@Parameter(description = "名称",name = "name")String name,
                                                  @Parameter(description = "编码",name = "code") String code,
                                                  YueChipPage pageable) {
        return (IPageResultData<List<RoleListVo>>) roleRepository.list(name,code,pageable);
    }


    @Operation(description = "新建角色",summary = "新建角色")
    @PostMapping("/add")
    public IResultData add(@RequestBody @Validated RoleAddDto role){
        roleRepository.save(role);
        return ResultData.builder().build();
    }

    @Operation(description = "修改角色",summary = "修改角色")
    @PutMapping("/update")
    public IResultData update(@RequestBody @Validated RoleUpdateDto role){
        roleRepository.update(role);
        return ResultData.builder().build();
    }

}
