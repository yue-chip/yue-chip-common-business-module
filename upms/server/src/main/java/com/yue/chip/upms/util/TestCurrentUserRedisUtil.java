package com.yue.chip.upms.util;

import com.yue.chip.security.YueChipSimpleGrantedAuthority;
import com.yue.chip.utils.CurrentUserUtil;
import com.yue.chip.utils.SpringContextUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author jiacheng.liao on 2024/12/20
 */
public class TestCurrentUserRedisUtil {
    private static volatile RedisTemplate redisTemplate;
    public static final int timeout = 43200;

    public TestCurrentUserRedisUtil() {
    }

    public static Long getTenantNumber(String token) {
        if (!StringUtils.hasText(token)) {
            return null;
        } else {
            Object obj = getRedisTemplate().opsForValue().get("tenant-number-" + token);
            return Objects.nonNull(obj) ? toLong(obj) : null;
        }
    }

    public static void setTenantNumber(String token, Long tenantNumber, Long time) {
        if (StringUtils.hasText(token) && !Objects.isNull(tenantNumber)) {
            getRedisTemplate().opsForValue().set("tenant-number-" + token, tenantNumber, time, TimeUnit.MINUTES);
        }
    }

    public static void deleteTenantNumber(String token) {
        if (StringUtils.hasText(token)) {
            getRedisTemplate().delete("tenant-number-" + token);
        }
    }

    public static void expireTenantNumber(String token, Long time) {
        if (StringUtils.hasText(token)) {
            getRedisTemplate().expire("tenant-number-" + token, time, TimeUnit.MINUTES);
        }
    }

    public static Long getUsername(String token) {
        if (!StringUtils.hasText(token)) {
            return null;
        } else {
            Object obj = getRedisTemplate().opsForValue().get("token-username-" + token);
            return Objects.nonNull(obj) ? toLong(obj) : null;
        }
    }

    public static void setUsername(String token, String username, Long time) {
        if (StringUtils.hasText(token) && StringUtils.hasText(username)) {
            getRedisTemplate().opsForValue().set("token-username-" + token, username, time, TimeUnit.MINUTES);
        }
    }

    public static void deleteUsername(String token) {
        if (StringUtils.hasText(token)) {
            getRedisTemplate().delete("token-username-" + token);
        }
    }

    public static void expireUsername(String token, Long time) {
        if (StringUtils.hasText(token)) {
            getRedisTemplate().expire("token-username-" + token, time, TimeUnit.MINUTES);
        }
    }

    public static Long getUserId(String token) {
        if (!StringUtils.hasText(token)) {
            return null;
        } else {
            Object obj = getRedisTemplate().opsForValue().get("user-id-" + token);
            return Objects.nonNull(obj) ? toLong(obj) : null;
        }
    }

    public static void setUserId(String token, Long userId, Long time) {
        if (StringUtils.hasText(token)) {
            getRedisTemplate().opsForValue().set("user-id-" + token, userId, time, TimeUnit.MINUTES);
        }
    }

    public static void deleteUserId(String token) {
        if (StringUtils.hasText(token)) {
            getRedisTemplate().delete("user-id-" + token);
        }
    }

    public static void expireUserId(String token, Long time) {
        if (StringUtils.hasText(token)) {
            getRedisTemplate().expire("user-id-" + token, time, TimeUnit.MINUTES);
        }
    }

    public static void setAuthority(String token, Collection<GrantedAuthority> authorities, Long time) {
        if (StringUtils.hasText(token)) {
            getRedisTemplate().opsForValue().set("authority-" + token, authorities, time, TimeUnit.DAYS);
        }
    }

    public static void deleteAuthority(String token) {
        if (StringUtils.hasText(token)) {
            getRedisTemplate().delete("authority-" + token);
        }
    }

    public static void expireAuthority(String token, Long time) {
        if (StringUtils.hasText(token)) {
            getRedisTemplate().expire("authority-" + token, time, TimeUnit.MINUTES);
        }
    }

    public static Collection<YueChipSimpleGrantedAuthority> getAuthority() {
        Object obj = redisTemplate.opsForValue().get("authority-" + CurrentUserUtil.getToken());
        if (Objects.nonNull(obj)) {
            Collection<YueChipSimpleGrantedAuthority> list = (Collection)obj;
            return list;
        } else {
            return Collections.EMPTY_LIST;
        }
    }

    private static RedisTemplate getRedisTemplate() {
        if (Objects.isNull(redisTemplate)) {
            Class var0 = CurrentUserUtil.class;
            synchronized(CurrentUserUtil.class) {
                if (Objects.isNull(redisTemplate)) {
                    redisTemplate = (RedisTemplate) SpringContextUtil.getBean("redisTemplate");
                }
            }
        }

        return redisTemplate;
    }

    private static Long toLong(Object number) {
        if (Objects.nonNull(number)) {
            if (number instanceof Integer) {
                return ((Integer)number).longValue();
            }

            if (number instanceof Long) {
                return (Long)number;
            }
        }

        return null;
    }
}
