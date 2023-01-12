package com.yue.chip.upms.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@Validated
@Tag(name = "测试")
public class TestController {


    @GetMapping("/hello")
    @Operation(summary = "测试")
    public String test(){
        return "test";
    }


}
