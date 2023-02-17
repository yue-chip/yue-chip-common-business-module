package com.yue.chip.upms.domain.aggregates;

import com.yue.chip.upms.definition.aggregates.ResourcesVODefinition;
import com.yue.chip.upms.domain.repository.resources.ResourcesRepository;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Mr.Liu
 * @date 2023/1/12 下午2:17
 * @description 资源值对象
 */
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper=true)
@NoArgsConstructor
public class Resources extends ResourcesVODefinition {

}
