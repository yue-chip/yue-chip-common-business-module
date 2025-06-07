//package com.xiao.wei.security.infrastructure.dao.enums;
//
//import com.xiao.wei.security.infrastructure.po.enmus.EnumUtilPo;
//import com.yue.chip.core.persistence.curd.BaseDao;
//import jakarta.validation.constraints.NotBlank;
//import org.springframework.transaction.annotation.Propagation;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.Optional;
//
///**
// * @author Mr.Liu
// * @date 2023/7/6 上午11:27
// */
//public interface EnumUtilDao extends BaseDao<EnumUtilPo>{
//
//    /**
//     * 根据编码和版本号删除
//     * @param code
//     * @param version
//     * @return
//     */
//    @Transactional(propagation= Propagation.REQUIRES_NEW,rollbackFor = Exception.class)
//    public int deleteByCodeAndVersion(@NotBlank String code,@NotBlank String version);
//
//    /**
//     * 根据编码删除
//     * @param code
//     * @return
//     */
//    @Transactional(propagation= Propagation.REQUIRES_NEW,rollbackFor = Exception.class)
//    public int deleteByCode(@NotBlank String code);
//
//    /**
//     * 根据编码和版本号查找枚举
//     * @param code
//     * @param version
//     * @return
//     */
//    public Optional<EnumUtilPo> findFirstByCodeAndVersion(@NotBlank String code,@NotBlank String version);
//
//    /**
//     * 根据编码和版本号查找枚举
//     * @param code
//     * @return
//     */
//    public Optional<EnumUtilPo> findFirstByCode(@NotBlank String code);
//
//}
