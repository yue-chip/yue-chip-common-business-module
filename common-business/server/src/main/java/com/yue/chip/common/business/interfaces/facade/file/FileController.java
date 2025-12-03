package com.yue.chip.common.business.interfaces.facade.file;

import com.yue.chip.common.business.assembler.file.FileMapper;
import com.yue.chip.common.business.domain.aggregates.file.File;
import com.yue.chip.common.business.domain.repository.file.FileRepository;
import com.yue.chip.common.business.domain.service.file.FileService;
import com.yue.chip.common.business.domain.service.file.LargeFileService;
import com.yue.chip.common.business.interfaces.vo.file.FileVo;
import com.yue.chip.core.IResultData;
import com.yue.chip.core.ResultData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.extern.java.Log;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

import java.util.*;

/**
 * @author Mr.Liu
 * @date 2023/6/13 下午4:49
 */
@RestController()
@RequestMapping("/file")
@Validated
@Tag(name = "文件")
@Log
public class FileController {

    @Resource
    private FileService fileService;
    @Resource
    private LargeFileService largeFileService;
    @Resource
    private FileRepository fileRepository;

    @Resource
    private FileMapper fileMapper;

    @PostMapping("/upload")
    @Operation(description = "上传文件(支持多文件)", summary = "上传文件(支持多文件)")
    public IResultData<List<FileVo>> upload(StandardMultipartHttpServletRequest multipartHttpServletRequest) throws Exception {
        ResultData.ResultDataBuilder<List<FileVo>> builder = ResultData.builder();
        Map<String, MultipartFile> files = multipartHttpServletRequest.getFileMap();
        List<FileVo> fileList = new ArrayList<FileVo>();
        for (String originalFileName : files.keySet()) {
            MultipartFile file = files.get(originalFileName);
            if (file.getSize() <= 0) {
                continue;
            }
            Optional<File> optional = fileService.upload(file);
            if (optional.isPresent()) {
                File fileAggregateRoot = optional.get();
                if (Objects.nonNull(fileAggregateRoot)) {
                    fileAggregateRoot = fileRepository.add(fileMapper.toFilePo(fileAggregateRoot));
                    if (Objects.nonNull(fileAggregateRoot.getId())) {
                        String url = fileAggregateRoot.getUrl();
                        if (!Objects.equals(url.substring(0, 1), "/")) {
                            url = "/" + url;
                        }
                        if (url.indexOf("/file") < 0) {
                            url = fileService.URL_PREFIX + url;
                        }
                        fileAggregateRoot.setUrl(url);
                        fileList.add(fileMapper.toFileVo(fileAggregateRoot));
                    }
                }
            }
        }
        return builder.data(fileList).build();
    }

    @PostMapping("/init/upload")
    @Operation(description = "初始化分片上传", summary = "初始化分片上传")
    public IResultData<Map<String, Object>> initUpload(@Parameter(description = "文件名称", required = true) @RequestParam("fileName")
                                                       @NotBlank(message = "文件名称不能为空") String fileName,
                                                       @Parameter(description = "文件大小", required = true) @RequestParam("fileSize")
                                                       @NotNull(message = "文件大小不能为空") Long fileSize) {
        Map<String, Object> stateMap = largeFileService.initUpload(fileName, fileSize);
        return ResultData.builder().data(stateMap).build();
    }

    @PostMapping("/upload/chunk")
    @Operation(description = "上传分片", summary = "上传分片")
    public IResultData<?> uploadChunk(@Parameter(description = "上传ID", required = true) @RequestParam("uploadId")
                                      @NotBlank(message = "上传ID不能为空") String uploadId,
                                      @Parameter(description = "分片编号", required = true) @RequestParam("chunkNumber")
                                      @NotNull(message = "分片编号不能为空") Integer chunkNumber,
                                      MultipartFile chunk) {
        largeFileService.uploadChunk(uploadId, chunkNumber, chunk);
        return ResultData.builder().build();
    }

    @PostMapping("/merge/file")
    @Operation(description = "合并文件", summary = "合并文件")
    public IResultData<FileVo> mergeFile(@Parameter(description = "上传ID", required = true) @RequestParam("uploadId")
                                         @NotBlank(message = "上传ID不能为空") String uploadId) {
        File file = largeFileService.mergeFile(uploadId);
        return ResultData.builder().data(file).build();
    }

    @GetMapping("/upload/status")
    @Operation(description = "查询上传状态", summary = "查询上传状态")
    public IResultData<Map<String, Object>> getUploadStatus(@Parameter(description = "上传ID", required = true) @RequestParam("uploadId")
                                                            @NotBlank(message = "上传ID不能为空") String uploadId) {
        Map<String, Object> uploadStatus = largeFileService.getUploadStatus(uploadId);
        return ResultData.builder().data(uploadStatus).build();
    }

}
