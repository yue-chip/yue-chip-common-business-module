package com.yue.chip.common.business.domain.service.file;

import com.yue.chip.common.business.domain.aggregates.file.File;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * 大文件上传服务
 *
 * @author ZhangYuanLin
 */
public interface LargeFileService {

    /**
     * 初始化上传
     *
     * @param fileName 文件名
     * @param fileSize 文件大小
     * @return 初始化上传信息
     */
    Map<String, Object> initUpload(String fileName, long fileSize);

    /**
     * 上传分块
     *
     * @param uploadId    上传ID
     * @param chunkNumber 分块编号
     * @param chunk       分块文件
     */
    void uploadChunk(String uploadId, int chunkNumber, MultipartFile chunk);

    /**
     * 获取上传状态
     *
     * @param uploadId 上传ID
     * @return 上传状态信息
     */
    Map<String, Object> getUploadStatus(String uploadId);

    /**
     * 合并文件
     *
     * @param uploadId 上传ID
     * @return 文件信息
     */
    File mergeFile(String uploadId);
}
