package com.yue.chip.upms.domain.repository.resources;

import com.yue.chip.core.repository.BaseRepository;
import com.yue.chip.upms.domain.aggregates.Resources;
import com.yue.chip.upms.enums.Scope;
import com.yue.chip.upms.infrastructure.po.resources.ResourcesPo;
import com.yue.chip.upms.interfaces.dto.resources.ResourcesAddDto;
import com.yue.chip.upms.interfaces.vo.resources.ResourcesTree;

import java.util.List;
import java.util.Optional;

/**
 * @author Mr.Liu
 * @date 2023/1/14 上午10:48
 * @description ResourcesRepository
 */
public interface ResourcesRepository extends BaseRepository<ResourcesPo> {

    /**
     * 根据用户id查询树形结构权限
     *
     * @param userId
     * @param parentId
     * @param scope
     * @return
     */
    public List<ResourcesTree> findResourcesToTree(Long userId, Long parentId, Scope scope);

    /**
     * 查询树形结构数据
     * @param parentId
     * @param scope
     * @return
     */
    public List<ResourcesTree> findResourcesToTree(Long parentId, Scope scope);

    /**
     * 根据编码查询
     * @param code
     * @return
     */
    public Optional<Resources> findByCode(String code);

    /**
     * 根据名称查询资源
     * @param name
     * @return
     */
    public Optional<Resources> findByName(String name);

    /**
     * 根据名称查询当前节点下的资源
     * @param name
     * @param parentId
     * @return
     */
    public Optional<Resources> findByNameAndParentId(String name,Long parentId);

    /**
     * 根据url查询资源
     * @param url
     * @return
     */
    public Optional<Resources> findByUrl(String url);

    /**
     * 新建
     *
     * @param resources
     * @return
     */
    public Resources save(ResourcesAddDto resources);
}
