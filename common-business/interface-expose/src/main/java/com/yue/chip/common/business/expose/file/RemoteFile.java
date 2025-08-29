package com.yue.chip.common.business.expose.file;

import com.yue.chip.common.business.definition.file.FileDefinition;
import com.yue.chip.core.ResultData;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

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
    public ResultData<FileDefinition> find( Long fileId);

    @Override
    @GetExchange(PREFIX+URL)
    public ResultData<Map<String,String>> url( Long tableId,  String fileFieldName,  String tableName);

    @Override
    @GetExchange(PREFIX+URL_SINGLE)
    public ResultData<String> urlSingle( Long tableId, String fileFieldName, String tableName);

    @Override
    @PostExchange(PREFIX+SAVE)
    public ResultData<List<Long>> save( Long tableId,  String tableName,  String fileFieldName, List<Long> fileIds);

}
