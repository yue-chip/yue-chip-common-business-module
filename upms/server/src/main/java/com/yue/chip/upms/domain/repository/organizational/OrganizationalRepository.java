package com.yue.chip.upms.domain.repository.organizational;

import com.yue.chip.core.IPageResultData;
import com.yue.chip.core.YueChipPage;
import com.yue.chip.core.common.enums.State;
import com.yue.chip.upms.domain.aggregates.Grid;
import com.yue.chip.upms.domain.aggregates.Organizational;
import com.yue.chip.upms.infrastructure.po.organizational.GridPo;
import com.yue.chip.upms.infrastructure.po.organizational.OrganizationalPo;
import com.yue.chip.upms.infrastructure.po.organizational.OrganizationalUserPo;
import com.yue.chip.upms.interfaces.vo.organizational.GridVo;
import com.yue.chip.upms.interfaces.vo.organizational.OrganizationalTreeListVo;
import com.yue.chip.upms.vo.UserExposeVo;
import org.springframework.data.domain.Page;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * @author Mr.Liu
 * @description: TODO
 * @date 2023/10/7 下午6:16
 */
public interface OrganizationalRepository {

    /**
     * 根据用户ID查询关联的组织机构
     *
     * @param userId
     * @return
     */
    public Optional<Organizational> findByUserId(@NotNull Long userId);

    /**
     * 根据ID查询关联的组织机构
     *
     * @param id
     * @return
     */
    public Optional<Organizational> findById(@NotNull Long id);

    /**
     * 删除用户与组织机构的关联关系
     * @param userId
     */
    public void deleteOrganizationalByUserId(@NotNull Long userId);

    /**
     * 删除用户与组织机构的关联关系
     * @param organizationalId
     */
    public void deleteOrganizationalUserByOrganizationalId(@NotNull Long organizationalId);

    /**
     * 保存用户与组织架构的关联关系
     * @param organizationalUserPo
     */
    public void saveOrganizationalUser(@NotNull OrganizationalUserPo organizationalUserPo);

    /**
     * 保存组织架构
     * @param organizational
     */
    public void saveOrganizational(@NotNull OrganizationalPo organizational);

    /**
     * 删除祖籍机构
     * @param id
     */
    public void deleteOrganizationalById(@NotNull Long id);

    /**
     * 修改组织机构
     * @param organizational
     */
    public void updateOrganizational(@NotNull OrganizationalPo organizational);

    /**
     * 根据父节点id和机构名称查询机构
     * @param parentId
     * @param name
     * @return
     */
    public Optional<Organizational> findByParentIdAndName(@NotNull Long parentId, @NotBlank String name);

    /**
     * 查询树形结构
     *
     * @param parentId
     * @param state
     * @param name
     * @return
     */
    public List<OrganizationalTreeListVo> findTree(@NotNull Long parentId, @NotNull State state,String name);

    /**
     * 查询树形结构（当前登录用户所属的机构）
     * @param state
     * @return
     */
    public List<OrganizationalTreeListVo> findTree1( @NotNull State state);

    /**
     * 查村所有的子部门
     * @param parentId
     * @return
     */
    public List<Organizational> findAllChildren(@NotNull Long parentId);

    /**
     * 更新机构负责人
     *
     * @param userId
     */
    public void deleteLeader(Long userId);

    /**
     * 查询机构id数组信息
     * @param ids
     * @return
     */
    public List<OrganizationalPo> findByIdList(@NotNull @Size(min = 0) Set<Long> ids);

    public List<OrganizationalPo> findAll();

    public List<OrganizationalPo> findChildren(@NotNull Long parentId);

    public Page<OrganizationalPo> organizationalPoPage(@NotNull @Size(min = 0) List<Long> organizationalList, @NotNull YueChipPage yueChipPage);

    IPageResultData<UserExposeVo> organizationalPoList(List<Long> organizationalIds, String name, YueChipPage yueChipPage);

    public IPageResultData<UserExposeVo> findByUserIdIn(Set<Long> userIds, String name, YueChipPage yueChipPage);

    /**
     * 新增网格
     * @param gridPo
     */
    public void saveGrid(@NotNull GridPo gridPo);

    /**
     * 修改网格
     * @param gridPo
     */
    public void updateGrid(@NotNull GridPo gridPo);

    /**
     * 删除网格
     * @param ids
     */
    public void deleteGrid(@NotNull @Size(min = 0) List<Long> ids);

    /**
     * 根据用户id删除网格
     * @param userId
     */
    public void deleteGridByUserId(@NotNull Long userId);

    /**
     * 根据机构id删除网格
     * @param organizationalId
     */
    public void deleteGridByOrganizationalId(@NotNull Long organizationalId);

    /**
     * 详情
     *
     * @param id
     * @return
     */
    public Optional<Grid> gridDetails(@NotNull Long id);

    /**
     * 列表
     *
     * @param organizationalId
     * @param name
     * @param userName
     * @param yueChipPage
     * @return
     */
    Page<GridVo> listGrid(@NotNull Long organizationalId, String name, String userName, YueChipPage yueChipPage);

    /**
     * 分页列表
     *
     * @param organizationalIds
     * @param name
     * @param yueChipPage
     * @return
     */
    Page<Grid> listGridQuery(@NotNull Set<Long> organizationalIds, String name, YueChipPage yueChipPage, Set<Long> userIds, String time);

    /**
     * 根据机构id查寻网格
     * @param organizationalId
     * @return
     */
    List<Grid> listGrid(@NotNull Long organizationalId);

    /**
     * 根据网格id查询网格
     * @param gridId
     * @return
     */
    List<Grid> findByGridId(@NotNull Set<Long> gridId);

    /**
     * 根据机构id和用户id查询机构下的消防管理员
     * @param organizationalId
     * @param userId
     * @return
     */
    List<OrganizationalUserPo> findUserAllByOrganizationalIdAndUserIdIn(Long organizationalId, Set<Long> userId);
    /**
     * 根据用户id查询机构
     * @param userId
     * @return
     */
    List<OrganizationalUserPo> findUserAllByUserIdIn(Set<Long> userId);

    /**
     * 通过名称查询网格
     * @param name
     * @return
     */
    List<Grid> findGridByName(String name);
}
