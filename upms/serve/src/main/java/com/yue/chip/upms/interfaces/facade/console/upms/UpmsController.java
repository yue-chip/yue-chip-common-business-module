package com.yue.chip.upms.interfaces.facade.console.upms;

import com.yue.chip.core.IPageResultData;
import com.yue.chip.core.IResultData;
import com.yue.chip.core.ResultData;
import com.yue.chip.core.YueChipPage;
import com.yue.chip.core.controller.BaseController;
import com.yue.chip.core.controller.impl.BaseControllerImpl;
import com.yue.chip.core.persistence.Validator;
import com.yue.chip.upms.application.service.UpmsApplication;
import com.yue.chip.upms.domain.aggregates.Resources;
import com.yue.chip.upms.domain.aggregates.Role;
import com.yue.chip.upms.domain.aggregates.User;
import com.yue.chip.upms.domain.repository.upms.UpmsRepository;
import com.yue.chip.upms.enums.Scope;
import com.yue.chip.upms.interfaces.dto.resources.ResourcesAddDto;
import com.yue.chip.upms.interfaces.dto.resources.ResourcesUpdateDto;
import com.yue.chip.upms.interfaces.dto.role.RoleAddDto;
import com.yue.chip.upms.interfaces.dto.role.RoleResourcesAddDto;
import com.yue.chip.upms.interfaces.dto.role.RoleUpdateDto;
import com.yue.chip.upms.interfaces.vo.resources.ResourcesTree;
import com.yue.chip.upms.interfaces.vo.resources.ResourcesTreeList;
import com.yue.chip.upms.interfaces.vo.role.RoleListVo;
import com.yue.chip.utils.CurrentUserUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Mr.Liu
 * @date 2023/3/7 下午2:28
 */
@RestController("upmsConsoleController")
@RequestMapping("/upms/console")
@Validated
@Tag(name = "角色&用户&资源-pc端后台")
public class UpmsController extends BaseControllerImpl implements BaseController {

    @Resource
    private UpmsRepository upmsRepository;

    @Resource
    private UpmsApplication upmsApplication;

    @GetMapping("/currentUser/permissions")
    @Operation(summary = "获取当前用户的权限(菜单，资源)", description = "获取当前用户的权限(菜单，资源)")
    public IResultData<List<ResourcesTreeList>> userPermissions(){
        Long userId = CurrentUserUtil.getCurrentUserId();
        ResultData resultData = ResultData.builder()
                .data(upmsRepository.findResourcesToTreeList(userId,0L, Scope.CONSOLE))
                .build();
        return resultData;
    }

    @GetMapping("/role/list")
    @Operation(description = "角色列表",summary = "角色列表")
    public IPageResultData<List<RoleListVo>> roleList(@Parameter(description = "名称",name = "name")String name,
                                                  @Parameter(description = "编码",name = "code") String code,
                                                  YueChipPage pageable) {
        return (IPageResultData<List<RoleListVo>>) upmsRepository.roleList(name,code,pageable);
    }

    @Operation(description = "判断角色名称是否存在",summary = "判断角色名称是否存在")
    @GetMapping("/role/check/name/exist")
    public IResultData<Boolean> roleCheckNameIsExist(@NotBlank(message = "名称不能为空") @Parameter(description = "名称",required = true,name = "name")String name,
                                                 @Parameter(description = "修改需要传id，新增则不需要传",name = "id") Long id){
        ResultData resultData = ResultData.builder().data(false).build();
        Role role = Role.builder().name(name).id(id).build();
        resultData.setData(role.checkNameIsExist());
        return resultData;
    }

    @Operation(description = "新建角色",summary = "新建角色")
    @PostMapping("/role/add")
    public IResultData add(@RequestBody @Validated RoleAddDto role){
        upmsRepository.saveRole(role);
        return ResultData.builder().build();
    }

    @Operation(description = "修改角色",summary = "修改角色")
    @PutMapping("/role/update")
    public IResultData roleUpdate(@RequestBody @Validated RoleUpdateDto role){
        upmsRepository.updateRole(role);
        return ResultData.builder().build();
    }

    @Operation(description = "角色绑定资源权限(全量，先删后增)",summary = "角色绑定资源权限(全量，先删后增)")
    @PostMapping("/role/resources")
    public IResultData roleResources(@RequestBody @Validated RoleResourcesAddDto roleResourcesAddDto){
        upmsApplication.roleBindResources(roleResourcesAddDto);
        return ResultData.builder().build();
    }

    @Operation(description = "获取角色已绑定的资源权限",summary = "获取角色已绑定的资源权限")
    @GetMapping("/role/resources")
    public IResultData<List<Long>> roleResources(@Parameter(description = "角色id",name = "roleId",required = true)Long roleId){
        return ResultData.builder().build();
    }

    @Operation(description = "获取角色已绑定的用户",summary = "获取角色已绑定的用户")
    @GetMapping("/role/user")
    public IResultData<List<Long>> roleUser(@Parameter(description = "角色id",name = "roleId",required = true)Long roleId){

        return ResultData.builder().build();
    }


    @GetMapping("/resources/tree/list")
    @Operation(description = "树型结构资源列表",summary = "树型结构资源列表")
    public IResultData<List<ResourcesTreeList>> resourcesTreeList(@Parameter(description = "作用域",name = "scope")@RequestParam(defaultValue = "CONSOLE") Scope scope) {
        IResultData resultData = ResultData.builder()
                .data(upmsRepository.findResourcesToTreeList(0L,scope))
                .build();
        return resultData;
    }

    @GetMapping("/resources/tree")
    @Operation(description = "树型结构资源",summary = "树型结构资源")
    public IResultData<List<ResourcesTree>> resourcesTree(@Parameter(description = "作用域",name = "scope")@RequestParam(defaultValue = "CONSOLE") Scope scope) {
        IResultData resultData = ResultData.builder()
                .data(upmsRepository.findResourcesToTree(0L,scope))
                .build();
        return resultData;
    }

    @GetMapping("/resources/check/code/exist")
    @Operation(description = "判断资源编码是否存在",summary = "判断资源编码是否存在")
    public IResultData<Boolean> resourcesCheckCodeIsExist(@NotBlank(message = "编码不能为空") @Parameter(description = "编码",required = true,name = "code") String code,
                                                 @Parameter(description = "修改需要传id，新增则不需要传",name = "id")Long id){
        ResultData resultData = ResultData.builder().data(false).build();
        Resources resources = Resources.builder().code(code).id(id).build();
        resultData.setData(resources.checkCodeIsExist());
        return resultData;
    }

    @Operation(description = "判断资源名称是否存在",summary = "判断资源名称是否存在")
    @GetMapping("/resources/check/name/exist")
    public IResultData<Boolean> resourcesCheckNameIsExist(@NotBlank(message = "名称不能为空") @Parameter(description = "名称",required = true,name = "name")String name,
                                                 @Parameter(description = "修改需要传id，新增则不需要传",name = "id") Long id,
                                                 @NotNull(message = "父节点id不能为空") @Parameter(description = "父节点id",required = true,name = "parentId") Long parentId){
        ResultData resultData = ResultData.builder().data(false).build();
        Resources resources = Resources.builder().name(name).id(id).build();
        resultData.setData(resources.checkNameIsExist());
        return resultData;
    }

    @GetMapping("/resources/check/url/exist")
    @Operation(description = "判断资源url是否存在",summary = "判断资源url是否存在")
    public IResultData<Boolean> resourcesCheckUrlIsExist(@NotBlank(message = "url不能为空") @Parameter(description = "url",name="url",required = true)String url,
                                                @Parameter(description = "修改需要传id，新增则不需要传",name="id") Long id){
        ResultData resultData = ResultData.builder().data(false).build();
        Resources resources = Resources.builder().url(url).id(id).build();
        resultData.setData(resources.checkUrlIsExist());
        return resultData;
    }

    @Operation(description = "新建资源",summary = "新建资源")
    @PostMapping("/resources/add")
    public IResultData resourcesAdd(@RequestBody @Validated({Validator.Insert.class}) ResourcesAddDto resources){
        resources.setCode(resources.getCode().trim().toUpperCase());
        upmsRepository.saveResources(resources);
        return ResultData.builder().build();
    }

    @Operation(description = "修改资源",summary = "修改资源")
    @PutMapping("/resources/update")
    public IResultData resourcesUpdate(@RequestBody @Validated({Validator.Update.class}) ResourcesUpdateDto resources){
        resources.setCode(resources.getCode().trim().toUpperCase());
        upmsRepository.updateResources(resources);
        return ResultData.builder().build();
    }

    @GetMapping("/user/list")
    @Operation(description = "用户列表",summary = "用户列表")
    public IPageResultData<List<User>> userList(@Parameter(description = "姓名",name="name")String name,YueChipPage page) {
        IPageResultData pageResultData = upmsRepository.userList(name,page);
        return pageResultData;
    }

}
