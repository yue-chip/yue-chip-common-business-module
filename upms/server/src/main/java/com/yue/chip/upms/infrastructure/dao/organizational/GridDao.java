package com.yue.chip.upms.infrastructure.dao.organizational;

import com.yue.chip.core.YueChipPage;
import com.yue.chip.core.persistence.curd.BaseDao;
import com.yue.chip.upms.infrastructure.po.organizational.GridPo;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.bouncycastle.LICENSE;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Set;

/**
 * @author Mr.Liu
 * @description: TODO
 * @date 2023/11/21 下午2:02
 */
public interface GridDao extends BaseDao<GridPo>, GridDaoEx {

    /**
     * 根据机构id删除网格
     * @param organizationalId
     * @return
     */
    public int deleteAllByOrganizationalId(@NotNull Long organizationalId);

    /**
     * 根据用户id删除网格
     * @param userId
     * @return
     */
    public int deleteAllByUserId(@NotNull Long userId);

    /**
     * 根据机构id查寻网格
     * @param organizationalId
     * @return
     */
    public List<GridPo> findAllByOrganizationalId(@NotNull Long organizationalId);

    /**
     * 根据网格id查询网格
     * @param gridId
     * @return
     */
    List<GridPo> findAllByIdIn(@NotNull Set<Long> gridId);

    /**
     * 根据名称模糊查询网格
     * @param name
     * @return
     */
    List<GridPo> findAllByNameLike(String name);

    /**
     * 根据parent_id查询
     * @return
     */
    List<GridPo> findAllByParentId(Long id);

}
