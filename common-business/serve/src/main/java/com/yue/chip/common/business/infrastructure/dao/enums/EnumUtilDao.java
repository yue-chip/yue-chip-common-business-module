package com.yue.chip.common.business.infrastructure.dao.enums;

import com.yue.chip.common.business.infrastructure.po.enmus.EnumUtilPo;
import com.yue.chip.core.persistence.curd.BaseDao;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author Mr.Liu
 * @date 2023/7/6 上午11:27
 */
public interface EnumUtilDao extends BaseDao<EnumUtilPo> {

    /**
     * 根据编码和版本号删除
     * @param code
     * @param version
     * @return
     */
    @Transactional
    public int deleteByCodeAndVersion(@NotBlank String code,@NotBlank String version);

    /**
     * 根据编码和版本号查找枚举
     * @param code
     * @param version
     * @return
     */
    public Optional<EnumUtilPo> findFirstByCodeAndVersion(@NotBlank String code,@NotBlank String version);
}
