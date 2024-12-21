package com.yue.chip.upms.util;

import com.yue.chip.exception.AuthorizationException;
import com.yue.chip.security.YueChipUserDetails;
import com.yue.chip.utils.SpringContextUtil;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Objects;

/**
 * @author jiacheng.liao on 2024/12/20
 */
public class TestYueChipRedisTokenStoreUtil {

    private static volatile RedisTemplate redisTemplate;

    public TestYueChipRedisTokenStoreUtil() {
    }

    public static void store(YueChipUserDetails yueChipUserDetails, String token, Long time) {
        TestCurrentUserRedisUtil.setUsername(token, yueChipUserDetails.getUsername(), time);
        TestCurrentUserRedisUtil.setTenantNumber(token, yueChipUserDetails.getTenantNumber(), time);
        TestCurrentUserRedisUtil.setUserId(token, yueChipUserDetails.getId(), time);
        TestCurrentUserRedisUtil.setAuthority(token, yueChipUserDetails.getAuthorities(), time);
    }

    public static void clean(String token) {
        TestCurrentUserRedisUtil.deleteTenantNumber(token);
        TestCurrentUserRedisUtil.deleteAuthority(token);
        TestCurrentUserRedisUtil.deleteUserId(token);
        TestCurrentUserRedisUtil.deleteUsername(token);
    }

    public static void renewal(String token, Long time) {
//        Long i = LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();
        Long userId = TestCurrentUserRedisUtil.getUserId(token);
        if (Objects.isNull(userId)) {
            AuthorizationException.throwException("登陆异常，请重新登陆");
        }

        TestCurrentUserRedisUtil.expireTenantNumber(token, time);
        TestCurrentUserRedisUtil.expireUserId(token, time);
        TestCurrentUserRedisUtil.expireUsername(token, time);
        TestCurrentUserRedisUtil.expireAuthority(token, time);
    }

    public static String getUsername(String token) {
        Object obj = getRedisTemplate().opsForValue().get("token-username-" + token);
        return Objects.nonNull(obj) ? (String)obj : "";
    }

    private static RedisTemplate getRedisTemplate() {
        if (Objects.isNull(redisTemplate)) {
            Class var0 = com.yue.chip.utils.YueChipRedisTokenStoreUtil.class;
            synchronized(com.yue.chip.utils.YueChipRedisTokenStoreUtil.class) {
                redisTemplate = (RedisTemplate) SpringContextUtil.getBean(RedisTemplate.class);
            }
        }

        return redisTemplate;
    }
}
