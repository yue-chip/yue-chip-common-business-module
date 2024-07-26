package com.yue.chip.upms.domain.service.upms.impl;

import com.yue.chip.upms.domain.aggregates.Organizational;
import com.yue.chip.upms.domain.aggregates.Resources;
import com.yue.chip.upms.domain.repository.organizational.OrganizationalRepository;
import com.yue.chip.upms.domain.repository.upms.UpmsRepository;
import com.yue.chip.upms.domain.service.upms.UpmsDomainService;
import com.yue.chip.upms.infrastructure.po.organizational.OrganizationalUserPo;
import com.yue.chip.upms.infrastructure.po.role.RoleResourcesPo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Mr.Liu
 * @date 2023/3/6 上午11:29
 */
@Service
public class UpmsDomainServiceImpl implements UpmsDomainService {

    private static final Logger log = LoggerFactory.getLogger(UpmsDomainServiceImpl.class);
    @Resource
    private UpmsRepository upmsRepository;

    @Resource
    private OrganizationalRepository organizationalRepository;

    @Override
    public void roleResources(Long roleId, Long[] resourcesIds) {
        List<RoleResourcesPo> list = new ArrayList<>();
        if (Objects.nonNull(resourcesIds)) {
            List<Long> newResourcesIds = new ArrayList<>();
            newResourcesIds.addAll(Arrays.stream(resourcesIds).toList());
            for (Long id : resourcesIds) {
                List<Long> allParentId = getAllParentId(id);
                newResourcesIds.forEach(i->{
                    if (allParentId.contains(i)) {
                        allParentId.remove(i);
                    }
                });
                newResourcesIds.addAll(allParentId);
            }

            newResourcesIds.forEach( i ->{
                RoleResourcesPo roleResourcesPo = RoleResourcesPo.builder()
                        .resourcesId(i)
                        .roleId(roleId)
                        .build();
                list.add(roleResourcesPo);
            });
            upmsRepository.saveAllRoleResources(list);
        }
    }

    @Override
    public void userOrganizational(Long userId, List<Long> organizationalId) {
        List<Organizational> organizationalList = organizationalRepository.findByUserId(userId);
        if (!organizationalList.isEmpty()) {
            organizationalList.forEach(organizational -> {
                if (!Objects.equals(organizationalId,organizational.getId()) ){
                    //清楚机构负责人
                    organizationalRepository.deleteLeader(userId);
                }
            });
        }
        organizationalRepository.deleteOrganizationalByUserId(userId);
        if (Objects.nonNull(organizationalId) && !organizationalId.isEmpty()) {
            organizationalId.forEach(id -> {
                organizationalRepository.saveOrganizationalUser(
                        OrganizationalUserPo.builder()
                                .organizationalId(id)
                                .userId(userId)
                                .build()
                );
            });
        }
    }

    private List<Long> getAllParentId(Long resourcesId) {
        List<Resources> list = new ArrayList<>();
        getParent(resourcesId,list);
        List<Long> returnList = new ArrayList<>();
        list.forEach(resources -> {
            returnList.add(resources.getId());
        });
        return returnList;
    }

    private void getParent(Long resourcesId,List<Resources> list) {
        Optional<Resources> optional = upmsRepository.findResourcesById(resourcesId);
        if (optional.isPresent()) {
            Optional<Resources> optionalResources = optional.get().getParent();
            if (optionalResources.isPresent()){
                list.add(optionalResources.get());
                getParent(optionalResources.get().getId(),list);
            }
        }
    }
}
