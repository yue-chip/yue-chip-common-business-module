package com.yue.chip.upms.domain.service;

import com.yue.chip.core.ZoneService;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@Primary
public class ZoneServiceImpl implements ZoneService {

    @Resource
    private RedisTemplate redisTemplate;

    @Override
    public String getI18n() {
        return "zh-cn";
    }

    @Override
    public String getTimezone() {
        return "";
    }
}
