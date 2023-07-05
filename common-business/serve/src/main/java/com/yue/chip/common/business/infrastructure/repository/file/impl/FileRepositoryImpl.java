package com.yue.chip.common.business.infrastructure.repository.file.impl;

import com.yue.chip.common.business.assembler.file.FileMapper;
import com.yue.chip.common.business.domain.aggregates.file.File;
import com.yue.chip.common.business.domain.repository.file.FileRepository;
import com.yue.chip.common.business.infrastructure.dao.FileDao;
import com.yue.chip.common.business.infrastructure.po.file.FilePo;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Repository;

/**
 * @author Mr.Liu
 * @date 2023/6/13 下午4:33
 */
@Repository
public class FileRepositoryImpl implements FileRepository {

    @Resource
    private FileDao fileDao;

    @Resource
    private FileMapper fileMapper;

    @Override
    public File add(@NotNull FilePo file) {
        file = fileDao.save(file);
        return fileMapper.toFile(file);
    }
}
