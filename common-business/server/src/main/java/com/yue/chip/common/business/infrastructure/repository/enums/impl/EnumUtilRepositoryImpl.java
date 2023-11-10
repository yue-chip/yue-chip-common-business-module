package com.yue.chip.common.business.infrastructure.repository.enums.impl;

import com.yue.chip.common.business.assembler.enums.EnumUtilMapper;
import com.yue.chip.common.business.domain.aggregates.enums.EnumUtil;
import com.yue.chip.common.business.domain.repository.enums.EnumUtilRepository;
import com.yue.chip.common.business.infrastructure.dao.enums.EnumUtilDao;
import com.yue.chip.common.business.infrastructure.po.enmus.EnumUtilPo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * @author Mr.Liu
 * @date 2023/7/6 上午11:26
 */
@Repository
public class EnumUtilRepositoryImpl implements EnumUtilRepository {

    @Resource
    private EnumUtilDao enumUtilDao;

    @Resource
    private EnumUtilMapper enumUtilMapper;



    @Override
    public EnumUtil save(EnumUtilPo enumUtilPo) {
        if (StringUtils.hasText(enumUtilPo.getCode()) && StringUtils.hasText(enumUtilPo.getVersion())) {
            enumUtilDao.deleteByCodeAndVersion(enumUtilPo.getCode(),enumUtilPo.getVersion());
        }
        enumUtilPo = enumUtilDao.save(enumUtilPo);
        return enumUtilMapper.toEnumUtil(enumUtilPo);
    }

    @Override
    public void save(List<EnumUtilPo> enumUtilPos) {
        if (Objects.isNull(enumUtilPos) || enumUtilPos.size() == 0) {
            return ;
        }
        enumUtilPos.forEach(enumUtilPo -> {
            //保存枚举
            save(enumUtilPo);
            //保存到其它租户数据库中的枚举
            enumUtilDao.saveOtherTenantEnum(enumUtilPo);
        });
    }

    @Override
    public Optional<EnumUtil> find(String code, String version) {
        Optional<EnumUtilPo> optional = enumUtilDao.findFirstByCodeAndVersion(code,version);
        if (optional.isPresent()) {
            return Optional.ofNullable(enumUtilMapper.toEnumUtil(optional.get()));
        }
        return Optional.empty();
    }
}
