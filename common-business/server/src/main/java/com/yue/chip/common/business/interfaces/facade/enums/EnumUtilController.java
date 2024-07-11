package com.yue.chip.common.business.interfaces.facade.enums;

import com.yue.chip.annotation.AuthorizationIgnore;
import com.yue.chip.common.business.assembler.enums.EnumUtilMapper;
import com.yue.chip.common.business.domain.aggregates.enums.EnumUtil;
import com.yue.chip.common.business.domain.repository.enums.EnumUtilRepository;
import com.yue.chip.common.business.interfaces.dto.enuns.EnumUtilDto;
import com.yue.chip.common.business.interfaces.vo.enums.EnumUtilVo;
import com.yue.chip.core.IResultData;
import com.yue.chip.core.ResultData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotBlank;
import lombok.extern.java.Log;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * @author Mr.Liu
 * @date 2023/7/6 上午11:51
 */
@RestController()
@RequestMapping("/enum")
@Validated
@Tag(name = "枚举")
@Log
public class EnumUtilController   {

    @Resource
    private EnumUtilRepository enumUtilRepository;

    @Resource
    private EnumUtilMapper enumUtilMapper;

    @PostMapping("/persistence")
    @Operation(description = "枚举持久化",summary = "枚举持久化")
    @AuthorizationIgnore
    public IResultData persistence(@RequestBody List<EnumUtilDto> enumUtilDtos) {
        enumUtilRepository.save(enumUtilMapper.toEnumUtilPo(enumUtilDtos));
        return ResultData.builder().build();
    }

    @GetMapping("")
    @Operation(description = "获取枚举",summary = "获取枚举")
    @AuthorizationIgnore
    public IResultData<EnumUtilVo> get(@NotBlank(message = "枚举编码不能为空") @Parameter(description = "枚举编码",name = "code",required = true)String code,
                                       @NotBlank(message = "枚举版本号不能为空") @Parameter(description = "枚举版本号",name = "version",required = true) String version) {
        Optional<EnumUtil> optional = enumUtilRepository.find(code,version);
        if (optional.isPresent()){
            return ResultData.builder().data(enumUtilMapper.toEnumUtilVo(optional.get())).build();
        }
        return ResultData.builder().build();
    }
}
