package com.yue.chip.upms.application.expose.impl.upms;

import com.yue.chip.core.IPageResultData;
import com.yue.chip.core.PageSerializable;
import com.yue.chip.core.YueChipPage;
import com.yue.chip.core.YueChipPageSerializable;
import com.yue.chip.grid.vo.GridExposeVo;
import com.yue.chip.grid.vo.GridTreeVo;
import com.yue.chip.grid.vo.GridVo;
import com.yue.chip.upms.UpmsExposeService;
import com.yue.chip.upms.assembler.organizational.GridMapper;
import com.yue.chip.upms.assembler.organizational.OrganizationalMapper;
import com.yue.chip.upms.assembler.organizational.OrganizationalUserMapper;
import com.yue.chip.upms.assembler.user.UserMapper;
import com.yue.chip.upms.definition.organizational.GridUserDefinition;
import com.yue.chip.upms.domain.aggregates.Grid;
import com.yue.chip.upms.domain.aggregates.Organizational;
import com.yue.chip.upms.domain.aggregates.User;
import com.yue.chip.upms.domain.repository.organizational.OrganizationalRepository;
import com.yue.chip.upms.domain.repository.upms.UpmsRepository;
import com.yue.chip.upms.infrastructure.po.organizational.GridUserPo;
import com.yue.chip.upms.infrastructure.dao.organizational.GridUserDao;
import com.yue.chip.upms.infrastructure.dao.weixin.UserWeiXinDao;
import com.yue.chip.upms.infrastructure.po.organizational.OrganizationalPo;
import com.yue.chip.upms.infrastructure.po.organizational.OrganizationalUserPo;
import com.yue.chip.upms.infrastructure.po.user.UserWeiXinPo;
import com.yue.chip.upms.vo.OrganizationalExposeVo;
import com.yue.chip.upms.vo.OrganizationalUserExposeVo;
import com.yue.chip.upms.vo.UserExposeVo;
import com.yue.chip.upms.interfaces.vo.organizational.GridVo2;
import com.yue.chip.upms.vo.*;
import com.yue.chip.utils.CurrentUserUtil;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.data.domain.Page;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author xianming.chen
 * @description: TODO
 * @date 2023-10-28
 */
@DubboService(interfaceClass = UpmsExposeService.class)
public class UpmsExposeServiceImpl implements UpmsExposeService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private UpmsRepository upmsRepository;

    @Resource
    private OrganizationalRepository organizationalRepository;

    @Resource
    private OrganizationalMapper organizationalMapper;

    @Resource
    private GridMapper gridMapper;
    @Resource
    private GridUserDao gridUserDao;
    @Resource
    private OrganizationalUserMapper organizationalUserMapper;
    @Resource
    private UserWeiXinDao userWeiXinDao;

    @Override
    public List<UserExposeVo> findUserAllByIdIn(List<Long> userIds) {
        return userMapper.toUserExposeVo(upmsRepository.findUserByIds(userIds)) ;
    }

    @Override
    public com.yue.chip.core.Optional<UserExposeVo> findUserById(Long userId) {
        Optional<User> optional = upmsRepository.findUserById(userId);
        if (optional.isPresent()) {
            return com.yue.chip.core.Optional.ofNullable(userMapper.toUserExposeVo(optional.get()));
        }
        return com.yue.chip.core.Optional.empty();
    }

    @Override
    public List<UserExposeVo> findUserAllByOrganizationalId(List<Long> organizationalIds) {
        return userMapper.toUserExposeVo(upmsRepository.findUserByOrganizationalId(organizationalIds));
    }

    @Override
    public PageSerializable<UserExposeVo> findUserAllByOrganizationalId(List<Long> organizationalIds, String name, YueChipPage yueChipPage) {
        IPageResultData<List<UserExposeVo>> page = organizationalRepository.organizationalPoList(organizationalIds, name, yueChipPage);
        return new YueChipPageSerializable<>(page.getContent(),page.getPageable(),page.getTotalElements());
    }

    @Override
    public com.yue.chip.core.Optional<UserExposeVo> findByIdAndTenantNumber(Long id, Long tenantNumber) {
        Optional<User> optional = upmsRepository.findByIdAndTenantNumber(id,tenantNumber);
        if (optional.isPresent()) {
            return com.yue.chip.core.Optional.ofNullable(userMapper.toUserExposeVo(optional.get()));
        }
        return com.yue.chip.core.Optional.empty();
    }

    @Override
    public com.yue.chip.core.Optional<UserExposeVo> findByGridIdAndTenantNumber(Long id, Long tenantNumber) {
        Optional<User> optional = upmsRepository.findByGridIdAndTenantNumber(id,tenantNumber);
        if (optional.isPresent()) {
            return com.yue.chip.core.Optional.ofNullable(userMapper.toUserExposeVo(optional.get()));
        }
        return com.yue.chip.core.Optional.empty();
    }

    @Override
    public com.yue.chip.core.Optional<OrganizationalExposeVo> findOrganizationalById(Long id) {
        java.util.Optional<Organizational> optional = organizationalRepository.findById(id);
        if (optional.isPresent()) {
            return com.yue.chip.core.Optional.ofNullable(organizationalMapper.toOrganizationalExposVo(optional.get()));
        }
        return com.yue.chip.core.Optional.empty();
    }

    @Override
    public List<OrganizationalExposeVo> findOrganizationalByIdList(Set<Long> ids) {
        List<OrganizationalPo> byIdList = organizationalRepository.findByIdList(ids);
        List<OrganizationalExposeVo> list = new ArrayList<>();
        if (!CollectionUtils.isEmpty(byIdList)) {
            byIdList.forEach(po -> {
                OrganizationalExposeVo organizationalExposeVo = organizationalMapper.toOrganizationalExposeVo(po);
                list.add(organizationalExposeVo);
            });
        }
        return list;
    }

    @Override
    public List<OrganizationalExposeVo> findOrganizationalAll() {
        List<OrganizationalPo> organizationalPoList = organizationalRepository.findAll();
        List<OrganizationalExposeVo> list = new ArrayList<>();
        if (!CollectionUtils.isEmpty(organizationalPoList)) {
            organizationalPoList.forEach(po -> {
                OrganizationalExposeVo organizationalExposeVo = organizationalMapper.toOrganizationalExposeVo(po);
                list.add(organizationalExposeVo);
            });
        }
        return list;
    }

    @Override
    public List<OrganizationalExposeVo> findOrganizationalAllChildrenByOrganizationalId(Long organizationalId) {
        List<Organizational> list = organizationalRepository.findAllChildren(organizationalId);
        List<Organizational> firstList = new ArrayList<>();
        java.util.Optional<Organizational> optional = organizationalRepository.findById(organizationalId);
        if (optional.isPresent()) {
            firstList.add(optional.get());
        }
        firstList.addAll(list);
        return organizationalMapper.toOrganizationalExposeVo(firstList);
    }

    @Override
    public List<OrganizationalExposeVo> findOrganizationalAllChildrenByUserId(Long userId) {
        if (Objects.isNull(userId)) {
            return Collections.EMPTY_LIST;
        }
        List<Organizational> organizationalList = organizationalRepository.findByUserId(userId);
        if (!organizationalList.isEmpty()) {
            List<OrganizationalExposeVo> result = new ArrayList<>();
            organizationalList.forEach(po -> {
                result.addAll(findOrganizationalAllChildrenByOrganizationalId(po.getId()));
            });
            return result;
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public List<OrganizationalExposeVo> findOrganizationalAllChildrenByCurrentUserId() {
        return findOrganizationalAllChildrenByUserId(CurrentUserUtil.getCurrentUserId(true));
    }

    @Override
    public Set<Long> findOrganizationalAllChildrenOrganizationalIds(Long parentId) {
        Set<Long> childrenIds = new HashSet<>();
        List<Organizational> allChildren = organizationalRepository.findAllChildren(parentId);
        if (!CollectionUtils.isEmpty(allChildren)) {
            Set<Long> ids = allChildren.stream().map(Organizational::getId).collect(Collectors.toSet());
            childrenIds.addAll(ids);
        }
        return childrenIds;
    }
    @Override
    public List<UserExposeVo> findUserAllByNameOrPhoneNumber(@NotBlank String name, @NotBlank String phoneNumber) {
        List<User> list = upmsRepository.findAllByNameOrPhoneNumber(name,phoneNumber);
        return userMapper.toUserExposeVo(list);
    }

    @Override
    public List<OrganizationalExposeVo> findOrganizationalChildrenOrganizationalIds(Long parentId) {
        List<OrganizationalExposeVo> list = new ArrayList<>();
        List<OrganizationalPo> children = organizationalRepository.findChildren(parentId);
        if (!CollectionUtils.isEmpty(children)) {
            children.forEach(po -> {
                OrganizationalExposeVo organizationalExposeVo = organizationalMapper.toOrganizationalExposeVo(po);
                list.add(organizationalExposeVo);
            });
        }
        return list;
    }

    @Override
    public PageSerializable<OrganizationalExposeVo> organizationalExposeVoPage(List<Long> organizationalList, YueChipPage yueChipPage) {
        Page<OrganizationalPo> page = organizationalRepository.organizationalPoPage(organizationalList, yueChipPage);
        List<OrganizationalExposeVo> organizationalExposeVoList = organizationalMapper.toOrganizationalExposeVoList(page.getContent());
        return new YueChipPageSerializable<OrganizationalExposeVo>(organizationalExposeVoList, page.getPageable(),page.getTotalElements());
    }

    @Override
    public List<GridExposeVo> findByOrganizationalId(Long organizationalId) {
        List<Grid> list = organizationalRepository.listGrid(organizationalId);
        return gridMapper.toGridExposeVo(list);
    }

    @Override
    public List<GridTreeVo> findTreeByOrganizationalId(Long organizationalId) {
        List<GridTreeVo> gridTreeVos = organizationalRepository.listGridTree2(organizationalId);
        return gridTreeVos;
    }

    @Override
    public List<GridExposeVo> findByGridId(Set<Long> gridId) {
        List<Grid> list = organizationalRepository.findByGridId(gridId);
        return gridMapper.toGridExposeVo(list);
    }

    @Override
    public List<OrganizationalUserExposeVo> findUserAllByOrganizationalIdAndUserIdIn(Long organizationalId, Set<Long> userId) {
        List<OrganizationalUserPo> list = organizationalRepository.findUserAllByOrganizationalIdAndUserIdIn(organizationalId, userId);
        return organizationalUserMapper.toListOrganizationalUserExposeVo(list);
    }

    @Override
    public List<OrganizationalUserExposeVo> findUserAllByUserIdIn(Set<Long> userId) {
        List<OrganizationalUserPo> list = organizationalRepository.findUserAllByUserIdIn(userId);
        return organizationalUserMapper.toListOrganizationalUserExposeVo(list);
    }

    @Override
    public UserGridVo bindUserOrganizationalGird(List<UserOrganizationalGirdVo> voList) {
        UserGridVo map = organizationalRepository.bindUserOrganizationalGird(voList);
        return map;
    }

    @Override
    public List<Long> findAllByGridId(Long gridId) {
        List<Long> userIdList = new ArrayList<>();
        List<GridUserPo> gridUserPoList = gridUserDao.findAllByGridId(gridId);
        if (!CollectionUtils.isEmpty(gridUserPoList)) {
            List<Long> userIds = gridUserPoList.stream().map(GridUserPo::getUserId).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(userIds)) {
                userIdList.addAll(userIds);
            }
        }
        return userIdList;
    }

    @Override
    public List<String> findAllByWeiXinOpenIdByPhone(String phone) {
        List<UserWeiXinPo> allByPhoneNumber = userWeiXinDao.findAllByPhoneNumber(phone);
        return allByPhoneNumber.stream().map(UserWeiXinPo::getOpenId).collect(Collectors.toList());
    }

}
