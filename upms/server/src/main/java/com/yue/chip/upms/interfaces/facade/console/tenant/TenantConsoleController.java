package com.yue.chip.upms.interfaces.facade.console.tenant;

import com.yue.chip.core.IPageResultData;
import com.yue.chip.core.IResultData;
import com.yue.chip.core.ResultData;
import com.yue.chip.core.YueChipPage;
import com.yue.chip.core.common.enums.State;
import com.yue.chip.upms.application.service.TenantApplication;
import com.yue.chip.upms.assembler.tenant.TenantMapper;
import com.yue.chip.upms.domain.aggregates.Tenant;
import com.yue.chip.upms.domain.repository.tenant.TenantRepository;
import com.yue.chip.upms.interfaces.dto.tenant.TenantAddDTO;
import com.yue.chip.upms.interfaces.dto.tenant.TenantUpdateDTO;
import com.yue.chip.upms.interfaces.vo.tenant.TenantVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * @author Mr.Liu
 * @description: TODO
 * @date 2023/10/24 上午10:50
 */
@RestController()
@RequestMapping("/console/tenant")
@Validated
//@Tag(name = "租户管理-pc端后台")
@Slf4j
public class TenantConsoleController {

    @Resource
    private TenantRepository tenantRepository;

    @Resource
    private TenantApplication tenantApplication;

    @Resource
    private TenantMapper tenantMapper;


    @GetMapping("/list")
    //@Operation(description = "租户-租户列表",summary = "租户-租户列表")
    public IPageResultData<List<TenantVo>> list(@Parameter(description = "名称",name = "name")String name,
                                                    @Parameter(description = "负责人",name = "manager") String manager,
                                                    @Parameter(description = "状态",name = "state") State state,
                                                    @Parameter(description = "联系电话",name = "phoneNumber") String phoneNumber,
                                                    YueChipPage pageable) {
        return (IPageResultData<List<TenantVo>>) tenantRepository.list(name,manager,state,phoneNumber,pageable);
    }

    //@Operation(description = "租户-新建租户",summary = "租户-新建租户")
    @PostMapping("/add")
    public IResultData add(@RequestBody @Validated TenantAddDTO tenantAddDTO){
        tenantApplication.save(tenantAddDTO);
        return ResultData.builder().build();
    }

    //@Operation(description = "租户-修改租户",summary = "租户-修改租户")
    @PutMapping("/update")
    public IResultData update(@RequestBody @Validated TenantUpdateDTO tenantUpdateDTO){
        tenantApplication.update(tenantUpdateDTO);
        return ResultData.builder().build();
    }

    //@Operation(description = "租户-修改租户状态",summary = "租户-修改租户状态")
    @PutMapping("/update/state")
    public IResultData updateState(@Parameter(description = "状态",name = "状态",required = true) @NotNull(message = "状态不能为空") State state,
                                   @Parameter(description = "id",name = "id",required = true) @NotNull(message = "id不能为空") Long id){
        tenantApplication.update(id,state);
        return ResultData.builder().build();
    }

    //@Operation(description = "租户-删除租户",summary = "租户-删除租户)")
    @DeleteMapping("/delete")
    public IResultData roleDelete(@Size(min = 1,message = "请选择要删除的租户") @Parameter(description = "租户id",name = "id",required = true)@RequestParam("ids") List<Long> ids){
        tenantApplication.delete(ids);
        return ResultData.builder().build();
    }

    @GetMapping("/details")
    //@Operation(description = "租户-租户详情",summary = "租户-租户详情")
    public IResultData<TenantVo> details(@Parameter(description = "修改需要传id，新增则不需要传",name = "id") @NotNull(message = "id不能为空")Long id) {
        Optional<Tenant> optional = tenantRepository.tenantDetails(id);
        if (optional.isPresent()) {
           return ResultData.builder().data(optional.isEmpty()?null:tenantMapper.toTenantVo(optional.get())).build();
        }
        return ResultData.builder().build();
    }

    //@Operation(description = "租户-判断租户名称是否存在",summary = "租户-判断租户名称是否存在")
    @GetMapping("/check/name/exist")
    public IResultData<Boolean> roleCheckNameIsExist(@NotBlank(message = "名称不能为空") @Parameter(description = "名称",required = true,name = "name")String name,
                                                     @Parameter(description = "修改需要传id，新增则不需要传",name = "id") Long id){
        ResultData resultData = ResultData.builder().data(false).build();
        Tenant tenant = Tenant.builder().name(name).id(id).build();
        resultData.setData(tenant.checkNameIsExist());
        return resultData;
    }

}
