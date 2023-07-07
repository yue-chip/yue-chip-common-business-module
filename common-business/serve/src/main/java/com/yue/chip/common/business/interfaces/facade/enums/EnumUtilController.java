package com.yue.chip.common.business.interfaces.facade.enums;

import com.yue.chip.annotation.AuthorizationIgnore;
import com.yue.chip.common.business.assembler.enums.EnumUtilMapper;
import com.yue.chip.common.business.domain.repository.enums.EnumUtilRepository;
import com.yue.chip.common.business.interfaces.dto.enuns.EnumUtilDto;
import com.yue.chip.core.IResultData;
import com.yue.chip.core.ResultData;
import com.yue.chip.core.controller.BaseController;
import com.yue.chip.core.controller.impl.BaseControllerImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Mr.Liu
 * @date 2023/7/6 上午11:51
 */
@RestController()
@RequestMapping("/enum")
@Validated
@Tag(name = "枚举")
@Log
public class EnumUtilController  extends BaseControllerImpl implements BaseController {

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



}
