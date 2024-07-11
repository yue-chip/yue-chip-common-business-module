package com.yue.chip.grid;

import com.yue.chip.core.IResultData;
import com.yue.chip.core.PageSerializable;
import com.yue.chip.core.ResultData;
import com.yue.chip.core.YueChipPage;
import com.yue.chip.grid.vo.GridExposeVo;
import com.yue.chip.upms.vo.UserExposeVo;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    PageSerializable<GridExposeVo> listGridQuery(Set<Long> organizationalIds, String name, YueChipPage yueChipPage, Set<Long> userIds, String time);

    PageSerializable<UserExposeVo> findByGridIdIn(Set<Long> gridIds, String name, YueChipPage yueChipPage);

    /**
     * 删除网格
     * @param ids
     */
    void deleteGrid(@NotNull @Size(min = 0) List<Long> ids);
}
