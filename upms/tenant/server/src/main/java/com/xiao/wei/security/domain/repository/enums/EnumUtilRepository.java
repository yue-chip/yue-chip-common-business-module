//package com.xiao.wei.security.domain.repository.enums;
//
//import com.xiao.wei.security.domain.aggregates.enums.EnumUtil;
//import com.xiao.wei.security.infrastructure.po.enmus.EnumUtilPo;
//import jakarta.validation.constraints.NotBlank;
//import jakarta.validation.constraints.NotNull;
//
//import java.util.Optional;
//
///**
// * @author Mr.Liu
// * @date 2023/7/6 上午11:25
// */
//public interface EnumUtilRepository {
//
//    /**
//     * 保存枚举
//     * @param enumUtilPo
//     * @return
//     */
//    public EnumUtil save(@NotNull EnumUtilPo enumUtilPo);
//
//    /**
//     * 根据编码和版本查询枚举
//     *
//     * @param code
//     * @param version
//     * @return
//     */
//    public Optional<EnumUtil> find(@NotBlank String code, @NotBlank String version);
//
//    public Optional<EnumUtil> find(@NotBlank String code);
//}
