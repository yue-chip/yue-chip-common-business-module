//package com.xiao.wei.security.infrastructure.repository.enmus.impl;
//
//import com.xiao.wei.security.assembler.enums.EnumUtilMapper;
//import com.xiao.wei.security.domain.aggregates.enums.EnumUtil;
//import com.xiao.wei.security.domain.repository.enums.EnumUtilRepository;
//import com.xiao.wei.security.infrastructure.dao.enums.EnumUtilDao;
//import com.xiao.wei.security.infrastructure.po.enmus.EnumUtilPo;
//import jakarta.annotation.Resource;
//import org.springframework.stereotype.Repository;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.util.StringUtils;
//
//import java.util.Optional;
//
///**
// * @author Mr.Liu
// * @date 2023/7/6 上午11:26
// */
//@Repository
//public class EnumUtilRepositoryImpl implements EnumUtilRepository {
//
//    @Resource
//    private EnumUtilDao enumUtilDao;
//
//    @Resource
//    private EnumUtilMapper enumUtilMapper;
//
//
//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public EnumUtil save(EnumUtilPo enumUtilPo) {
//        if (StringUtils.hasText(enumUtilPo.getCode()) && StringUtils.hasText(enumUtilPo.getVersion())) {
//            enumUtilDao.deleteByCodeAndVersion(enumUtilPo.getCode(),enumUtilPo.getVersion());
//        }else if (StringUtils.hasText(enumUtilPo.getCode()) && !StringUtils.hasText(enumUtilPo.getVersion())) {
//            enumUtilDao.deleteByCode(enumUtilPo.getCode());
//        }
//        enumUtilPo = enumUtilDao.save(enumUtilPo);
//        return enumUtilMapper.toEnumUtil(enumUtilPo);
//    }
//
//
//    @Override
//    public Optional<EnumUtil> find(String code, String version) {
//        Optional<EnumUtilPo> optional = enumUtilDao.findFirstByCodeAndVersion(code,version);
//        if (optional.isPresent()) {
//            return Optional.ofNullable(enumUtilMapper.toEnumUtil(optional.get()));
//        }
//        return Optional.empty();
//    }
//
//    @Override
//    public Optional<EnumUtil> find(String code) {
//        Optional<EnumUtilPo> optional = enumUtilDao.findFirstByCode(code);
//        if (optional.isPresent()) {
//            return Optional.ofNullable(enumUtilMapper.toEnumUtil(optional.get()));
//        }
//        return Optional.empty();
//    }
//}
