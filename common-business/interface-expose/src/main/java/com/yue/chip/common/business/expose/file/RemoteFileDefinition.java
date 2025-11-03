package com.yue.chip.common.business.expose.file;

import com.yue.chip.annotation.AuthorizationIgnore;
import com.yue.chip.common.business.definition.file.FileDefinition;
import com.yue.chip.core.ResultData;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface RemoteFileDefinition {

    static final String PREFIX = "/common";

    static final String FIND = "/file/remote/fileId";
    static final String URL = "/file/remote/url";
    static final String URLS = "/file/remote/urls";
    static final String URL_SINGLE = "/file/remote/urlSingle";
    static final String SAVE = "/file/remote/save";

    /**
     * 根据file id 获取文件
     * @param fileId
     * @return
     */
    @Operation(description = "根据文件id查找",summary = "根据文件id查找")
    @AuthorizationIgnore
    public ResultData<FileDefinition> find( Long fileId);

    /**
     * 根据表id和表名称查找多个文件url
     *
     * @param tableId       关联表的id
     * @param fileFieldName 关联表的字段名称(如:头像,照片,合同……) 被关联表中实际不存在该字段
     * @param tableName     关联表的id
     * @return fileId 和 url 的映射关系
     */
    @Operation(description = "查找文件",summary = "查找文件")
    @AuthorizationIgnore
    public ResultData<Map<String,String>> url(@NotNull(message = "表id不能为空") @RequestParam(value = "tableId") Long tableId,
                                              @NotBlank(message = "表字段名不能为空") @RequestParam(value = "fileFieldName") String fileFieldName,
                                              @NotBlank(message = "表明") @RequestParam(value = "tableName") String tableName);

    @Operation(description = "查找文件",summary = "查找文件")
    @AuthorizationIgnore
    public ResultData<Map<String, String>> urls(@NotNull ArrayList<Long> tableIds, @NotBlank String fileFieldName, @NotBlank String tableName);

    /**
     * 根据表id和表名称查找单个文件url
     *
     * @param tableId       关联表的id
     * @param fileFieldName 关联表的字段名称(如:头像,照片,合同……) 被关联表中实际不存在该字段
     * @param tableName     关联表的id
     * @return fileId 和 url 的映射关系
     */
    @Operation(description = "查找文件",summary = "查找文件")
    @AuthorizationIgnore
    public ResultData<String> urlSingle(@NotNull(message = "表id不能为空") @RequestParam(value = "tableId") Long tableId,
                                        @NotBlank(message = "表字段名不能为空") @RequestParam(value = "fileFieldName") String fileFieldName,
                                        @NotBlank(message = "tableName") @RequestParam(value = "tableName") String tableName);

    /**
     * 保存表与文件的关联关系
     * @param tableId
     * @param tableName
     * @param fileFieldName
     * @param fileIds
     * @return
     */
    @Operation(description = "保存文件",summary = "保存文件")
    @AuthorizationIgnore
    public ResultData<List<Long>> save(@NotNull(message = "表id不能为空") @RequestParam(value = "tableId")Long tableId,
                                       @NotBlank(message = "表名不能为空") @RequestParam(value = "tableName")String tableName,
                                       @NotBlank(message = "表字段名不能为空") @RequestParam(value = "fileFieldName")String fileFieldName,
                                       @NotNull(message = "文件id不能为空") @Size(min = 1,message = "文件id不能为空") @RequestParam(value = "fileIds")List<Long> fileIds);
}
