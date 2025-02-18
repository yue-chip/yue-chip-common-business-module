package com.yue.chip.upms.application.expose.impl.enums;

import com.yue.chip.core.Optional;
import com.yue.chip.core.common.EnumPersistenceExposeService;
import com.yue.chip.core.common.enums.EnumPersistenceBean;
import com.yue.chip.core.common.enums.EnumUtilDefinition;
import com.yue.chip.upms.assembler.enums.EnumUtilMapper;
import com.yue.chip.upms.domain.aggregates.enums.EnumUtil;
import com.yue.chip.upms.domain.repository.enums.EnumUtilRepository;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotBlank;
import org.apache.dubbo.config.annotation.DubboService;

import java.util.List;
import java.util.Objects;

@DubboService(interfaceClass = EnumPersistenceExposeService.class)
public class EnumPersistenceServiceExposeImpl implements EnumPersistenceExposeService {

    @Resource
    private EnumUtilRepository enumUtilRepository;

    @Resource
    private EnumUtilMapper enumUtilMapper;

    @Override
    public void save(List<EnumPersistenceBean> list) {
        if (Objects.nonNull(list) && !list.isEmpty()) {
            list.forEach(bean -> {
                enumUtilRepository.save(enumUtilMapper.toEnumUtilPo(bean));
            });
        }
    }

    @Override
    public Optional<EnumUtilDefinition> get(@NotBlank String code, @NotBlank String version) {
        java.util.Optional<EnumUtil> enumUtilOptional = java.util.Optional.empty();
        if (Objects.nonNull(code) && Objects.nonNull(version)) {
            enumUtilOptional = enumUtilRepository.find(code,version);
        }else if (Objects.nonNull(code)  && Objects.isNull(version)) {
            enumUtilOptional = enumUtilRepository.find(code);
        }
        if (enumUtilOptional.isPresent()) {
            return Optional.ofNullable(enumUtilMapper.toEnumUtilPo(enumUtilOptional.get()));
        }
        return Optional.empty();
    }
}
