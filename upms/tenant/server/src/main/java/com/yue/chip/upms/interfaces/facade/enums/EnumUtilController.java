package com.yue.chip.upms.interfaces.facade.enums;

import com.yue.chip.core.IResultData;
import com.yue.chip.core.ResultData;
import com.yue.chip.core.common.enums.EnumPersistenceBean;
import com.yue.chip.core.common.remote.http.RemoteEnumPersistenceHttp;
import com.yue.chip.upms.assembler.enums.EnumUtilMapper;
import com.yue.chip.upms.domain.repository.enums.EnumUtilRepository;
import com.yue.chip.upms.interfaces.vo.enums.EnumUtilVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotBlank;
import lombok.extern.java.Log;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

/**
 * @author Mr.Liu
 * @date 2023/7/6 上午11:51
 */
@RestController()
@RequestMapping("/enum")
@Validated
@Tag(name = "枚举")
@Log
public class EnumUtilController implements RemoteEnumPersistenceHttp {

    @Resource
    private EnumUtilRepository enumUtilRepository;

    @Resource
    private EnumUtilMapper enumUtilMapper;

    @GetMapping("")
    @Operation(description = "获取枚举",summary = "获取枚举")
    public IResultData<EnumUtilVo> get(@NotBlank(message = "枚举编码不能为空") @Parameter(description = "枚举编码",name = "code",required = true)String code,
                                       @NotBlank(message = "枚举版本号不能为空") @Parameter(description = "枚举版本号",name = "version",required = true) String version) {
//        Optional<EnumUtil> optional = enumUtilRepository.find(code,version);
//        if (optional.isPresent()){
//            return ResultData.builder().data(enumUtilMapper.toEnumUtilVo(optional.get())).build();
//        }
        return ResultData.builder().build();
    }

    @Override
    public void save(List<EnumPersistenceBean> list) {
        if (Objects.nonNull(list) && !list.isEmpty()) {
            list.forEach(bean -> {
                enumUtilRepository.save(enumUtilMapper.toEnumUtilPo(bean));
            });
        }
    }
}
