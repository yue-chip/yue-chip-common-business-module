package com.yue.chip.upms.infrastructure.repository.impl.resources;

import com.yue.chip.core.repository.impl.BaseRepositoryImpl;
import com.yue.chip.upms.domain.repository.resources.ResourcesRepository;
import com.yue.chip.upms.enums.Scope;
import com.yue.chip.upms.infrastructure.assembler.resources.ResourcesMapper;
import com.yue.chip.upms.infrastructure.dao.resources.ResourcesDao;
import com.yue.chip.upms.infrastructure.po.resources.ResourcesPo;
import com.yue.chip.upms.interfaces.vo.resources.ResourcesTree;
import jakarta.annotation.Resource;
import org.mariadb.jdbc.client.socket.impl.PacketReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

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
        List<ResourcesPo> list = resourcesDao.findByUserId(userId,parentId,scope);
        List<ResourcesTree> treeList = new ArrayList<>();
        list.forEach(resourcesPo -> {
            ResourcesTree resourcesTree = resourcesMapper.toResourcesTree(resourcesPo);
            resourcesTree.setChild(findResourcesToTree(userId,resourcesPo.getId(),scope));
            treeList.add(resourcesTree);
        });
        return treeList;
    }

}
