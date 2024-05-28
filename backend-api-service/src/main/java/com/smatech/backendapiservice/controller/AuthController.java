package com.smatech.backendapiservice.controller;

import com.smatech.backendapiservice.common.response.CommonResponse;
import com.smatech.backendapiservice.domain.dto.LoginDto;
import com.smatech.backendapiservice.service.api.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("login")
    public CommonResponse login(@RequestBody LoginDto loginDto){
        return authService.login(loginDto);
    }

}
