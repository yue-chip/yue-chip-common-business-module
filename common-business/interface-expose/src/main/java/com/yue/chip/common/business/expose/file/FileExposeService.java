package com.yue.chip.common.business.expose.file;

import com.yue.chip.common.business.definition.file.FileDefinition;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author Mr.Liu
 * @date 2023/7/6 下午3:32
 */
public interface FileExposeService {

    /**
     * 根据file id 获取文件
     * @param fileId
     * @return
     */
    public Optional<FileDefinition> find(@NotNull Long fileId);

    /**
     * 根据file id 获取文件URL
     * @param fileId
     * @return
     */
    public String getUrl(@NotNull Long fileId);

    /**
     * 根据表id和表名称查找多个文件url
     *
     * @param tableId       关联表的id
     * @param fileFieldName 关联表的字段名称(如:头像,照片,合同……) 被关联表中实际不存在该字段
     * @param tableName     关联表的id
     * @param tenantNumber
     * @return fileId 和 url 的映射关系
     */
//    @Cacheable(value = FileDefinition.CACHE_KEY_URL_MULTIPLE,key = "#p0 + '-' + #p1 + '-' +#p2 + '-' +#p3")
    @Deprecated
    public Map<String,String> getUrl(@NotNull Long tableId, @NotBlank String fileFieldName, @NotBlank String tableName,Long tenantNumber);

    public Map<String,String> getUrl(@NotNull Long tableId, @NotBlank String fileFieldName, @NotBlank String tableName);

    /**
     * 根据表id和表名称查找单个文件url
     *
     * @param tableId       关联表的id
     * @param fileFieldName 关联表的字段名称(如:头像,照片,合同……) 被关联表中实际不存在该字段
     * @param tableName     关联表的id
     * @param tenantNumber
     * @return fileId 和 url 的映射关系
     */
//    @Cacheable(value = FileDefinition.CACHE_KEY_URL_SINGLE,key = "#p0 + '-' + #p1 + '-' +#p2 + '-' +#p3")
    @Deprecated
    public String getUrlSingle(@NotNull Long tableId,@NotBlank String fileFieldName,@NotBlank String tableName,Long tenantNumber);

    public String getUrlSingle(@NotNull Long tableId,@NotBlank String fileFieldName,@NotBlank String tableName);

    /**
     * 保存表与文件的关联关系
     *
     * @param tableId       关联表的id
     * @param tableName     关联表的id
     * @param fileFieldName 关联表的字段名称(如:头像,照片,合同……) 被关联表中实际不存在该字段
     * @param fileIds       文件id
     * @param tenantNumber
     * @return
     */
//    @CacheEvict(value = {FileDefinition.CACHE_KEY_URL_SINGLE,FileDefinition.CACHE_KEY_URL_MULTIPLE}, key = "#p0 + '-' + #p1 + '-' +#p2 + '-' +#p3")
    @Deprecated
    public List<Long> save(@NotNull Long tableId, @NotBlank String tableName, @NotBlank String fileFieldName,@NotNull @Size(min = 1) List<Long> fileIds,Long tenantNumber);

    /**
     * 保存表与文件的关联关系
     * @param tableId
     * @param tableName
     * @param fileFieldName
     * @param fileIds
     * @return
     */
    public List<Long> save(@NotNull Long tableId, @NotBlank String tableName, @NotBlank String fileFieldName,@NotNull @Size(min = 1) List<Long> fileIds);


    /**
     * 保存表与文件的关联关系
     *
     * @param tableId       关联表的id
     * @param tableName     关联表的id
     * @param fileFieldName 关联表的字段名称(如:头像,照片,合同……) 被关联表中实际不存在该字段
     * @param fileId        文件id
     * @param tenantNumber
     * @return
     */
//    @CacheEvict(value = {FileDefinition.CACHE_KEY_URL_SINGLE,FileDefinition.CACHE_KEY_URL_MULTIPLE}, key = "#p0 + '-' + #p1 + '-' +#p2 + '-' +#p3")
    @Deprecated
    public List<Long> save(@NotNull Long tableId, @NotBlank String tableName, @NotBlank String fileFieldName,@NotNull Long fileId,Long tenantNumber);

    /**
     * 保存表与文件的关联关系
     * @param tableId
     * @param tableName
     * @param fileFieldName
     * @param fileId
     * @return
     */
    public List<Long> save(@NotNull Long tableId, @NotBlank String tableName, @NotBlank String fileFieldName,@NotNull Long fileId);


//    /**
//     * 保存表与文件的关联关系
//     *
//     * @param tableId       关联表的id
//     * @param tableName     关联表的id
//     * @param fileFieldName 关联表的字段名称(如:头像,照片,合同……) 被关联表中实际不存在该字段
//     * @param fileId        文件id
//     * @return
//     */
//    @Deprecated //Tri协议下会有问题  似乎是没法反序列化Long...
//    public List<Long> save(@NotNull Long tableId, @NotBlank String tableName, @NotBlank String fileFieldName,@NotNull @Size(min = 1) Long... fileId);

    public String testSendSms();
}
