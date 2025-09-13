package com.yue.chip.common.business.domain.service;

import com.yue.chip.core.health.HealthCheckUpService;
import io.swagger.v3.oas.annotations.servers.Server;

@Server
public class HealthCheckUpServiceImpl implements HealthCheckUpService {
    @Override
    public Boolean checkHealth() {
        return true;
    }
}
