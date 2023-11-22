package com.yue.chip.upms.infrastructure.dao.organizational;

import com.yue.chip.core.YueChipPage;
import com.yue.chip.core.persistence.curd.BaseDao;
import com.yue.chip.upms.infrastructure.po.organizational.GridPo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.bouncycastle.LICENSE;
import org.springframework.data.domain.Page;

import java.util.List;

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

}