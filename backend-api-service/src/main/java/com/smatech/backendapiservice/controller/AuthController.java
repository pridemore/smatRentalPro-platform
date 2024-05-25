package com.smatech.backendapiservice.controller;

import com.smatech.backendapiservice.common.response.CommonResponse;
import com.smatech.backendapiservice.domain.dto.LoginDto;
import com.smatech.backendapiservice.domain.dto.UserDto;
import com.smatech.backendapiservice.service.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public CommonResponse register(@RequestBody UserDto registerDto) {
        return userService.registerUser(registerDto);
    }

    @PostMapping("login")
    public CommonResponse login(@RequestBody LoginDto loginDto){
        return userService.login(loginDto);
    }

}
