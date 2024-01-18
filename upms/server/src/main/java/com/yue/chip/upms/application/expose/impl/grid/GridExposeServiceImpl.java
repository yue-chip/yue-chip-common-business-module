package com.yue.chip.upms.application.expose.impl.grid;

import com.yue.chip.core.YueChipPage;
import com.yue.chip.grid.GridExposeService;
import com.yue.chip.grid.vo.GridExposeVo;
import com.yue.chip.upms.assembler.organizational.GridMapper;
import com.yue.chip.upms.domain.aggregates.Grid;
import com.yue.chip.upms.domain.repository.organizational.OrganizationalRepository;
import com.yue.chip.upms.vo.UserExposeVo;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author xianming.chen
 * @description: TODO
 * @date 2023-12-06
 */

@DubboService(interfaceClass = GridExposeService.class)
public class GridExposeServiceImpl implements GridExposeService {

    @Resource
    private OrganizationalRepository organizationalRepository;

    @Resource
    private GridMapper gridMapper;


    @Override
    public List<GridExposeVo> findGridByName(String name) {
        List<Grid> list = organizationalRepository.findGridByName(name);
        return gridMapper.toGridExposeVo(list);
    }

    @Override
    public Page<GridExposeVo> listGridQuery(Set<Long> organizationalIds, String name, YueChipPage yueChipPage, Set<Long> userIds, String time) {
        Page<Grid> page = organizationalRepository.listGridQuery(organizationalIds, name, yueChipPage, userIds, time);
        return new PageImpl<GridExposeVo>(gridMapper.toGridExposeVo(page.getContent()),page.getPageable(),page.getTotalElements());
    }

    @Override
    public Page<UserExposeVo> findByGridIdIn(Set<Long> gridIds, String name, YueChipPage yueChipPage) {
        List<Grid> gridList = organizationalRepository.findByGridId(gridIds);
        Set<Long> userIds = gridList.stream().map(Grid::getUserId).collect(Collectors.toSet());
        return organizationalRepository.findByUserIdIn(userIds, name, yueChipPage);
    }
}
