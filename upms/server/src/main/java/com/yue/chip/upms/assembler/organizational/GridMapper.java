package com.yue.chip.upms.assembler.organizational;

import com.yue.chip.upms.domain.aggregates.Grid;
import com.yue.chip.upms.infrastructure.po.organizational.GridPo;
import com.yue.chip.upms.interfaces.dto.organizational.GridAddDto;
import com.yue.chip.upms.interfaces.dto.organizational.GridUpdateDto;
import com.yue.chip.upms.interfaces.vo.organizational.GridVo;
import com.yue.chip.upms.vo.GridExposeVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author Mr.Liu
 * @description: TODO
 * @date 2023/11/21 下午2:54
 */
@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface GridMapper {

    GridMapper INSTANCE = Mappers.getMapper(GridMapper.class);

    public GridPo toGridPo(GridAddDto gridAddDto);

    public GridPo toGridPo(GridUpdateDto gridUpdateDto);

    public Grid toGrid(GridPo gridPo);

    public List<Grid> toGrid(List<GridPo> gridPoList);

    public GridVo toGridVo(Grid grid);

    public List<GridVo> toGridVo(List<Grid> gridList);

    public GridExposeVo toGridExposeVo(Grid grid);
    
    public List<GridExposeVo> toGridExposeVo(List<Grid> gridList);
}
