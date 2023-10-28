package com.yue.chip.upms.application.expose.impl.organizational;

import com.yue.chip.upms.OrganizationalExpose;
import com.yue.chip.upms.assembler.organizational.OrganizationalMapper;
import com.yue.chip.upms.domain.aggregates.Organizational;
import com.yue.chip.upms.domain.repository.organizational.OrganizationalRepository;
import com.yue.chip.upms.vo.OrganizationalExposeVo;
import jakarta.annotation.Resource;
import org.apache.dubbo.config.annotation.DubboService;

import java.util.Optional;

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
    public OrganizationalExposeVo findById(Long id) {
        Optional<Organizational> optional = organizationalRepository.findById(id);
        if (optional.isPresent()) {
            return organizationalMapper.toOrganizationalExposVo(optional.get());
        }
        return null;
    }
}
