package com.yue.chip.upms.interfaces.facade.remote.enums;

import com.yue.chip.core.ResultData;
import com.yue.chip.core.common.enums.EnumPersistenceBean;
import com.yue.chip.core.common.enums.EnumUtilDefinition;
import com.yue.chip.core.common.remote.http.RemoteEnumDefinition;
import com.yue.chip.upms.assembler.enums.EnumUtilMapper;
import com.yue.chip.upms.domain.aggregates.enums.EnumUtil;
import com.yue.chip.upms.domain.repository.enums.EnumUtilRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.java.Log;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController()
@RequestMapping()
@Validated
@Tag(name = "枚举远程调用")
@Log
public class RemoteEnumController implements RemoteEnumDefinition {

    @Resource
    private EnumUtilRepository enumUtilRepository;

    @Resource
    private EnumUtilMapper enumUtilMapper;

    @Override
    @PostMapping(SAVE)
    public void save(List<EnumPersistenceBean> list) {
        if (Objects.nonNull(list) && !list.isEmpty()) {
            list.forEach(bean -> {
                enumUtilRepository.save(enumUtilMapper.toEnumUtilPo(bean));
            });
        }
    }

    @Override
    @GetMapping(GET)
    public ResultData<EnumUtilDefinition> get( String code,  String version) {
        Optional<EnumUtil> optional = enumUtilRepository.find(code,version);
        ResultData.ResultDataBuilder<EnumUtilDefinition> resultData = ResultData.builder();
        if (optional.isPresent()){
            resultData.data(enumUtilMapper.toEnumUtilDefinition(optional.get()));
            return resultData.build();
        }
        return resultData.build();
    }

}
