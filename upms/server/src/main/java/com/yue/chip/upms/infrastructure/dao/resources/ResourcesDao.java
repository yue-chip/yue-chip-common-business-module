package com.yue.chip.upms.infrastructure.dao.resources;

import com.yue.chip.core.persistence.curd.BaseDao;
import com.yue.chip.upms.enums.Scope;
import com.yue.chip.upms.infrastructure.po.resources.ResourcesPo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @author Mr.Liu
 * @date 2023/1/14 上午10:33
 * @description ResourcesDao
 */
public interface ResourcesDao extends BaseDao<ResourcesPo>, ResourcesDaoEx {

    public List<ResourcesPo> findByParentIdAndScopeOrderBySortAsc(@NotNull Long parentId, @NotNull Scope scope);

    public Optional<ResourcesPo> findFirstByCode(@NotBlank String code);

    public Optional<ResourcesPo> findFirstByName(@NotBlank String name);

    public Optional<ResourcesPo> findFirstByNameAndParentId(@NotBlank String name,@NotNull Long parentId);

    public List<ResourcesPo> findByParentId(@NotNull Long parentId);

    public Optional<ResourcesPo> findFirstByUrl(@NotBlank String url);

    @Modifying
    @Transactional
    @Query("UPDATE ResourcesPo e SET e.nameEncrypt = :nameEncrypt, e.nameHmac = :nameHmac " +
            " WHERE e.id = :id")
    void updateEncrypt(@Param("nameEncrypt") String nameEncrypt, @Param("nameHmac") String nameHmac,
                       @Param("id") Long id);

}
