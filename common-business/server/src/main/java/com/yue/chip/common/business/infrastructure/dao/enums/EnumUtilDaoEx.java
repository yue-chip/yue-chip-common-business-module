package com.yue.chip.common.business.infrastructure.dao.enums;

import com.yue.chip.common.business.infrastructure.po.enmus.EnumUtilPo;
import javax.validation.constraints.NotNull;

/**
 * @author Mr.Liu
 * @description: TODO
 * @date 2023/10/25 下午7:28
 */
public interface EnumUtilDaoEx {
    /**
     * 保存其它租户数据库中的枚举
     * @param enumUtilPo
     */
    public void saveOtherTenantEnum(@NotNull EnumUtilPo enumUtilPo);
}
