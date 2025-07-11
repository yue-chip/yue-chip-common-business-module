package com.yue.chip.upms.interfaces.facade.remote.http;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yue.chip.core.CurrentUserClient;
import com.yue.chip.upms.domain.aggregates.User;
import com.yue.chip.upms.domain.repository.upms.UpmsRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.java.Log;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;

@RestController()
@RequestMapping("/remote")
@Validated
@Tag(name = "远程获取当前用户")
@Log
public class CurrentUserClientController implements CurrentUserClient {

    @Resource
    private UpmsRepository upmsRepository;

    @Override
    @GetMapping("/user/map")
    public Map<String, Object> findUserToMap(String username) {
        Optional<User> optional = upmsRepository.findUserByUsername(username);
        if (optional.isPresent()) {
            User user = optional.get();
            Map<String, Object> map = new ObjectMapper().convertValue(user,Map.class);
            return map;
        }
        return null;
    }
}
