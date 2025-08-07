package com.yue.chip.common.business.expose.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.dubbo.remoting.http12.rest.Schema;

import java.io.Serial;
import java.io.Serializable;

/**
 * 文件DTO
 *
 * @author ZhangYuanLin
 */
@Data
@Schema
@NoArgsConstructor
public class FileDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @NotBlank(message = "文件名不能为空")
    @Schema(description = "文件名")
    private String fileName;

    @NotEmpty(message = "文件字节数组不能为空")
    @Schema(description = "文件字节数组")
    private byte[] byteArray;

}
