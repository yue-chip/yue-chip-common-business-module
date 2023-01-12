package com.yue.chip.upms.dao;

import com.yue.chip.core.YueChipPage;
import com.yue.chip.upms.entity.User;
import org.springframework.data.domain.Page;

public interface UserDaoEx {

    public Page<User> test(YueChipPage yueChipPage);
}
