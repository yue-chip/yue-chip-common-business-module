package com.yue.chip.upms;

import com.yue.chip.core.PageResultData;
import com.yue.chip.core.ResultData;
import com.yue.chip.core.YueChipPage;
import com.yue.chip.core.common.enums.State;
import com.yue.chip.core.common.enums.UserType;
import com.yue.chip.upms.vo.UserExposeVo;

import java.util.List;
import java.util.Map;


public interface RemoteUpmsDefinition {

    static final String PREFIX = "/upms";

    static final String FIND = "/remote/user/find";
    static final String FIND_1 = "/remote/user/find1";
    static final String FIND_2 = "/remote/user/find2";
    static final String FIND_3 = "/remote/user/find3";
    static final String FIND_4 = "/remote/user/find4";
    static final String FIND_5 = "/remote/user/find5";
    static final String FIND_6 = "/remote/user/find6";
    static final String FIND_PHONE = "/remote/user/find/phone";
    static final String FIND_EMAIL = "/remote/user/find/email";
    static final String REGISTER = "/remote/user/find/register";
    static final String REGISTER_EMAIL = "/remote/user/find/register/email";
    static final String LOGOUT_USER = "/remote/user/logout/user";
    static final String UPDATE_PASSWORD = "/remote/user/update/password";

    /**
     * 根据ids查询所有用户
     * @param userIds
     * @return
     */
    ResultData<List<UserExposeVo>> find( List<Long> userIds);

    ResultData<UserExposeVo> find( Long userId);

    ResultData<UserExposeVo> find( String username);

    PageResultData<List<UserExposeVo>> find(String nameLike, UserType userType, YueChipPage yueChipPage, Map<String,Object> map);
    /**
     * 根据用户id和租户编码查寻用户
     * @param id
     * @param tenantNumber
     * @return
     */
    ResultData<UserExposeVo> find( Long id, Long tenantNumber);

    PageResultData<List<UserExposeVo>> find(YueChipPage yueChipPage, Map<String,Object> map);

    PageResultData<List<UserExposeVo>> find(String name, String nickname, String username, String phoneNumber, String email, State state, String nameLike, YueChipPage yueChipPage, Map<String,Object> map);

    ResultData<UserExposeVo> findPhoneNumber( String phoneNumber);

    ResultData<UserExposeVo> findEmail( String email);

    /**
     * APP用户注册或修改密码
     * @param phoneNumber
     * @param password
     * @param id
     */
    ResultData register( String phoneNumber,  String password, String name, Long id);

    /**
     * APP用户注册或修改密码(根据Email)
     * @param email         email
     * @param password      密码
     * @param id            用户ID
     */
    ResultData registerByEmail( String email,  String password, String name, Long id);

    /**
     * 注销账号
     */
    ResultData logoutUser( Long userId);

    ResultData updateUserPassword( Long userId,  String password);
}
