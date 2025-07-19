package com.yue.chip.common.business.expose.file;

import com.yue.chip.common.business.definition.file.FileDefinition;
import com.yue.chip.core.ResultData;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
public interface RemoteFile {

    /**
     * 根据file id 获取文件
     * @param fileId
     * @return
     */
    @GetExchange("/api/common/file/remote/fileId")
    @GetMapping("/remote/fileId")
    @Operation(description = "根据文件id查找",summary = "根据文件id查找")
    public ResultData<FileDefinition> find(@NotNull Long fileId);

    /**
     * 根据表id和表名称查找多个文件url
     *
     * @param tableId       关联表的id
     * @param fileFieldName 关联表的字段名称(如:头像,照片,合同……) 被关联表中实际不存在该字段
     * @param tableName     关联表的id
     * @return fileId 和 url 的映射关系
     */
    @GetExchange("/api/common/file/remote/url")
    @GetMapping("/remote/url")
    @Operation(description = "查找文件",summary = "查找文件")
    public ResultData<Map<String,String>> url(@NotNull Long tableId, @NotBlank String fileFieldName, @NotBlank String tableName);

    /**
     * 根据表id和表名称查找单个文件url
     *
     * @param tableId       关联表的id
     * @param fileFieldName 关联表的字段名称(如:头像,照片,合同……) 被关联表中实际不存在该字段
     * @param tableName     关联表的id
     * @return fileId 和 url 的映射关系
     */
    @GetExchange("/api/common/file/remote/url/single")
    @GetMapping("/remote/url/single")
    @Operation(description = "查找文件",summary = "查找文件")
    public ResultData<String> urlSingle(@NotNull Long tableId,@NotBlank String fileFieldName,@NotBlank String tableName);

    /**
     * 保存表与文件的关联关系
     * @param tableId
     * @param tableName
     * @param fileFieldName
     * @param fileIds
     * @return
     */
    @PostExchange("/api/common/file/remote/save")
    @PostMapping("/remote/save")
    @Operation(description = "保存文件",summary = "保存文件")
    public ResultData<List<Long>> save(@NotNull Long tableId, @NotBlank String tableName, @NotBlank String fileFieldName,@NotNull @Size(min = 1) List<Long> fileIds);

}
