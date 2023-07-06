package com.yue.chip.common.business.application.expose.file.impl;

import com.yue.chip.common.business.assembler.file.FileMapper;
import com.yue.chip.common.business.definition.file.FileDefinition;
import com.yue.chip.common.business.domain.aggregates.file.File;
import com.yue.chip.common.business.domain.repository.file.FileRepository;
import com.yue.chip.common.business.expose.file.FileExposeService;
import jakarta.annotation.Resource;
import org.apache.dubbo.config.annotation.DubboService;

import java.util.Optional;

/**
 * @author Mr.Liu
 * @date 2023/7/6 下午3:36
 */
@DubboService(interfaceClass = FileExposeService.class)
public class FileExposeServiceImpl implements FileExposeService {

    @Resource
    private FileRepository fileRepository;

    @Resource
    private FileMapper fileMapper;

    @Override
    public Optional<FileDefinition> find(Long fileId) {
        Optional<File> optional = fileRepository.find(fileId);
        if (optional.isPresent()) {
            Optional.ofNullable(fileMapper.toFileDefinition(optional.get()));
        }
        return Optional.empty();
    }

    @Override
    public String getUrl(Long fileId) {
        Optional<FileDefinition> optional = find(fileId);
        if (optional.isPresent()){
            return optional.get().getUrl();
        }
        return "";
    }
}
