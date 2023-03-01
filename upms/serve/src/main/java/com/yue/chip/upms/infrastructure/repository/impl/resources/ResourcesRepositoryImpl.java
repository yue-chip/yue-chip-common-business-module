package com.yue.chip.upms.infrastructure.repository.impl.resources;

import com.yue.chip.core.repository.impl.BaseRepositoryImpl;
import com.yue.chip.upms.domain.aggregates.Resources;
import com.yue.chip.upms.domain.repository.resources.ResourcesRepository;
import com.yue.chip.upms.enums.Scope;
import com.yue.chip.upms.infrastructure.assembler.resources.ResourcesMapper;
import com.yue.chip.upms.infrastructure.dao.resources.ResourcesDao;
import com.yue.chip.upms.infrastructure.po.resources.ResourcesPo;
import com.yue.chip.upms.interfaces.dto.resources.ResourcesAddDto;
import com.yue.chip.upms.interfaces.vo.resources.ResourcesTree;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author Mr.Liu
 * @date 2023/1/14 上午10:49
 * @description ResourcesRepositoryImpl
 */
@Repository
public class ResourcesRepositoryImpl extends BaseRepositoryImpl<ResourcesPo> implements ResourcesRepository {

    @Resource
    private ResourcesDao resourcesDao;

    @Resource
    private ResourcesMapper resourcesMapper;

    @Override
    public List<ResourcesTree> findResourcesToTree(Long userId, Long parentId, Scope scope) {
        List<ResourcesPo> list = new ArrayList<>();
        if (Objects.nonNull(userId)) {
            list = resourcesDao.findByUserId(userId,parentId,scope);
        }else {
            list = resourcesDao.findByParentIdAndScopeOrderBySort(parentId,scope);
        }
        List<ResourcesTree> treeList = new ArrayList<>();
        list.forEach(resourcesPo -> {
            ResourcesTree resourcesTree = resourcesMapper.toResourcesTree(resourcesPo);
            resourcesTree.setChildren(findResourcesToTree(userId,resourcesPo.getId(),scope));
            treeList.add(resourcesTree);
        });
        return treeList;
    }

    @Override
    public List<ResourcesTree> findResourcesToTree(Long parentId, Scope scope) {
        return findResourcesToTree(null,parentId,scope);
    }

    @Override
    public Optional<Resources> findByCode(String code) {
        return convertResources(resourcesDao.findFirstByCode(code));
    }

    @Override
    public Optional<Resources> findByName(String name) {
        return convertResources(resourcesDao.findFirstByName(name));
    }

    @Override
    public Optional<Resources> findByNameAndParentId(String name, Long parentId) {
        return convertResources(resourcesDao.findFirstByNameAndParentId(name,parentId));
    }

    @Override
    public Optional<Resources> findByUrl(String url) {
        return convertResources(resourcesDao.findFirstByUrl(url));
    }

    @Override
    public Resources save(ResourcesAddDto resources) {
        ResourcesPo resourcesPo = resourcesMapper.toResourcesPo(resources);
        resourcesPo = this.save(resourcesPo);
        return resourcesMapper.toResources(resourcesPo);
    }

    private Optional<Resources> convertResources(Optional<ResourcesPo> optional) {
        if (optional.isPresent()) {
            return Optional.ofNullable(resourcesMapper.toResources(optional.get()));
        }
        return Optional.empty();
    }

}
