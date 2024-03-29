package com.yue.chip.upms.interfaces.facade.console.upms;

import com.yue.chip.core.IPageResultData;
import com.yue.chip.core.IResultData;
import com.yue.chip.core.ResultData;
import com.yue.chip.core.YueChipPage;
import com.yue.chip.core.common.enums.State;
import com.yue.chip.core.persistence.Validator;
import com.yue.chip.upms.application.service.UpmsApplication;
import com.yue.chip.upms.assembler.organizational.OrganizationalMapper;
import com.yue.chip.upms.domain.aggregates.Organizational;
import com.yue.chip.upms.domain.aggregates.Resources;
import com.yue.chip.upms.domain.aggregates.Role;
import com.yue.chip.upms.domain.aggregates.User;
import com.yue.chip.upms.domain.repository.organizational.OrganizationalRepository;
import com.yue.chip.upms.domain.repository.upms.UpmsRepository;
import com.yue.chip.upms.enums.Scope;
import com.yue.chip.upms.assembler.resources.ResourcesMapper;
import com.yue.chip.upms.assembler.role.RoleMapper;
import com.yue.chip.upms.assembler.user.UserMapper;
import com.yue.chip.upms.infrastructure.po.organizational.OrganizationalUserPo;
import com.yue.chip.upms.interfaces.dto.organizational.OrganizationalAddDto;
import com.yue.chip.upms.interfaces.dto.organizational.OrganizationalUpdateDto;
import com.yue.chip.upms.interfaces.dto.resources.ResourcesAddDto;
import com.yue.chip.upms.interfaces.dto.resources.ResourcesUpdateDto;
import com.yue.chip.upms.interfaces.dto.role.RoleAddDto;
import com.yue.chip.upms.interfaces.dto.role.RoleResourcesAddDto;
import com.yue.chip.upms.interfaces.dto.role.RoleUpdateDto;
import com.yue.chip.upms.interfaces.dto.user.UserAddOrUpdateDto;
import com.yue.chip.upms.interfaces.dto.user.UserRoleAddDto;
import com.yue.chip.upms.interfaces.dto.user.UserUpdatePasswordDto;
import com.yue.chip.upms.interfaces.vo.organizational.OrganizationalTreeListVo;
import com.yue.chip.upms.interfaces.vo.organizational.OrganizationalTreeSelectVo;
import com.yue.chip.upms.interfaces.vo.organizational.OrganizationalVo;
import com.yue.chip.upms.interfaces.vo.resources.ResourcesTreeVo;
import com.yue.chip.upms.interfaces.vo.resources.ResourcesTreeListVo;
import com.yue.chip.upms.interfaces.vo.resources.ResourcesVo;
import com.yue.chip.upms.interfaces.vo.role.RoleVo;
import com.yue.chip.upms.interfaces.vo.user.UserVo;
import com.yue.chip.utils.CurrentUserUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * @author Mr.Liu
 * @date 2023/3/7 下午2:28
 */
@RestController()
@RequestMapping("/console")
@Validated
@Tag(name = "角色&用户&资源-pc端后台")
@Slf4j
public class UpmsConsoleController {

    @Resource
    private UpmsRepository upmsRepository;

    @Resource
    private OrganizationalRepository organizationalRepository;

    @Resource
    private UpmsApplication upmsApplication;

    @Resource
    private RoleMapper roleMapper;

    @Resource
    private ResourcesMapper resourcesMapper;

    @Resource
    private OrganizationalMapper organizationalMapper;
    @Resource
    private UserMapper userMapper;

    @GetMapping("/currentUser/permissions")
    @Operation(summary = "权限-获取当前用户的权限(菜单，资源)", description = "权限-获取当前用户的权限(菜单，资源)")
    public IResultData<List<ResourcesTreeListVo>> userPermissions(){
//        Optional<User> optional = upmsRepository.findUserById(CurrentUserUtil.getCurrentUserId());
        User user = User.builder().id(CurrentUserUtil.getCurrentUserId()).build();
        ResultData resultData = ResultData.builder().data(user.getResourcesTree()).build();
        return resultData;
    }

    @GetMapping("/current/user/details")
    @Operation(summary = "用户-获取当前登录用户信息", description = "用户-获取当前登录用户信息")
    public IResultData<UserVo> userDetails(){
        Optional<User> optional = upmsRepository.findUserById(CurrentUserUtil.getCurrentUserId(true));
        return ResultData.builder().data(optional.isPresent()?userMapper.toUserVo(optional.get()):null).build();
    }

    @GetMapping("/role/list")
    @Operation(description = "角色-角色列表",summary = "角色-角色列表")
    public IPageResultData<List<RoleVo>> roleList(@Parameter(description = "名称",name = "name")String name,
                                                  @Parameter(description = "编码",name = "code") String code,
                                                  YueChipPage pageable) {
        return (IPageResultData<List<RoleVo>>) upmsRepository.roleList(name,code,pageable);
    }

    @Operation(description = "角色-判断角色名称是否存在",summary = "角色-判断角色名称是否存在")
    @GetMapping("/role/check/name/exist")
    public IResultData<Boolean> roleCheckNameIsExist(@NotBlank(message = "名称不能为空") @Parameter(description = "名称",required = true,name = "name")String name,
                                                 @Parameter(description = "修改需要传id，新增则不需要传",name = "id") Long id){
        ResultData resultData = ResultData.builder().data(false).build();
        Role role = Role.builder().name(name).id(id).build();
        resultData.setData(role.checkNameIsExist());
        return resultData;
    }

    @Operation(description = "角色-新建角色",summary = "角色-新建角色")
    @PostMapping("/role/add")
    public IResultData add(@RequestBody @Validated RoleAddDto role){
        upmsRepository.saveRole(roleMapper.toRolePo(role));
        return ResultData.builder().build();
    }

    @Operation(description = "角色-角色详情",summary = "角色-角色详情")
    @GetMapping("/role/details")
    public IResultData<RoleVo> roleDetails(@NotNull(message = "角色id不能为空")@Parameter(description = "角色id",name = "id",required = true)Long id){
        Optional<Role> optional = upmsRepository.findRoleById(id);
        return ResultData.builder().data(optional.isPresent()?roleMapper.toRoleVo(optional.get()):null).build();
    }

    @Operation(description = "角色-修改角色",summary = "角色-修改角色")
    @PutMapping("/role/update")
    public IResultData roleUpdate(@RequestBody @Validated RoleUpdateDto role){
        upmsRepository.updateRole(roleMapper.toRolePo(role));
        return ResultData.builder().build();
    }

    @Operation(description = "角色-角色绑定资源权限(全量，先删后增)",summary = "角色-角色绑定资源权限(全量，先删后增)")
    @PostMapping("/role/resources")
    public IResultData roleResources(@RequestBody @Validated RoleResourcesAddDto roleResourcesAddDto){
        upmsApplication.roleBindResources(roleResourcesAddDto);
        return ResultData.builder().build();
    }

    @Operation(description = "角色-删除角色(默认角色不能删除)",summary = "角色-删除角色(默认角色不能删除)")
    @DeleteMapping("/role/delete")
    public IResultData roleDelete(@NotNull(message = "角色id不能为空")@Parameter(description = "角色id",name = "id",required = true)Long id){
        upmsApplication.deleteRole(id);
        return ResultData.builder().build();
    }

    @Operation(description = "角色-获取角色已绑定的资源权限",summary = "角色-获取角色已绑定的资源权限")
    @GetMapping("/role/resources")
    public IResultData<List<Long>> roleResources(@NotNull(message = "角色id不能为空")@Parameter(description = "角色id",name = "roleId",required = true)Long roleId){
        Optional<Role> operation = upmsRepository.findRoleById(roleId);
        ResultData resultData = ResultData.builder().build();
        if (operation.isPresent()) {
            resultData.setData(operation.get().getResourcesIdForFront());
        }
        return resultData;
    }

    @Operation(description = "角色-获取角色已绑定的用户",summary = "角色-获取角色已绑定的用户")
    @GetMapping("/role/user")
    public IResultData<List<Long>> roleUser(@NotNull(message = "角色id不能为空")@Parameter(description = "角色id",name = "roleId",required = true)Long roleId){
        ResultData resultData = ResultData.builder().build();
        Optional<Role> operation = upmsRepository.findRoleById(roleId);
        if (operation.isPresent()) {
            resultData.setData(operation.get().getUserId());
        }
        return resultData;

    }

    @Operation(description = "用户-用户绑定角色(全量，先删后增)",summary = "用户-用户绑定角色(全量，先删后增)")
    @PostMapping("/user/role")
    public IResultData roleUserAdd(@RequestBody @Validated UserRoleAddDto userRoleAddDto){
        ResultData resultData = ResultData.builder().build();
        upmsApplication.userBindRole(userRoleAddDto);
        return resultData;
    }


    @GetMapping("/resources/tree/list")
    @Operation(description = "菜单资源-树型结构资源列表",summary = "菜单资源-树型结构资源列表")
    public IResultData<List<ResourcesTreeListVo>> resourcesTreeList(@Parameter(description = "作用域",name = "scope")@RequestParam(defaultValue = "CONSOLE") Scope scope) {
        IResultData resultData = ResultData.builder()
                .data(upmsRepository.findResourcesToTreeList(0L,scope))
                .build();
        return resultData;
    }

    @GetMapping("/resources/tree")
    @Operation(description = "菜单资源-树型结构资源",summary = "菜单资源-树型结构资源")
    public IResultData<List<ResourcesTreeVo>> resourcesTree(@Parameter(description = "作用域",name = "scope")@RequestParam(defaultValue = "CONSOLE") Scope scope) {
        IResultData resultData = ResultData.builder()
                .data(upmsRepository.findResourcesToTree(0L,scope))
                .build();
        return resultData;
    }

    @GetMapping("/resources/check/code/exist")
    @Operation(description = "菜单资源-判断资源编码是否存在",summary = "菜单资源-判断资源编码是否存在")
    public IResultData<Boolean> resourcesCheckCodeIsExist(@NotBlank(message = "编码不能为空") @Parameter(description = "编码",required = true,name = "code") String code,
                                                 @Parameter(description = "修改需要传id，新增则不需要传",name = "id")Long id){
        ResultData resultData = ResultData.builder().data(false).build();
        Resources resources = Resources.builder().code(code).id(id).build();
        resultData.setData(resources.checkCodeIsExist());
        return resultData;
    }

    @Operation(description = "菜单资源-判断资源名称是否存在",summary = "菜单资源-判断资源名称是否存在")
    @GetMapping("/resources/check/name/exist")
    public IResultData<Boolean> resourcesCheckNameIsExist(@NotBlank(message = "名称不能为空") @Parameter(description = "名称",required = true,name = "name")String name,
                                                 @Parameter(description = "修改需要传id，新增则不需要传",name = "id") Long id,
                                                 @NotNull(message = "父节点id不能为空") @Parameter(description = "父节点id",required = true,name = "parentId") Long parentId){
        ResultData resultData = ResultData.builder().data(false).build();
        Resources resources = Resources.builder().name(name).id(id).parentId(parentId).build();
        resultData.setData(resources.checkNameIsExist());
        return resultData;
    }

    @GetMapping("/resources/check/url/exist")
    @Operation(description = "菜单资源-判断资源url是否存在",summary = "菜单资源-判断资源url是否存在")
    public IResultData<Boolean> resourcesCheckUrlIsExist(@NotBlank(message = "url不能为空") @Parameter(description = "url",name="url",required = true)String url,
                                                @Parameter(description = "修改需要传id，新增则不需要传",name="id") Long id){
        ResultData resultData = ResultData.builder().data(false).build();
        Resources resources = Resources.builder().url(url).id(id).build();
        resultData.setData(resources.checkUrlIsExist());
        return resultData;
    }

    @Operation(description = "菜单资源-新建资源",summary = "菜单资源-新建资源")
    @PostMapping("/resources/add")
    public IResultData resourcesAdd(@RequestBody @Validated({Validator.Insert.class}) ResourcesAddDto resources){
        resources.setCode(resources.getCode().trim().toUpperCase());
        upmsRepository.saveResources(resourcesMapper.toResourcesPo(resources));
        return ResultData.builder().build();
    }

    @Operation(description = "菜单资源-修改资源",summary = "菜单资源-修改资源")
    @PutMapping("/resources/update")
    public IResultData resourcesUpdate(@RequestBody @Validated({Validator.Update.class}) ResourcesUpdateDto resources){
        resources.setCode(resources.getCode().trim().toUpperCase());
        upmsRepository.updateResources(resourcesMapper.toResourcesPo(resources));
        return ResultData.builder().build();
    }

    @Operation(description = "菜单资源-资源详情",summary = "菜单资源-资源详情")
    @GetMapping("/resources/details")
    public IResultData<ResourcesVo> resourcesDetails(@NotNull(message = "资源id不能为空")@Parameter(description = "url",name="url",required = true)Long id ){
        Optional<Resources> optional = upmsRepository.findResourcesById(id);
        return ResultData.builder().data(optional.isPresent()?resourcesMapper.toResourcesVo(optional.get()):null).build();
    }

    @Operation(description = "菜单资源-删除资源",summary = "菜单资源-删除资源")
    @DeleteMapping("/resources/delete")
    public IResultData resourcesDelete(@NotNull(message = "资源id不能为空")@Parameter(description = "url",name="url",required = true)Long id ){
        upmsApplication.deleteResources(id);
        return ResultData.builder().build();
    }

    @GetMapping("/user/list")
    @Operation(description = "用户-用户列表",summary = "用户-用户列表")
    public IPageResultData<List<UserVo>> userList(@Parameter(description = "姓名",name="name")String name, YueChipPage page) {
        IPageResultData<List<UserVo>> pageResultData = upmsRepository.userList(name,page);
        return pageResultData;
    }

    @PostMapping("/user/add")
    @Operation(description = "用户-新增用户",summary = "用户-新增用户")
    public IResultData saveUser(@RequestBody @Validated({Validator.Insert.class}) UserAddOrUpdateDto userAddOrUpdateDto) {
        upmsApplication.saveUser(userAddOrUpdateDto);
        return ResultData.builder().build();

    }

    @GetMapping("/user/details")
    @Operation(description = "用户-用户详情",summary = "用户-用户详情")
    public IResultData<UserVo> userDetails(@NotNull(message = "用户id不能为空") @Parameter(description = "用户id",name="id",required = true)Long id) {
        Optional<User> optional = upmsRepository.findUserById(id);
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

    @DeleteMapping("/user/delete")
    @Operation(description = "用户-删除用户",summary = "用户-删除用户")
    public IResultData deleteUser(@Size(min = 1,message = "要删除的数据不能为空") @Parameter(description = "用户id",name="ids",required = true)@RequestParam("ids")List<Long> ids) {
        upmsApplication.deleteUser(ids);
        return ResultData.builder().build();
    }

    @GetMapping("/user/is/exist")
    @Operation(description = "用户-判断账号是否存在",summary = "用户-判断账号是否存在")
    public IResultData<Boolean> updateUser(@NotNull(message = "账号不能为空")@Parameter(description = "账号",name="username",required = true)String username) {
        return ResultData.builder().data(User.builder().username(username).build().checkUsernameIsExist()).build();
    }

    @PostMapping("/organizational/add")
    @Operation(description = "组织机构-添加组织机构",summary = "组织机构-添加组织机构")
    public IResultData addOrganizational(@RequestBody @Validated({Validator.Insert.class}) OrganizationalAddDto organizationalAddDto) {
        upmsApplication.saveOrganizational(organizationalAddDto);
        return ResultData.builder().build();
    }

    @PutMapping("/organizational/update")
    @Operation(description = "组织机构-修改组织机构",summary = "组织机构-修改组织机构")
    public IResultData updateOrganizational(@RequestBody @Validated({Validator.Update.class}) OrganizationalUpdateDto organizationalUpdateDto) {
        upmsApplication.updateOrganizational(organizationalUpdateDto);
        return ResultData.builder().build();
    }

    @DeleteMapping("/organizational/delete")
    @Operation(description = "组织机构-删除组织机构",summary = "组织机构-删除组织机构")
    public IResultData deleteOrganizational(@Size(min = 1,message = "要删除的数据不能为空") @Parameter(description = "组织机构id",name="ids",required = true)@RequestParam("ids")List<Long> ids) {
        upmsApplication.deleteOrganizational(ids);
        return ResultData.builder().build();
    }

    @GetMapping("/organizational/tree/list")
    @Operation(description = "组织机构-树形结构列表",summary = "组织机构-树形结构列表")
    public IResultData<List<OrganizationalTreeListVo>> organizationalTreeList(){
        return ResultData.builder().data(organizationalRepository.findTree(0L, null)).build();
    }

    @GetMapping("/organizational/tree/select")
    @Operation(description = "组织机构-树形结构下拉框选择",summary = "组织机构-树形结构下拉框选择")
    public IResultData<List<OrganizationalTreeSelectVo>> organizationalTreeSelect(){
        List<OrganizationalTreeListVo> treeListVos = organizationalRepository.findTree(0L,State.NORMAL);
        return ResultData.builder().data(organizationalMapper.toOrganizationalTreeSelectVo(treeListVos)).build();
    }

    @GetMapping("/organizational/tree/select1")
    @Operation(description = "组织机构-树形结构下拉框选择(当前登录用户所属的机构)",summary = "组织机构-树形结构下拉框选择(当前登录用户所属的机构)")
    public IResultData<List<OrganizationalTreeSelectVo>> organizationalTreeSelect1(){
        List<OrganizationalTreeListVo> treeListVos = organizationalRepository.findTree1(State.NORMAL);
        return ResultData.builder().data(organizationalMapper.toOrganizationalTreeSelectVo(treeListVos)).build();
    }

    @GetMapping("/organizational/user/select/list")
    @Operation(description = "组织机构-获取机构下的用户",summary = "组织机构-获取机构下的用户")
    public IResultData<List<UserVo>> organizationalUserList(@NotNull(message = "机构id不能为空") @Parameter(description = "组织机构id",name="organizationalId",required = true)Long organizationalId){
        return ResultData.builder().data(userMapper.toUserSelectVo(upmsRepository.findUserByOrganizationalId(organizationalId))).build();
    }

    @GetMapping("/organizational/details")
    @Operation(description = "组织机构-组织机构详情",summary = "组织机构-组织机构详情")
    public IResultData<OrganizationalVo> organizationalDetails(@NotNull(message = "组织机构id不能为空") @Parameter(description = "组织机构id",name="id",required = true)Long id) {
        Optional<Organizational> optional = organizationalRepository.findById(id);
        return ResultData.builder().data(optional.isPresent()?organizationalMapper.toOrganizationalVo(optional.get()):null).build();
    }
}
