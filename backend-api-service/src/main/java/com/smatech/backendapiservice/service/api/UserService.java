package com.smatech.backendapiservice.service.api;

import com.smatech.backendapiservice.common.response.CommonResponse;
import com.smatech.backendapiservice.domain.UserEntity;
import com.smatech.backendapiservice.domain.dto.LoginDto;
import com.smatech.backendapiservice.domain.dto.UserDto;

import java.util.Optional;

public interface UserService {
    Optional<UserEntity> findByUsername(String adminUsername);

    CommonResponse registerUser(UserDto registerDto);

    CommonResponse login(LoginDto loginDto);
}
