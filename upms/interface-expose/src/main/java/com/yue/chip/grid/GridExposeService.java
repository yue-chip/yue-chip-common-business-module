package com.yue.chip.grid;

import com.yue.chip.core.YueChipPage;
import com.yue.chip.grid.vo.GridExposeVo;
import com.yue.chip.upms.vo.UserExposeVo;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Set;

/**
 * @author xianming.chen
 * @description: TODO
 * @date 2023-12-06
 */
public interface GridExposeService {

    /**
     * 根据网格名称模糊查询网格信息
     * @param name
     * @return
     */
    List<GridExposeVo> findGridByName(String name);
    /**
     * 网格列表分页查询
     *
     * @param name
     * @return
     */

    Page<GridExposeVo> listGridQuery(Set<Long> organizationalIds, String name, YueChipPage yueChipPage, Set<Long> userIds);

    Page<UserExposeVo> findByGridIdIn(Set<Long> gridIds, YueChipPage yueChipPage);
}
