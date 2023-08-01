package com.yue.chip.common.business.infrastructure.repository.file.impl;

import com.yue.chip.common.business.assembler.file.FileMapper;
import com.yue.chip.common.business.domain.aggregates.file.File;
import com.yue.chip.common.business.domain.repository.file.FileRepository;
import com.yue.chip.common.business.infrastructure.dao.file.FileDao;
import com.yue.chip.common.business.infrastructure.dao.file.FileRelationalDao;
import com.yue.chip.common.business.infrastructure.po.file.FilePo;
import com.yue.chip.common.business.infrastructure.po.file.FileRelationalPo;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.Filter;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author Mr.Liu
 * @date 2023/6/13 下午4:33
 */
@Repository
public class FileRepositoryImpl implements FileRepository {

    @Resource
    private FileDao fileDao;

    @Resource
    private FileRelationalDao fileRelationalDao;

    @Resource
    private FileMapper fileMapper;

    @Override
    public File add(FilePo file) {
        file = fileDao.save(file);
        return fileMapper.toFile(file);
    }

    @Override
    public Optional<File> find(Long id) {
        if (Objects.isNull(id)) {
            return Optional.empty();
        }
        Optional<FilePo> optional = fileDao.findById(id);
        if (optional.isPresent()) {
            Optional.ofNullable(fileMapper.toFile(optional.get()));
        }
        return Optional.empty();
    }

    @Override
    public List<File> find(Long tableId,  String fileFieldName, String tableName) {
        List<FilePo> list = fileDao.find(tableId, fileFieldName, tableName);
        return fileMapper.toFile(list);
    }

    @Override
    public void save(Long tableId, String tableName, String fileFieldName, List<Long> fileIds) {
        fileRelationalDao.deleteByTableIdAndTableNameAndFileFieldName(tableId, tableName, fileFieldName);
        if (Objects.nonNull(tableId) && Objects.nonNull(tableName) && Objects.nonNull(fileFieldName) && Objects.nonNull(fileIds)) {
            List<FileRelationalPo> list = new ArrayList<>();
            fileIds.stream().filter(id->Objects.nonNull(id)).forEach(fileId ->{
                FileRelationalPo fileRelationalPo = FileRelationalPo.builder()
                        .fileFieldName(fileFieldName)
                        .fileId(fileId)
                        .tableId(tableId)
                        .tableName(tableName)
                        .build();
                list.add(fileRelationalPo);
            });
            fileRelationalDao.saveAll(list);
        }
    }
}
