package com.yue.chip.common.business.infrastructure.repository.file.impl;

import com.yue.chip.common.business.domain.repository.file.FileRepository;
import com.yue.chip.common.business.infrastructure.dao.FileDao;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;

/**
 * @author Mr.Liu
 * @date 2023/6/13 下午4:33
 */
@Repository
public class FileRepositoryImpl implements FileRepository {

    @Resource
    private FileDao fileDao;
}
