package com.yue.chip.common.business.domain.aggregates.file;

import com.yue.chip.common.business.definition.file.FileDefinition;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.stereotype.Component;

/**
 * @author Mr.Liu
 * @date 2023/7/5 下午4:54
 */
@Data
@EqualsAndHashCode(callSuper=true)
@SuperBuilder
@NoArgsConstructor
@Component
public class File extends FileDefinition {

}
