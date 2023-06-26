package com.yue.chip.common.business.interfaces.facade;

import com.yue.chip.common.business.domain.service.file.FileService;
import com.yue.chip.core.controller.BaseController;
import com.yue.chip.core.controller.impl.BaseControllerImpl;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.java.Log;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author Mr.Liu
 * @date 2023/6/13 下午4:49
 */
@RestController()
@RequestMapping("/file")
@Validated
@Tag(name = "文件")
@Log
public class FileController extends BaseControllerImpl implements BaseController {

    @Resource
    private FileService fileService;

    @PostMapping("/upload")
    @ApiOperation(value = "上传文件(支持多文件)",notes = "上传文件(支持多文件)")
    public IResultData<List<File>> upload(StandardMultipartHttpServletRequest multipartHttpServletRequest) throws Exception {
        Map<String, MultipartFile> files = multipartHttpServletRequest.getFileMap();
        List<File> fileList = new ArrayList<File>();
        for(String originalFileName : files.keySet()) {
            MultipartFile file = files.get(originalFileName);
            if (file.getSize() <= 0) {
                continue;
            }
            com.lion.common.entity.file.File entity = fileUploadService.upload(file);
            if (Objects.nonNull(entity)) {
                fileService.save(entity);
                if (Objects.nonNull(entity.getId())) {
                    String url = entity.getUrl();
                    if (!Objects.equals(url.substring(0,1),"/")) {
                        url = "/"+url;
                    }
                    if (url.indexOf("/file")<0){
                        url = FileUploadService.URL_PREFIX+url;
                    }
                    entity.setUrl(url);
                    fileList.add(entity);
                }
            }
        }
        return ResultData.instance().setData(fileList);
    }

}
