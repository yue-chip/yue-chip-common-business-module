package com.yue.chip.upms.assembler.organizational;

import com.yue.chip.grid.vo.GridTreeVo;
import com.yue.chip.upms.domain.aggregates.Grid;
import com.yue.chip.upms.infrastructure.po.organizational.GridPo;
import com.yue.chip.upms.interfaces.dto.organizational.GridAddDto;
import com.yue.chip.upms.interfaces.dto.organizational.GridAddDto2;
import com.yue.chip.upms.interfaces.dto.organizational.GridUpdateDto;
import com.yue.chip.upms.interfaces.dto.organizational.GridUpdateDto2;
import com.yue.chip.upms.interfaces.vo.organizational.GridVo;
import com.yue.chip.grid.vo.GridExposeVo;
import com.yue.chip.upms.interfaces.vo.organizational.GridVo2;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Set;

/**
 * @author Mr.Liu
 * @description: TODO
 * @date 2023/11/21 下午2:54
 */
@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface GridMapper {

    GridMapper INSTANCE = Mappers.getMapper(GridMapper.class);

    public GridPo toGridPo(GridAddDto gridAddDto);
    GridPo toGridPo(GridAddDto2 gridAddDto2);
    GridPo toGridPo(GridUpdateDto2 gridUpdateDto2);

    public GridPo toGridPo(GridUpdateDto gridUpdateDto);

    public Grid toGrid(GridPo gridPo);

    public List<Grid> toGrid(List<GridPo> gridPoList);

    public GridVo toGridVo(Grid grid);

    public List<GridVo> toGridVo(List<Grid> gridList);
    List<GridVo2> toListGridVo(List<GridPo> gridPoList);
    List<GridTreeVo> toListGridTreeVo(List<GridPo> gridPoList);

    public GridExposeVo toGridExposeVo(Grid grid);
    
    public List<GridExposeVo> toGridExposeVo(List<Grid> gridList);
}
