package com.yue.chip.common.business.interfaces.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.yue.chip.common.business.definition.file.FileDefinition;
import com.yue.chip.common.business.domain.aggregates.file.File;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author Mr.Liu
 * @date 2023/7/5 下午5:17
 */
@Data
@Schema(description = "文件")
@EqualsAndHashCode(callSuper=true)
@SuperBuilder
@NoArgsConstructor
public class FileVo extends FileDefinition {
}
