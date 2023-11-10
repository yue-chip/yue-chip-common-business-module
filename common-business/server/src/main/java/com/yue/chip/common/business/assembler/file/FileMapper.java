package com.yue.chip.common.business.assembler.file;

import com.yue.chip.common.business.definition.file.FileDefinition;
import com.yue.chip.common.business.domain.aggregates.file.File;
import com.yue.chip.common.business.infrastructure.po.file.FilePo;
import com.yue.chip.common.business.interfaces.vo.file.FileVo;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author Mr.Liu
 * @date 2023/7/5 下午5:00
 */
@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FileMapper {
    FileMapper INSTANCE = Mappers.getMapper(FileMapper.class);

    public FilePo toFilePo(File file);

    public File toFile(FilePo filePo);

    public List<File> toFile(List<FilePo> filePoList);

    public FileVo toFileVo(File file);

    public FileDefinition toFileDefinition(FilePo filePo);

    public FileDefinition toFileDefinition(File filePo);
}
