package com.yue.chip.upms.service;

import com.yue.chip.core.YueChipPage;
import com.yue.chip.core.service.BaseService;
import com.yue.chip.upms.entity.User;
import org.springframework.data.domain.Page;

public interface UserService extends BaseService<User> {

    public Page<User> test(YueChipPage yueChipPage);
}

