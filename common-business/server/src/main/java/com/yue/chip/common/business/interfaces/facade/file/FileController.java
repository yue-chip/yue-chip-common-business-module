package com.yue.chip.common.business.interfaces.facade.file;

import com.yue.chip.annotation.AuthorizationIgnore;
import com.yue.chip.common.business.assembler.file.FileMapper;
import com.yue.chip.common.business.domain.aggregates.file.File;
import com.yue.chip.common.business.domain.repository.file.FileRepository;
import com.yue.chip.common.business.domain.service.file.FileService;
import com.yue.chip.common.business.interfaces.vo.file.FileVo;
import com.yue.chip.core.IResultData;
import com.yue.chip.core.ResultData;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.java.Log;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author Mr.Liu
 * @date 2023/6/13 下午4:49
 */
@RestController()
@RequestMapping("/file")
@Validated
//@Tag(name = "文件")
@Log
public class FileController  {

    @Resource
    private FileService fileService;
    @Resource
    private FileRepository fileRepository;

    @Resource
    private FileMapper fileMapper;

    @PostMapping("/upload")
    @Operation(description = "上传文件(支持多文件)",summary = "上传文件(支持多文件)")
    @AuthorizationIgnore
    public IResultData<List<FileVo>> upload(StandardMultipartHttpServletRequest multipartHttpServletRequest) throws Exception {
        Map<String, MultipartFile> files = multipartHttpServletRequest.getFileMap();
        List<FileVo> fileList = new ArrayList<FileVo>();
        for(String originalFileName : files.keySet()) {
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
        return ResultData.builder().data(fileList).build();
    }

}
