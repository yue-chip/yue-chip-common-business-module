package com.yue.chip.upms.application.expose.impl.grid;

import com.yue.chip.core.IPageResultData;
import com.yue.chip.core.PageSerializable;
import com.yue.chip.core.YueChipPage;
import com.yue.chip.core.YueChipPageSerializable;
import com.yue.chip.exception.BusinessException;
import com.yue.chip.grid.GridExposeService;
import com.yue.chip.grid.vo.GridExposeVo;
import com.yue.chip.upms.assembler.organizational.GridMapper;
import com.yue.chip.upms.domain.aggregates.Grid;
import com.yue.chip.upms.domain.repository.organizational.OrganizationalRepository;
import com.yue.chip.upms.infrastructure.dao.organizational.GridDao;
import com.yue.chip.upms.infrastructure.dao.organizational.GridUserDao;
import com.yue.chip.upms.infrastructure.po.organizational.GridPo;
import com.yue.chip.upms.infrastructure.po.organizational.GridUserPo;
import com.yue.chip.upms.vo.UserExposeVo;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.data.domain.Page;
import org.springframework.util.CollectionUtils;

import java.util.*;
import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
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

    @Resource
    private GridDao gridDao;

    @Resource
    private GridUserDao gridUserDao;


    @Override
    public List<GridExposeVo> findGridByName(String name) {
        List<Grid> list = organizationalRepository.findGridByName(name);
        return gridMapper.toGridExposeVo(list);
    }

    @Override
    public List<Long> findAllByGridId(Long gridId) {
        List<Long> userIdList = new ArrayList<>();
        List<GridUserPo> gridUserPoList = gridUserDao.findAllByGridId(gridId);
        if (!CollectionUtils.isEmpty(gridUserPoList)) {
            List<Long> userIds = gridUserPoList.stream().map(GridUserPo::getUserId).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(userIds)) {
                userIdList.addAll(userIds);
            }
        }
        return userIdList;
    }

    @Override
    public PageSerializable<GridExposeVo> listGridQuery(Set<Long> organizationalIds, String name, YueChipPage yueChipPage, Set<Long> userIds, String time) {
        Page<Grid> page = organizationalRepository.listGridQuery(organizationalIds, name, yueChipPage, userIds, time);
        return new YueChipPageSerializable<GridExposeVo>(gridMapper.toGridExposeVo(page.getContent()),page.getPageable(),page.getTotalElements());
    }

    @Override
    public PageSerializable<UserExposeVo> findByGridIdIn(Set<Long> gridIds, String name, YueChipPage yueChipPage) {
        List<Grid> gridList = organizationalRepository.findByGridId(gridIds);
        Set<Long> userIds = gridList.stream().map(Grid::getUserId).collect(Collectors.toSet());
        IPageResultData<List<UserExposeVo>> pageResultData = organizationalRepository.findByUserIdIn(userIds, name, yueChipPage);
        return new YueChipPageSerializable(pageResultData.getData(),pageResultData.getPageable(),pageResultData.getTotalElements());
    }

    @Override
    public void deleteGrid(List<Long> ids) {
        if (Objects.nonNull(ids) && ids.size()>0) {
            if (ids.size() == 1) {
                Long id = ids.get(0);
                Optional<GridPo> gridPoOptional = gridDao.findById(id);
                if (gridPoOptional.get().getParentId() > 0) {
                    List<GridPo> gridPoList = gridDao.findAllByParentId(id);
                    if (!CollectionUtils.isEmpty(gridPoList)) {
                        throw new BusinessException("请先删除该网格下的所有的子网格再删除！");
                    }
                }
                if (gridPoOptional.get().getParentId() == 0) {
                    List<GridPo> gridPoList = gridDao.findAllByParentId(id);
                    if (!CollectionUtils.isEmpty(gridPoList)) {
                        throw new BusinessException("请先删除该网格下的所有的子网格再删除！");
                    }
                }
                gridDao.deleteById(id);
                List<Long> idList = gridUserDao.findAllByGridId(id).stream().map(GridUserPo::getId).collect(Collectors.toList());
                if (!CollectionUtils.isEmpty(idList)) {
                    idList.forEach(idd -> {
                        gridDao.deleteById(idd);
                    });
                }
            } else {
                ids.forEach(id->{
                    gridDao.deleteById(id);
                });
                List<Long> idList = gridUserDao.findAllByGridIdIn(ids).stream().map(GridUserPo::getId).collect(Collectors.toList());
                if (!CollectionUtils.isEmpty(idList)) {
                    gridUserDao.deleteByIds(idList);
                }
            }
        }
    }
}
