package com.yue.chip.common.business.expose.file;

import com.yue.chip.common.business.definition.file.FileDefinition;
import com.yue.chip.core.ResultData;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Mr.Liu
 * @date 2023/7/6 下午3:32
 */
@HttpExchange("${http.exchange.host}")
public interface RemoteFile extends RemoteFileDefinition {

    @Override
    @GetExchange(PREFIX+FIND)
    public ResultData<FileDefinition> find(  @RequestParam(value = "fileId")Long fileId);

    @Override
    @GetExchange(PREFIX+URL)
    public ResultData<Map<String,String>> url(  @RequestParam(value = "tableId", required = false)Long tableId,
                                                @RequestParam(value = "fileFieldName")  String fileFieldName,
                                                @RequestParam(value = "tableName") String tableName);

    @Override
    @GetExchange(PREFIX+URLS)
    ResultData<Map<String, String>> urls(@RequestParam(value = "tableIds", required = false) ArrayList<Long> tableIds,
                                @RequestParam(value = "fileFieldName") String fileFieldName,
                                @RequestParam(value = "tableName")String tableName);

    @Override
    @GetExchange(PREFIX+URL_SINGLE)
    public ResultData<String> urlSingle( @RequestParam(value = "tableId")Long tableId,
                                         @RequestParam(value = "fileFieldName")String fileFieldName,
                                         @RequestParam(value = "tableName")String tableName);

    @Override
    @PostExchange(PREFIX+SAVE)
    public ResultData<List<Long>> save( @RequestParam(value = "tableId") Long tableId,
                                        @RequestParam(value = "tableName") String tableName,
                                        @RequestParam(value = "fileFieldName") String fileFieldName,
                                        @RequestParam(value = "fileIds", required = false) List<Long> fileIds);

}
