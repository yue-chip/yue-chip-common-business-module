package com.yue.chip.upms.application.expose.impl.organizational;

import com.yue.chip.core.Optional;
import com.yue.chip.organizational.OrganizationalExposeService;
import com.yue.chip.upms.assembler.organizational.OrganizationalMapper;
import com.yue.chip.upms.domain.aggregates.Organizational;
import com.yue.chip.upms.domain.repository.organizational.OrganizationalRepository;
import com.yue.chip.upms.infrastructure.po.organizational.OrganizationalPo;
import com.yue.chip.organizational.vo.OrganizationalExposeVo;
import jakarta.annotation.Resource;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * @author xianming.chen
 * @description: TODO
 * @date 2023-10-28
 */
@DubboService(interfaceClass = OrganizationalExposeService.class )
public class OrganizationalExposeServiceImpl implements OrganizationalExposeService {

    @Resource
    private OrganizationalRepository organizationalRepository;

    @Resource
    private OrganizationalMapper organizationalMapper;

    @Override
    public Optional<OrganizationalExposeVo> findById(Long id) {
        java.util.Optional<Organizational> optional = organizationalRepository.findById(id);
        if (optional.isPresent()) {
            return Optional.ofNullable(organizationalMapper.toOrganizationalExposVo(optional.get()));
        }
        return Optional.empty();
    }

    @Override
    public List<OrganizationalExposeVo> findByIdList(Set<Long> ids) {
        List<OrganizationalPo> byIdList = organizationalRepository.findByIdList(ids);
        List<OrganizationalExposeVo> list = new ArrayList<>();
        if (!CollectionUtils.isEmpty(byIdList)) {
            byIdList.forEach(po -> {
                OrganizationalExposeVo organizationalExposeVo = organizationalMapper.toOrganizationalExposeVo(po);
                list.add(organizationalExposeVo);
            });
        }
        return list;
    }

    @Override
    public List<OrganizationalExposeVo> findAll() {
        List<OrganizationalPo> organizationalPoList = organizationalRepository.findAll();
        List<OrganizationalExposeVo> list = new ArrayList<>();
        if (!CollectionUtils.isEmpty(organizationalPoList)) {
            organizationalPoList.forEach(po -> {
                OrganizationalExposeVo organizationalExposeVo = organizationalMapper.toOrganizationalExposeVo(po);
                list.add(organizationalExposeVo);
            });
        }
        return list;
    }

    @Override
    public Set<Long> findAllChildrenOrganizationalIds(Long parentId) {
        Set<Long> childrenIds = new HashSet<>();
        List<Organizational> allChildren = organizationalRepository.findAllChildren(parentId);
        if (!CollectionUtils.isEmpty(allChildren)) {
            Set<Long> ids = allChildren.stream().map(Organizational::getId).collect(Collectors.toSet());
            childrenIds.addAll(ids);
        }
        return childrenIds;
    }
}
