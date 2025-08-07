package com.yue.chip.common.business.expose.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.dubbo.remoting.http12.rest.Schema;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 文件添加DTO
 *
 * @author ZhangYuanLin
 */
@Data
@Schema
@NoArgsConstructor
public class FileAddDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @NotNull(message = "表ID不能为空")
    @Schema(description = "表ID")
    private Long tableId;

    @NotBlank(message = "表名不能为空")
    @Schema(description = "表名")
    private String tableName;

    @NotBlank(message = "文件字段名不能为空")
    @Schema(description = "文件字段名")
    private String fileFieldName;

    @Schema(description = "租户ID")
    private Long tenantNumber;

    @NotEmpty(message = "文件对象不能为空")
    @Schema(description = "文件对象")
    private List<FileDTO> fileList;

}
