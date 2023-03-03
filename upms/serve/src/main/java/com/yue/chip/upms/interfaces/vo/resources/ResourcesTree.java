package com.yue.chip.upms.interfaces.vo.resources;

import lombok.Builder;
import lombok.Data;
import org.apache.commons.io.input.buffer.PeekableInputStream;

import java.util.List;

/**
 * @author Mr.Liu
 * @date 2023/3/3 下午4:38
 */
@Data
@Builder
public class ResourcesTree {

    private String title;

    private Long key;

    private List<ResourcesTree> children;
}
