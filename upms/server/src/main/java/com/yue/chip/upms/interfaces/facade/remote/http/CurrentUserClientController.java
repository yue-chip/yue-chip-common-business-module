package com.yue.chip.upms.interfaces.facade.remote.http;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yue.chip.core.remote.http.CurrentUserClientDefinition;
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
@RequestMapping("")
@Validated
@Tag(name = "远程获取当前用户")
@Log
public class CurrentUserClientController implements CurrentUserClientDefinition {

    @Resource
    private UpmsRepository upmsRepository;

    @Override
    @GetMapping(USER_TO_MAP)
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
