package com.yue.chip.upms;

import com.yue.chip.upms.vo.OrganizationalExposeVo;
import jakarta.validation.constraints.NotNull;

/**
 * @author xianming.chen
 * @description: TODO
 * @date 2023-10-28
 */
public interface OrganizationalExpose {

    /**
     * 根据id查询机构
     *
     * @param id
     * @return
     */
    public OrganizationalExposeVo findById(@NotNull Long id);
}
