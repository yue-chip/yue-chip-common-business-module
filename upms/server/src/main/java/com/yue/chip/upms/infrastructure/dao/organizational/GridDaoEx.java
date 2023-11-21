package com.yue.chip.upms.infrastructure.dao.organizational;

import com.yue.chip.core.YueChipPage;
import com.yue.chip.upms.infrastructure.po.organizational.GridPo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;

/**
 * @author Mr.Liu
 * @description: TODO
 * @date 2023/11/21 下午2:02
 */
public interface GridDaoEx {

    /**
     * 列表
     *
     * @param organizationalId
     * @param name
     * @param userName
     * @param yueChipPage
     * @return
     */
    public Page<GridPo> List(@NotNull Long organizationalId,@NotBlank String name, @NotBlank String userName, @NotNull YueChipPage yueChipPage);
}
