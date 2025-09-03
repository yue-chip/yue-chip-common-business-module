package com.yue.chip.common.business.expose.file;

import com.yue.chip.common.business.definition.file.FileDefinition;
import com.yue.chip.core.ResultData;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.web.bind.annotation.RequestParam;
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

    @GetExchange(PREFIX+FIND)
    public ResultData<FileDefinition> find(@NotNull(message = "文件id不能为空") @RequestParam(value = "fileId") Long fileId);

    @GetExchange(PREFIX+URL)
    public ResultData<Map<String,String>> url( @NotNull(message = "表id不能为空") @RequestParam(value = "tableId") Long tableId,
                                               @NotBlank(message = "表字段名不能为空") @RequestParam(value = "fileFieldName") String fileFieldName,
                                               @NotBlank(message = "表明") @RequestParam(value = "tableName") String tableName);

    @GetExchange(PREFIX+URL_SINGLE)
    public ResultData<String> urlSingle( @NotNull(message = "表id不能为空") @RequestParam(value = "tableId") Long tableId,
                                         @NotBlank(message = "表字段名不能为空") @RequestParam(value = "fileFieldName") String fileFieldName,
                                         @NotBlank(message = "tableName") @RequestParam(value = "tableName") String tableName);

    @PostExchange(PREFIX+SAVE)
    public ResultData<List<Long>> save( @NotNull(message = "表id不能为空") @RequestParam(value = "tableId")Long tableId,
                                        @NotBlank(message = "表名不能为空") @RequestParam(value = "tableName")String tableName,
                                        @NotBlank(message = "表字段名不能为空") @RequestParam(value = "fileFieldName")String fileFieldName,
                                        @NotNull(message = "文件id不能为空") @Size(min = 1,message = "文件id不能为空") @RequestParam(value = "fileIds")List<Long> fileIds);

}
