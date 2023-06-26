package com.yue.chip.common.business.definition.file;

import com.yue.chip.core.BaseDefinition;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author Mr.Liu
 * @date 2023/6/13 下午4:15
 */
@Data
@Schema(description = "文件")
@EqualsAndHashCode(callSuper=true)
@SuperBuilder
@NoArgsConstructor
public class FileDefinition extends BaseDefinition {

    private static final long serialVersionUID = -2124525197256620973L;

    @Schema(description = "原始文件名")
    protected String originalFileName;

    @Schema(description = "文件名")
    protected String fileName;

    @Schema(description = "文件大小")
    protected Long size;

    @Schema(description = "文件下载路径")
    protected String url;


}
