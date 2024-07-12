package com.yue.chip.common.business.application.expose.file.impl;

import com.yue.chip.common.business.assembler.file.FileMapper;
import com.yue.chip.common.business.definition.file.FileDefinition;
import com.yue.chip.common.business.domain.aggregates.file.File;
import com.yue.chip.common.business.domain.repository.file.FileRepository;
import com.yue.chip.common.business.expose.file.FileExposeService;
import com.yue.chip.common.business.expose.sms.SmsExposeService;
import jakarta.annotation.Resource;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.util.StringUtils;

import java.util.*;

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

    @DubboReference
    private SmsExposeService smsExposeService;

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
        smsExposeService.sendSms();
        return "";
    }

    @Override
    public Map<String, String> getUrl(Long tableId, String fileFieldName, String tableName, Long tenantNumber) {
        if (Objects.isNull(tableId) || !StringUtils.hasText(tableName)|| !StringUtils.hasText(fileFieldName)) {
            return Collections.EMPTY_MAP;
        }
        List<File> list = fileRepository.find(tableId, fileFieldName, tableName);
        Map<String, String> urls = new HashMap<>();
        if (Objects.nonNull(list)) {
            list.forEach(file -> {
                urls.put(String.valueOf(file.getId()), file.getUrl());
            });
        }
        return urls;
    }

    @Override
    public Map<String, String> getUrl(Long tableId, String fileFieldName, String tableName) {
        if (Objects.isNull(tableId) || !StringUtils.hasText(tableName)|| !StringUtils.hasText(fileFieldName)) {
            return Collections.EMPTY_MAP;
        }
        List<File> list = fileRepository.find(tableId, fileFieldName, tableName);
        Map<String, String> urls = new HashMap<>();
        if (Objects.nonNull(list)) {
            list.forEach(file -> {
                urls.put(String.valueOf(file.getId()), file.getUrl());
            });
        }
        return urls;
    }

    @Override
    public String getUrlSingle(Long tableId, String fileFieldName, String tableName, Long tenantNumber) {
        Map<String, String> fileMap = getUrl(tableId, fileFieldName, tableName, tenantNumber);
        if (fileMap.size()>0) {
            String id = (String) fileMap.keySet().toArray()[0];
            return fileMap.get(id);
        }
        return "";
    }

    @Override
    public String getUrlSingle(Long tableId, String fileFieldName, String tableName) {
        Map<String, String> fileMap = getUrl(tableId, fileFieldName, tableName);
        if (fileMap.size()>0) {
            String id = (String) fileMap.keySet().toArray()[0];
            return fileMap.get(id);
        }
        return "";
    }

    @Override
    public List<Long> save(Long tableId, String tableName, String fileFieldName, List<Long> fileIds, Long tenantNumber) {
        fileRepository.save(tableId, tableName, fileFieldName, fileIds);
        return fileIds;
    }

    @Override
    public List<Long> save(Long tableId, String tableName, String fileFieldName, List<Long> fileIds) {
        fileRepository.save(tableId, tableName, fileFieldName, fileIds);
        return fileIds;
    }

    @Override
    public List<Long> save(Long tableId, String tableName, String fileFieldName, Long fileId, Long tenantNumber) {
        List<Long> fileIds = new ArrayList<>();
        fileIds.add(fileId);
        return save(tableId, tableName, fileFieldName, fileIds, tenantNumber);
    }

    @Override
    public List<Long> save(Long tableId, String tableName, String fileFieldName, Long fileId) {
        List<Long> fileIds = new ArrayList<>();
        fileIds.add(fileId);
        return save(tableId, tableName, fileFieldName, fileIds);
    }

    @Override
    public String testSendSms() {
        smsExposeService.sendSms();
        return "";
    }
//
//    @Override
//    public List<Long> save(Long tableId, String tableName, String fileFieldName, Long... fileId) {
//        if (Objects.isNull(fileId)) {
//            return Collections.EMPTY_LIST;
//        }
//        List<Long> fileIds = Stream.of(fileId).filter(id -> Objects.nonNull(id)).collect(Collectors.toList());
//        save(tableId,fileFieldName,tableName,fileIds);
//        return fileIds;
//    }
}
