package com.yue.chip.upms.infrastructure.repository.organizational.impl;

import com.yue.chip.core.IPageResultData;
import com.yue.chip.core.PageResultData;
import com.yue.chip.core.YueChipPage;
import com.yue.chip.core.common.enums.State;
import com.yue.chip.exception.BusinessException;
import com.yue.chip.grid.vo.GridTreeVo;
import com.yue.chip.upms.application.service.UpmsApplication;
import com.yue.chip.upms.assembler.organizational.GridMapper;
import com.yue.chip.upms.assembler.organizational.OrganizationalMapper;
import com.yue.chip.upms.assembler.user.UserMapper;
import com.yue.chip.upms.domain.aggregates.Grid;
import com.yue.chip.upms.domain.aggregates.Organizational;
import com.yue.chip.upms.domain.aggregates.User;
import com.yue.chip.upms.domain.repository.organizational.OrganizationalRepository;
import com.yue.chip.upms.domain.repository.upms.UpmsRepository;
import com.yue.chip.upms.infrastructure.dao.organizational.GridDao;
import com.yue.chip.upms.infrastructure.dao.organizational.GridUserDao;
import com.yue.chip.upms.infrastructure.dao.organizational.OrganizationalDao;
import com.yue.chip.upms.infrastructure.dao.organizational.OrganizationalUserDao;
import com.yue.chip.upms.infrastructure.dao.user.UserDao;
import com.yue.chip.upms.infrastructure.po.organizational.GridPo;
import com.yue.chip.upms.infrastructure.po.organizational.GridUserPo;
import com.yue.chip.upms.infrastructure.po.organizational.OrganizationalPo;
import com.yue.chip.upms.infrastructure.po.organizational.OrganizationalUserPo;
import com.yue.chip.upms.infrastructure.po.user.UserPo;
import com.yue.chip.upms.interfaces.dto.user.UserAddOrUpdateDto;
import com.yue.chip.upms.interfaces.vo.organizational.GridVo;
import com.yue.chip.upms.interfaces.vo.organizational.GridVo2;
import com.yue.chip.upms.interfaces.vo.organizational.OrganizationalTreeListVo;
import com.yue.chip.upms.interfaces.vo.user.UserVo;
import com.yue.chip.upms.vo.UserExposeVo;
import com.yue.chip.upms.vo.UserOrganizationalGirdVo;
import com.yue.chip.utils.CurrentUserUtil;
import javax.annotation.Resource;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Mr.Liu
 * @description: TODO
 * @date 2023/10/7 下午6:20
 */
@Component
public class OrganizationalRepositoryImpl implements OrganizationalRepository {

    @Resource
    private OrganizationalDao organizationalDao;

    @javax.annotation.Resource
    private UserMapper userMapper;

    @Resource
    private OrganizationalUserDao organizationalUserDao;

    @Resource
    private OrganizationalMapper organizationalMapper;

    @Resource
    private GridMapper gridMapper;

    @Resource
    private UpmsRepository upmsRepository;

    @Resource
    private UpmsApplication upmsApplication;

    @Resource
    private GridDao gridDao;

    @Resource
    private GridUserDao gridUserDao;

    @Resource
    private UserDao userDao;



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
    public List<OrganizationalTreeListVo> findTree(Long parentId, State state, String name) {
        List<OrganizationalTreeListVo> treeListVos = new ArrayList<OrganizationalTreeListVo>();
        List<OrganizationalPo> list = new ArrayList<>();
        if (Objects.nonNull(state)) {
            list = organizationalDao.findAllByParentIdAndStateOrderBySortAsc(parentId,state);
        }else {
            list = organizationalDao.findAllByParentIdOrderBySortAsc(parentId);
        }
        treeListVos = organizationalMapper.toOrganizationalTreeListVo(list);
        treeListVos.forEach(organizationalTreeListVo -> {
            organizationalTreeListVo.setChildren(findTree(organizationalTreeListVo.getId(),state, name));
            if (Objects.nonNull(organizationalTreeListVo.getLeaderId())) {
                Optional<User> optional = upmsRepository.findUserById(organizationalTreeListVo.getLeaderId());
                if (optional.isPresent()) {
                    organizationalTreeListVo.setLeaderName(optional.get().getName());
                }
            }
        });
        return treeListVos.size()>0?treeListVos:null;
    }

    @Override
    public List<OrganizationalTreeListVo> findTree1(State state) {
        Optional<Organizational> optional = findByUserId(CurrentUserUtil.getCurrentUserId());
        if (optional.isPresent()) {
            List<OrganizationalTreeListVo> list = findTree(optional.get().getParentId(),state, null);
            List<OrganizationalTreeListVo> returList = new ArrayList<>();
            for (OrganizationalTreeListVo organizationalTreeListVo : list) {
                if (Objects.equals(organizationalTreeListVo.getId(),optional.get().getId())) {
                    returList.add(organizationalTreeListVo);
                }
            }
            return returList;
        }
        return Collections.EMPTY_LIST;
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

    @Override
    public List<OrganizationalPo> findByIdList(Set<Long> ids) {
        List<OrganizationalPo> allByIdIn = organizationalDao.findAllByIdIn(ids);
        return allByIdIn;
    }

    @Override
    public List<OrganizationalPo> findAll() {
        List<OrganizationalPo> organizationalPoList = organizationalDao.findAll();
        return organizationalPoList;
    }

    @Override
    public List<OrganizationalPo> findChildren(Long parentId) {
        List<OrganizationalPo> list = organizationalDao.findAllByParentId(parentId);
        return list;
    }

    @Override
    public Page<OrganizationalPo> organizationalPoPage(List<Long> organizationalList, YueChipPage yueChipPage) {
        Page<OrganizationalPo> organizationalPos = organizationalDao.organizationalPoPage(organizationalList, yueChipPage);
        return organizationalPos;
    }

    @Override
    public IPageResultData<List<UserExposeVo>> organizationalPoList(List<Long> organizationalIds, String name, YueChipPage yueChipPage) {
        List<OrganizationalUserPo> organizationalIdIn = organizationalUserDao.findAllByOrganizationalIdIn(organizationalIds);
        List<Long> userIdList = organizationalIdIn.stream().map(OrganizationalUserPo::getUserId).collect(Collectors.toList());
        IPageResultData<List<User>> page = upmsRepository.userList(userIdList, name, yueChipPage);

        List<UserExposeVo> userExposeVo = userMapper.toUserExposeVo(page.getData());
        Map<Long, Long> map = organizationalIdIn.stream().collect(Collectors.toMap(OrganizationalUserPo::getUserId, OrganizationalUserPo::getOrganizationalId));
        userExposeVo.forEach(user -> {
            user.setOrganizationalId(map.get(user.getId()));
        });

        return new PageResultData(userExposeVo,page.getPageable(),page.getTotalElements());
    }

    @Override
    public IPageResultData<List<UserExposeVo>> findByUserIdIn(Set<Long> userIds, String name, YueChipPage yueChipPage) {
        IPageResultData<List<User>> page = upmsRepository.userList(new ArrayList<>(userIds), name, yueChipPage);
        Map<Long, Long> userOrganizationalMap = findUserAllByUserIdIn(userIds).stream().collect(Collectors.toMap(OrganizationalUserPo::getUserId, OrganizationalUserPo::getOrganizationalId));
        List<UserExposeVo> userExposeVo = userMapper.toUserExposeVo(page.getData());
        userExposeVo.forEach(user -> {
            user.setOrganizationalId(userOrganizationalMap.get(user.getId()));
        });

        return new PageResultData(userExposeVo,page.getPageable(),page.getTotalElements());
    }


    @Override
    public void saveGrid(GridPo gridPo) {
        gridDao.save(gridPo);
    }

    @Override
    public void saveGrid2(GridPo gridPo, List<Long> userIds) {
        if (Objects.isNull(gridPo.getParentId())) {
            gridPo.setParentId(0L);
        }
        GridPo save = gridDao.save(gridPo);
        userIds.forEach(userId -> {
            GridUserPo gridUserPo = new GridUserPo();
            gridUserPo.setGridId(save.getId());
            gridUserPo.setUserId(userId);
            gridUserDao.save(gridUserPo);
        });

    }

    @Override
    public void updateGrid(GridPo gridPo, List<Long> userIds) {
        if (Objects.isNull(gridPo.getParentId())) {
            gridPo.setParentId(0L);
        }
        gridDao.update(gridPo);
        List<GridUserPo> gridUserPoList = gridUserDao.findAllByGridId(gridPo.getId());
        if (!CollectionUtils.isEmpty(gridUserPoList)) {
            List<Long> ids = gridUserPoList.stream().map(GridUserPo::getId).collect(Collectors.toList());
            ids.forEach(id -> {
                gridUserDao.deleteById(id);
            });
        }
        userIds.forEach(userId -> {
            GridUserPo gridUserPo = new GridUserPo();
            gridUserPo.setUserId(userId);
            gridUserPo.setGridId(gridPo.getId());
            gridUserDao.save(gridUserPo);
        });
    }

    @Override
    @Transactional
    public void deleteGrid(List<Long> ids) {
        if (Objects.nonNull(ids) && ids.size()>0) {
            if (ids.size() == 1) {
                Long id = ids.get(0);
                Optional<GridPo> gridPoOptional = gridDao.findById(id);
                if (gridPoOptional.get().getParentId() > 0) {
                    List<GridPo> gridPoList = gridDao.findAllByParentId(id);
                    if (!CollectionUtils.isEmpty(gridPoList)) {
                        throw new BusinessException("请先删除该网格下的所有的子网格再删除！");
                    }
                }
                if (gridPoOptional.get().getParentId() == 0) {
                    List<GridPo> gridPoList = gridDao.findAllByParentId(id);
                    if (!CollectionUtils.isEmpty(gridPoList)) {
                        throw new BusinessException("请先删除该网格下的所有的子网格再删除！");
                    }
                }
                gridDao.deleteById(id);
                List<Long> idList = gridUserDao.findAllByGridId(id).stream().map(GridUserPo::getId).collect(Collectors.toList());
                if (!CollectionUtils.isEmpty(idList)) {
                    idList.forEach(idd -> {
                        gridDao.deleteById(idd);
                    });
                }
            } else {
                ids.forEach(id->{
                    gridDao.deleteById(id);
                });
                List<Long> idList = gridUserDao.findAllByGridIdIn(ids).stream().map(GridUserPo::getId).collect(Collectors.toList());
                if (!CollectionUtils.isEmpty(idList)) {
                    gridUserDao.deleteByIds(idList);
                }
            }
        }
    }

    @Override
    public void deleteGridByUserId(Long userId) {
        gridDao.deleteAllByUserId(userId);
    }

    @Override
    public void deleteGridByOrganizationalId(Long organizationalId) {
        gridDao.deleteAllByOrganizationalId(organizationalId);
    }

    @Override
    public Optional<Grid> gridDetails(Long id) {
        Optional<GridPo> optional = gridDao.findById(id);
        if (optional.isPresent()) {
            return Optional.ofNullable(gridMapper.toGrid(optional.get()));
        }
        return Optional.empty();
    }

    @Override
    public IPageResultData<List<GridVo>> listGrid(Long organizationalId, String name, String userName, YueChipPage yueChipPage) {
        Page<GridPo> page = gridDao.List(organizationalId,name,userName,yueChipPage);
        List<Grid> list = gridMapper.toGrid(page.getContent());
        return new PageResultData<List<GridVo>>(gridMapper.toGridVo(list),page.getPageable(),page.getTotalElements());
    }

    @Override
    public List<GridVo2> listGridTree(Long organizationalId) {
        List<GridVo2> tree = new ArrayList<>();
        List<GridPo> gridPoList = gridDao.findAllByOrganizationalId(organizationalId);
        if (!CollectionUtils.isEmpty(gridPoList)) {
            List<GridVo2> gridVos = gridMapper.toListGridVo(gridPoList);
            gridVos.forEach(gridVo -> {
                List<Long> userIdList = new ArrayList<>();
                List<GridUserPo> gridUserPoList = gridUserDao.findAllByGridId(gridVo.getId());
                if (Objects.nonNull(gridVo.getUserId()) && CollectionUtils.isEmpty(gridUserPoList)) {
                    userIdList.add(gridVo.getUserId());
                }
                if (!CollectionUtils.isEmpty(gridUserPoList)) {
                    List<Long> userIds = gridUserPoList.stream().map(GridUserPo::getUserId).collect(Collectors.toList());
                    userIdList.addAll(userIds);
                }
                List<UserPo> userPoList = userDao.findAllByIdIn(userIdList);
                if (!CollectionUtils.isEmpty(userPoList)) {
                    List<UserVo> listUserVo = userMapper.toListUser(userPoList);
                    gridVo.setUser(listUserVo);
                }
            });
            tree = buildTree(gridVos);
        }
        return tree;

    }

    @Override
    public List<GridTreeVo> listGridTree2(Long organizationalId) {
        List<GridTreeVo> tree = new ArrayList<>();
        List<GridPo> gridPoList = gridDao.findAllByOrganizationalId(organizationalId);
        if (!CollectionUtils.isEmpty(gridPoList)) {
            List<GridTreeVo> gridVos = gridMapper.toListGridTreeVo(gridPoList);
            gridVos.forEach(gridVo -> {
                List<Long> userIdList = new ArrayList<>();
                if (Objects.nonNull(gridVo.getUserId())) {
                    userIdList.add(gridVo.getUserId());
                }
                List<GridUserPo> gridUserPoList = gridUserDao.findAllByGridId(gridVo.getId());
                if (!CollectionUtils.isEmpty(gridUserPoList)) {
                    List<Long> userIds = gridUserPoList.stream().map(GridUserPo::getUserId).collect(Collectors.toList());
                    userIdList.addAll(userIds);
                }
                List<UserPo> userPoList = userDao.findAllByIdIn(userIdList);
//                if (!CollectionUtils.isEmpty(userPoList)) {
//                    List<UserVo> listUserVo = userMapper.toListUser(userPoList);
//                    gridVo.setUser(listUserVo);
//                }
            });
            tree = buildTree2(gridVos);
        }
        return tree;
    }

    private List<GridVo2> buildTree(List<GridVo2> gridVos) {
        Map<Long, List<GridVo2>> map = new HashMap<>();
        for (GridVo2 gridVo : gridVos) {
            if (!map.containsKey(gridVo.getParentId())) {
                map.put(gridVo.getParentId(), new ArrayList<>());
            }
            map.get(gridVo.getParentId()).add(gridVo);
        }
        return buildTreeRecursive(map, 0L);
    }

    private List<GridTreeVo> buildTree2(List<GridTreeVo> gridVos) {
        Map<Long, List<GridTreeVo>> map = new HashMap<>();
        for (GridTreeVo gridVo : gridVos) {
            if (!map.containsKey(gridVo.getParentId())) {
                map.put(gridVo.getParentId(), new ArrayList<>());
            }
            map.get(gridVo.getParentId()).add(gridVo);
        }
        return buildTreeRecursive2(map, 0L);
    }

    private List<GridVo2> buildTreeRecursive(Map<Long, List<GridVo2>> map, Long parentId) {
        List<GridVo2> gridVos = map.get(parentId);
        if (gridVos == null) {
            return new ArrayList<>();
        }
        gridVos.sort((a, b) -> a.getSort() - b.getSort());
        for (GridVo2 gridVo : gridVos) {
            gridVo.setChildren(buildTreeRecursive(map, gridVo.getId()));
        }
        return gridVos;
    }

    private List<GridTreeVo> buildTreeRecursive2(Map<Long, List<GridTreeVo>> map, Long parentId) {
        List<GridTreeVo> gridVos = map.get(parentId);
        if (gridVos == null) {
            return new ArrayList<>();
        }
        gridVos.sort((a, b) -> a.getSort() - b.getSort());
        for (GridTreeVo gridVo : gridVos) {
            gridVo.setChildren(buildTreeRecursive2(map, gridVo.getId()));
        }
        return gridVos;
    }

    @Override
    public IPageResultData<List<Grid>> listGridQuery(Set<Long> organizationalIds, String name, YueChipPage yueChipPage, Set<Long> userIds, String time) {
        Page<GridPo> page = gridDao.listGridQuery(organizationalIds,name,yueChipPage, userIds, time);
        List<Grid> list = gridMapper.toGrid(page.getContent());
        return new PageResultData<List<Grid>>(list,page.getPageable(),page.getTotalElements());
    }

    @Override
    public List<Grid> listGrid(Long organizationalId) {
        List<GridPo> list = gridDao.findAllByOrganizationalId(organizationalId);
        return gridMapper.toGrid(list);
    }

    @Override
    public List<Grid> findByGridId(Set<Long> gridId) {
        List<GridPo> list = gridDao.findAllByIdIn(gridId);
        return gridMapper.toGrid(list);
    }

    @Override
    public List<OrganizationalUserPo> findUserAllByOrganizationalIdAndUserIdIn(Long organizationalId, Set<Long> userId) {
        List<OrganizationalUserPo> organizationalUserPoList = organizationalUserDao.findAllByOrganizationalIdAndUserIdIn(organizationalId, userId);
        return organizationalUserPoList;
    }

    @Override
    public List<OrganizationalUserPo> findUserAllByUserIdIn(Set<Long> userId) {
        List<OrganizationalUserPo> organizationalUserPoList = organizationalUserDao.findAllByUserIdIn(userId);
        return organizationalUserPoList;
    }

    @Override
    public List<Grid> findGridByName(String name) {
        List<GridPo> list = gridDao.findAllByNameLike(name);
        return gridMapper.toGrid(list);
    }

    @Override
    public Map<String, Long> bindUserOrganizationalGird(List<UserOrganizationalGirdVo> voList) {
        Map<String, Long> bindUserGird = new HashMap<>();
        if (!CollectionUtils.isEmpty(voList)) {
            // 过滤重复网格员
            Set<String> userList = voList.stream().map(UserOrganizationalGirdVo::getNameAndPhone).collect(Collectors.toSet());
            // 筛选正确的网格员信息
            List<String> phoneList = new ArrayList<>();
            userList.forEach(vo -> {
                String phone = vo.replaceAll("\\D", "");
                if (!userDao.findFirstByUsername(phone).isPresent()) {
                    phoneList.add(vo);
                }
            });
            // 用户信息与机构id关联
            Map<String, Long> userOrganizationalIdMap = new HashMap<>();
            voList.forEach(vo -> {
                userOrganizationalIdMap.put(vo.getNameAndPhone(), vo.getOrganizationalId());
            });
            // 添加未录入库的网格员
            if (!CollectionUtils.isEmpty(phoneList)) {
                phoneList.forEach(user -> {
                    String name = user.replaceAll("\\d", "");
                    String phone = user.replaceAll("\\D", "");
                    UserAddOrUpdateDto userAddOrUpdateDto = new UserAddOrUpdateDto();
                    userAddOrUpdateDto.setPasswordI(phone);
                    userAddOrUpdateDto.setUsername(phone);
                    userAddOrUpdateDto.setName(name);
                    userAddOrUpdateDto.setPhoneNumber(phone);
                    List<Long> organizationalIds = new ArrayList<>();
                    organizationalIds.add(userOrganizationalIdMap.get(user));
                    userAddOrUpdateDto.setOrganizationalId(organizationalIds);
                    upmsApplication.saveUser(userAddOrUpdateDto);
                });
            }

            voList.forEach(vo -> {
                String phone = vo.getNameAndPhone().replaceAll("\\D", "");
                UserPo user = userDao.findFirstByUsername(phone).get();
                // 找出相似的网格
                List<GridPo> gridPoList = gridDao.findAllByNameLike("%" + vo.getGirdName() + "%");
                if (!CollectionUtils.isEmpty(gridPoList)) {
                    // 网格与网格父节点关联
                    Map<Long, Long> gridParentIdMap = gridPoList.stream().collect(Collectors.toMap(GridPo::getId, GridPo::getParentId));
                    // 网格id
                    List<Long> gridList = gridPoList.stream().map(GridPo::getId).collect(Collectors.toList());
                    List<GridUserPo> gridUserList = gridUserDao.findAllByGridIdInAndUserId(gridList, user.getId());
                    if (!CollectionUtils.isEmpty(gridUserList)) { // 默认网格员在类似网格名称中，只绑定一个网格
                        // 网格员已与网格绑定
                        bindUserGird.put(vo.getNameAndPhone(), gridUserList.get(0).getGridId());
                    } else {
                        // 网格员未在类似网格名称中绑定过网格
                        // 最父级的网格
                        Long parentGridId = 0L;
                        for (GridPo gridUserPo : gridPoList) {
                            if (gridParentIdMap.get(gridUserPo.getId()) == 0) {
                                parentGridId = gridUserPo.getId();
                            }
                        }
                        if (parentGridId > 0) {
                            // 创建子网格
                            int i = gridPoList.size();
                            String gridName = vo.getGirdName() + i;
                            Long organizationalId = userOrganizationalIdMap.get(vo.getNameAndPhone());
                            GridPo gridPo = new GridPo();
                            gridPo.setParentId(parentGridId);
                            gridPo.setOrganizationalId(organizationalId);
                            gridPo.setName(gridName);
                            gridPo.setUserId(user.getId());
                            gridPo.setSort(1);
                            GridPo gridPo1 = gridDao.save(gridPo);
                            GridUserPo userPo = new GridUserPo();
                            userPo.setGridId(gridPo1.getId());
                            userPo.setUserId(user.getId());
                            gridUserDao.save(userPo);
                            bindUserGird.put(vo.getNameAndPhone(), gridPo1.getId());
                        }
                    }
                } else { // 一个类似网格名称都没有创建
                    // 创建父节点网格
                    Long organizationalId = userOrganizationalIdMap.get(vo.getNameAndPhone());
                    GridPo gridPo = new GridPo();
                    gridPo.setParentId(0L);
                    gridPo.setOrganizationalId(organizationalId);
                    gridPo.setSort(1);
                    gridPo.setName(vo.getGirdName());
                    GridPo parentGridPo = gridDao.save(gridPo);
                    GridPo gridPo1 = new GridPo();
                    gridPo1.setParentId(parentGridPo.getId());
                    gridPo1.setOrganizationalId(organizationalId);
                    gridPo1.setSort(1);
                    gridPo1.setUserId(user.getId());
                    gridPo1.setName(vo.getGirdName() + 1);
                    GridPo gridPo2 = gridDao.save(gridPo);
                    GridUserPo userPo = new GridUserPo();
                    userPo.setGridId(gridPo2.getId());
                    userPo.setUserId(user.getId());
                    gridUserDao.save(userPo);
                    bindUserGird.put(vo.getNameAndPhone(), gridPo2.getId());
                }
            });
        }
        return bindUserGird;
    }

    private void findAllChildren(Long parentId,List<Organizational> organizationalList) {
        List<OrganizationalPo> list = organizationalDao.findAllByParentId(parentId);
        list.forEach(organizationalPo -> {
            organizationalList.add(organizationalMapper.toOrganizational(organizationalPo));
            findAllChildren(organizationalPo.getId(),organizationalList);
        });
    }
}
