package com.yue.chip.common.business.domain.repository.enums;

import com.yue.chip.common.business.domain.aggregates.enums.EnumUtil;
import com.yue.chip.common.business.infrastructure.po.enmus.EnumUtilPo;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.util.List;
import java.util.Optional;

/**
 * @author Mr.Liu
 * @date 2023/7/6 上午11:25
 */
public interface EnumUtilRepository {

    /**
     * 保存枚举
     * @param enumUtilPo
     * @return
     */
    public EnumUtil save(@NotNull EnumUtilPo enumUtilPo);

    /**
     * 保存枚举
     *
     * @param enumUtilPos
     */
    public void save(@NotNull @Size(min = 1) List<EnumUtilPo> enumUtilPos);

    /**
     * 根据编码和版本查询枚举
     *
     * @param code
     * @param version
     * @return
     */
    public Optional<EnumUtil> find(@NotBlank String code, @NotBlank String version);
}
