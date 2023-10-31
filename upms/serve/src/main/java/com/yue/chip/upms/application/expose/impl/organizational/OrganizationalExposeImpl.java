package com.yue.chip.upms.application.expose.impl.organizational;

import com.yue.chip.core.Optional;
import com.yue.chip.upms.OrganizationalExpose;
import com.yue.chip.upms.assembler.organizational.OrganizationalMapper;
import com.yue.chip.upms.domain.aggregates.Organizational;
import com.yue.chip.upms.domain.repository.organizational.OrganizationalRepository;
import com.yue.chip.upms.infrastructure.po.organizational.OrganizationalPo;
import com.yue.chip.upms.vo.OrganizationalExposeVo;
import jakarta.annotation.Resource;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


/**
 * @author xianming.chen
 * @description: TODO
 * @date 2023-10-28
 */
@DubboService(interfaceClass = OrganizationalExpose.class )
public class OrganizationalExposeImpl implements OrganizationalExpose {

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
}
