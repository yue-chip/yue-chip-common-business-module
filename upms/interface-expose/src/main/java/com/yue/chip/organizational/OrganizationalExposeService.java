package com.yue.chip.organizational;

import com.yue.chip.core.Optional;
import com.yue.chip.organizational.vo.OrganizationalExposeVo;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.Set;

/**
 * @author xianming.chen
 * @description: TODO
 * @date 2023-10-28
 */
public interface OrganizationalExposeService {

    /**
     * 根据id查询机构
     *
     * @param id
     * @return
     */
    public Optional<OrganizationalExposeVo> findById(@NotNull Long id);

    /**
     * 查询机构id数组信息
     * @param ids
     * @return
     */
    public List<OrganizationalExposeVo> findByIdList(Set<Long> ids);

    public List<OrganizationalExposeVo> findAll();
}
