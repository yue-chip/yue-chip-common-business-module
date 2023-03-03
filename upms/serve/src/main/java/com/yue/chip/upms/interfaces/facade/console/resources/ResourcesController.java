package com.yue.chip.upms.interfaces.facade.console.resources;

import com.yue.chip.core.IResultData;
import com.yue.chip.core.ResultData;
import com.yue.chip.core.controller.BaseController;
import com.yue.chip.core.controller.impl.BaseControllerImpl;
import com.yue.chip.core.persistence.Validator;
import com.yue.chip.upms.domain.aggregates.Resources;
import com.yue.chip.upms.domain.repository.resources.ResourcesRepository;
import com.yue.chip.upms.enums.Scope;
import com.yue.chip.upms.infrastructure.assembler.resources.ResourcesMapper;
import com.yue.chip.upms.interfaces.dto.resources.ResourcesAddDto;
import com.yue.chip.upms.interfaces.dto.resources.ResourcesUpdateDto;
import com.yue.chip.upms.interfaces.vo.resources.ResourcesTree;
import com.yue.chip.upms.interfaces.vo.resources.ResourcesTreeList;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * @author Mr.Liu
 * @date 2023/3/1 上午11:15
 */
@RestController("resourcesConsoleController")
@RequestMapping("/resources/console")
@Validated
@Tag(name = "资源管理-pc端后台")
public class ResourcesController extends BaseControllerImpl implements BaseController {

    @Resource
    private ResourcesRepository resourcesRepository;

    @Resource
    private ResourcesMapper resourcesMapper;

    @GetMapping("/tree/list")
    @Operation(description = "获取树型结构资源分页列表",summary = "获取树型结构资源分页列表")
    public IResultData<List<ResourcesTreeList>> resourcesTreeList(@Parameter(description = "作用域",name = "scope")@RequestParam(defaultValue = "CONSOLE") Scope scope) {
        IResultData resultData = ResultData.builder()
                .data(resourcesRepository.findResourcesToTreeList(0L,scope))
                .build();
        return resultData;
    }

    @GetMapping("/tree")
    @Operation(description = "获取树型结构资源",summary = "获取树型结构资源")
    public IResultData<List<ResourcesTree>> resourcesTree(@Parameter(description = "作用域",name = "scope")@RequestParam(defaultValue = "CONSOLE") Scope scope) {
        IResultData resultData = ResultData.builder()
                .data(resourcesRepository.findResourcesToTree(0L,scope))
                .build();
        return resultData;
    }




    @GetMapping("/check/code/exist")
    @Operation(description = "判断编码是否存在",summary = "判断编码是否存在")
    public IResultData<Boolean> checkCodeIsExist(@NotBlank(message = "编码不能为空") @Parameter(description = "编码",required = true,name = "code") String code,
                                                 @Parameter(description = "修改需要传id，新增则不需要传",name = "id")Long id){
        ResultData resultData = ResultData.builder().data(false).build();
        Optional<Resources> optional = resourcesRepository.findByCode(code);
        if (optional.isPresent()){
            Resources resources = optional.get();
            resultData.setData(resources.checkCodeIsExist(resources,code,id));
        }
        return resultData;
    }

    @Operation(description = "判断名称是否存在",summary = "判断名称是否存在")
    @GetMapping("/check/name/exist")
    public IResultData<Boolean> checkNameIsExist(@NotBlank(message = "名称不能为空") @Parameter(description = "名称",required = true,name = "name")String name,
                                                 @Parameter(description = "修改需要传id，新增则不需要传",name = "id") Long id,
                                                 @NotNull(message = "父节点id不能为空") @Parameter(description = "父节点id",required = true,name = "parentId") Long parentId){
        ResultData resultData = ResultData.builder().data(false).build();
        Optional<Resources> optional = resourcesRepository.findByNameAndParentId(name,parentId);
        if (optional.isPresent()){
            Resources resources = optional.get();
            resultData.setData(resources.checkNameIsExist(resources,name,id));
        }
        return resultData;
    }

    @GetMapping("/check/url/exist")
    @Operation(description = "判断url是否存在",summary = "判断url是否存在")
    public IResultData<Boolean> checkUrlIsExist(@NotBlank(message = "url不能为空") @Parameter(description = "url",name="url",required = true)String url,
                                                @Parameter(description = "修改需要传id，新增则不需要传",name="id") Long id){
        ResultData resultData = ResultData.builder().data(false).build();
        Optional<Resources> optional = resourcesRepository.findByUrl(url);
        if (optional.isPresent()){
            Resources resources = optional.get();
            resultData.setData(resources.checkUrlIsExist(resources,url,id));
        }
        return resultData;
    }

    @Operation(description = "新建资源",summary = "新建资源")
    @PostMapping("/add")
    public IResultData add(@RequestBody @Validated({Validator.Insert.class}) ResourcesAddDto resources){
        resources.setCode(resources.getCode().trim().toUpperCase());
        resourcesRepository.save(resources);
        return ResultData.builder().build();
    }

    @Operation(description = "修改资源",summary = "修改资源")
    @PutMapping("/update")
    public IResultData update(@RequestBody @Validated({Validator.Update.class}) ResourcesUpdateDto resources){
        resources.setCode(resources.getCode().trim().toUpperCase());
        resourcesRepository.update(resources);
        return ResultData.builder().build();
    }



}
