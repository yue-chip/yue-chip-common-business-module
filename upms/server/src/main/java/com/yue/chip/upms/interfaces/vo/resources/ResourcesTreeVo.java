package com.yue.chip.upms.interfaces.vo.resources;

import com.yue.chip.utils.Sm4Api;
import lombok.Builder;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author Mr.Liu
 * @date 2023/3/3 下午4:38
 */
@Data
@Builder
public class ResourcesTreeVo {

    private String title;

    private Long key;

    private List<ResourcesTreeVo> children;

    public String getTitle() {
        if (StringUtils.hasText(this.title)) {
            return new Sm4Api().generalDataDec( this.title);
        }
        return this.title;
    }
}
