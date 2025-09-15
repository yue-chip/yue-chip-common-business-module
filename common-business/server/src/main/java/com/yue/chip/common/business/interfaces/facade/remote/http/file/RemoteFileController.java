package com.yue.chip.common.business.interfaces.facade.remote.http.file;

import com.yue.chip.common.business.assembler.file.FileMapper;
import com.yue.chip.common.business.definition.file.FileDefinition;
import com.yue.chip.common.business.domain.aggregates.file.File;
import com.yue.chip.common.business.domain.repository.file.FileRepository;
import com.yue.chip.common.business.expose.file.RemoteFileDefinition;
import com.yue.chip.core.ResultData;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.java.Log;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController()
@RequestMapping("")
@Validated
@Tag(name = "远程文件")
@Log
public class RemoteFileController implements RemoteFileDefinition {
    @Resource
    private FileRepository fileRepository;

    @Resource
    private FileMapper fileMapper;

    @Override
    @GetMapping(FIND)
    public ResultData<FileDefinition> find(Long fileId) {
        Optional<File> optional = fileRepository.find(fileId);
        ResultData.ResultDataBuilder<FileDefinition>  builder = ResultData.builder();
        if (optional.isPresent()) {
            return builder.data(fileMapper.toFileDefinition(optional.get())).build();
        }
        return builder.build();
    }

    @Override
    @GetMapping(URL)
    public ResultData<Map<String, String>> url(Long tableId, String fileFieldName, String tableName) {
        ResultData.ResultDataBuilder<Map<String, String>> builder = ResultData.builder();
        if (Objects.isNull(tableId) || !StringUtils.hasText(tableName)|| !StringUtils.hasText(fileFieldName)) {
            return builder.build();
        }
        List<File> list = fileRepository.find(tableId, fileFieldName, tableName);
        Map<String, String> urls = new HashMap<>();
        if (Objects.nonNull(list)) {
            list.forEach(file -> {
                urls.put(String.valueOf(file.getId()), file.getUrl());
            });
        }
        return  builder.data(urls).build();

    }

    @Override
    @GetMapping(URLS)
    public ResultData<Map<String, String>> urls(ArrayList<Long> tableIds, String fileFieldName, String tableName) {
        ResultData.ResultDataBuilder<Map<String, String>> builder = ResultData.builder();
        if (CollectionUtils.isEmpty(tableIds) || !StringUtils.hasText(tableName)|| !StringUtils.hasText(fileFieldName)) {
            return builder.build();
        }
        List<File> list = fileRepository.find(tableIds, fileFieldName, tableName);
        Map<String, String> urls = new HashMap<>();
        if (Objects.nonNull(list)) {
            list.forEach(file -> {
                urls.put(String.valueOf(file.getId()), file.getUrl());
            });
        }
        return  builder.data(urls).build();
    }

    @Override
    @GetMapping(URL_SINGLE)
    public ResultData<String> urlSingle(Long tableId, String fileFieldName, String tableName) {
        ResultData.ResultDataBuilder<String> builder = ResultData.builder();
        ResultData<Map<String, String>> resultData = url(tableId, fileFieldName, tableName);
        if (Objects.nonNull(resultData.getData())) {
            Map<String, String>  fileMap = resultData.getData();
            if (fileMap.size()>0) {
                String id = (String) fileMap.keySet().toArray()[0];
                return builder.data(String.valueOf(fileMap.get(id))).build();
            }
        }
        return builder.build();
    }

    @Override
    @GetMapping(SAVE)
    public ResultData<List<Long>> save(Long tableId, String tableName, String fileFieldName, List<Long> fileIds) {
        fileRepository.save(tableId, tableName, fileFieldName, fileIds);
        ResultData.ResultDataBuilder<List<Long>> builder = ResultData.builder();
        return builder.data(fileIds).build();
    }
}
