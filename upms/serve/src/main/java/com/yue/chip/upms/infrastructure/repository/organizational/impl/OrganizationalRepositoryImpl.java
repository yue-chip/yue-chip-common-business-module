package com.yue.chip.upms.infrastructure.repository.organizational.impl;

import com.yue.chip.core.common.enums.State;
import com.yue.chip.upms.assembler.organizational.OrganizationalMapper;
import com.yue.chip.upms.domain.aggregates.Organizational;
import com.yue.chip.upms.domain.aggregates.User;
import com.yue.chip.upms.domain.repository.organizational.OrganizationalRepository;
import com.yue.chip.upms.domain.repository.upms.UpmsRepository;
import com.yue.chip.upms.infrastructure.dao.organizational.OrganizationalDao;
import com.yue.chip.upms.infrastructure.dao.organizational.OrganizationalUserDao;
import com.yue.chip.upms.infrastructure.po.organizational.OrganizationalPo;
import com.yue.chip.upms.infrastructure.po.organizational.OrganizationalUserPo;
import com.yue.chip.upms.interfaces.vo.organizational.OrganizationalTreeListVo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author Mr.Liu
 * @description: TODO
 * @date 2023/10/7 下午6:20
 */
@Component
public class OrganizationalRepositoryImpl implements OrganizationalRepository {

    @Resource
    private OrganizationalDao organizationalDao;

    @Resource
    private OrganizationalUserDao organizationalUserDao;

    @Resource
    private OrganizationalMapper organizationalMapper;

    @Resource
    private UpmsRepository upmsRepository;


    @Override
    public Optional<Organizational> findByUserId(Long userId) {
        Optional<OrganizationalPo> optional = organizationalDao.findByUserId(userId);
        if (optional.isPresent()) {
            return Optional.ofNullable(organizationalMapper.toOrganizational(optional.get()));
        }
        return Optional.empty();
    }

    @Override
    public Optional<Organizational> findById(Long id) {
        Optional<OrganizationalPo> optional = organizationalDao.findById(id);
        if (optional.isPresent()) {
            return Optional.ofNullable(organizationalMapper.toOrganizational(optional.get()));
        }
        return Optional.empty();
    }

    @Override
    public void deleteOrganizationalByUserId(Long userId) {
        organizationalUserDao.deleteAllByUserId(userId);
    }

    @Override
    public void deleteOrganizationalUserByOrganizationalId(Long organizationalId) {
        organizationalUserDao.deleteAllByOrganizationalId(organizationalId);
    }

    @Override
    public void saveOrganizationalUser(OrganizationalUserPo organizationalUserPo) {
        organizationalUserDao.save(organizationalUserPo);
    }

    @Override
    public void saveOrganizational(OrganizationalPo organizational) {
        organizationalDao.save(organizational);
    }

    @Override
    public void deleteOrganizationalById(Long id) {
        organizationalDao.deleteById(id);
    }

    @Override
    public void updateOrganizational(OrganizationalPo organizational) {
        organizationalDao.update(organizational);
    }

    @Override
    public Optional<Organizational> findByParentIdAndName(Long parentId, String name) {
        Optional<OrganizationalPo> optional = organizationalDao.findFirstByParentIdAndName(parentId,name);
        if (optional.isPresent()) {
            return Optional.ofNullable(organizationalMapper.toOrganizational(optional.get()));
        }
        return Optional.empty();
    }

    @Override
    public List<OrganizationalTreeListVo> findTree(Long parentId,State state) {
        List<OrganizationalTreeListVo> treeListVos = new ArrayList<OrganizationalTreeListVo>();
        List<OrganizationalPo> list = new ArrayList<>();
        if (Objects.nonNull(state)) {
            list = organizationalDao.findAllByParentIdAndStateOrderBySortAsc(parentId,state);
        }else {
            list = organizationalDao.findAllByParentIdOrderBySortAsc(parentId);
        }
        treeListVos = organizationalMapper.toOrganizationalTreeListVo(list);
        treeListVos.forEach(organizationalTreeListVo -> {
            organizationalTreeListVo.setChildren(findTree(organizationalTreeListVo.getId(),state));
            if (Objects.nonNull(organizationalTreeListVo.getLeaderId())) {
                Optional<User> optional = upmsRepository.findUserById(organizationalTreeListVo.getLeaderId());
                if (optional.isPresent()) {
                    organizationalTreeListVo.setLeaderName(optional.get().getName());
                }
            }
        });
        return treeListVos;
    }

    @Override
    public List<Organizational> findAllChildren(Long parentId) {
        List<Organizational> returnList = new ArrayList<>();
        List<OrganizationalPo> list = organizationalDao.findAllByParentId(parentId);
        list.forEach(organizationalPo -> {
            returnList.add(organizationalMapper.toOrganizational(organizationalPo));
            findAllChildren(organizationalPo.getId(),returnList);
        });
        return returnList;
    }

    @Override
    public void deleteLeader(Long userId) {
        organizationalDao.deleteLeader(userId);
    }

    private void findAllChildren(Long parentId,List<Organizational> organizationals) {
        List<OrganizationalPo> list = organizationalDao.findAllByParentId(parentId);
        list.forEach(organizationalPo -> {
            organizationals.add(organizationalMapper.toOrganizational(organizationalPo));
            findAllChildren(organizationalPo.getId(),organizationals);
        });
    }
}
