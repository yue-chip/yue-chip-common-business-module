package com.yue.chip.common.business.application.expose.file.impl;

import com.yue.chip.common.business.assembler.file.FileMapper;
import com.yue.chip.common.business.definition.file.FileDefinition;
import com.yue.chip.common.business.domain.aggregates.file.File;
import com.yue.chip.common.business.domain.repository.file.FileRepository;
import com.yue.chip.common.business.expose.file.FileExposeService;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotNull;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    @Override
    public Map<Long, String> getUrl(Long tableId, String fileFieldName,String tableName) {
        if (Objects.isNull(tableId) || !StringUtils.hasText(tableName)|| !StringUtils.hasText(fileFieldName)) {
            return Collections.EMPTY_MAP;
        }
        List<File> list = fileRepository.find(tableId, fileFieldName, tableName);
        Map<Long, String> urls = new HashMap<>();
        if (Objects.nonNull(list)) {
            list.forEach(file -> {
                urls.put(file.getId(), file.getUrl());
            });
        }
        return urls;
    }

    @Override
    public String getUrlSingle(Long tableId, String fileFieldName, String tableName) {
        Map<Long, String> fileMap = getUrl(tableId, fileFieldName, tableName);
        if (fileMap.size()>0) {
            Long id = (Long) fileMap.keySet().toArray()[0];
            return fileMap.get(id);
        }
        return "";
    }

    @Override
    public void save(Long tableId, String tableName, String fileFieldName, List<Long> fileIds) {
        fileRepository.save(tableId, tableName, fileFieldName, fileIds);
    }

    @Override
    public void save(Long tableId, String tableName, String fileFieldName, @NotNull Long... fileId) {
        if (Objects.isNull(fileId)) {
            return;
        }
        List<Long> fileIds = Stream.of(fileId).collect(Collectors.toList());
        save(tableId,fileFieldName,tableName,fileIds);
    }
}
