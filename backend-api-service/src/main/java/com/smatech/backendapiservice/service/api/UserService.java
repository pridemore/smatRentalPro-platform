package com.smatech.backendapiservice.service.api;

import com.smatech.backendapiservice.domain.UserEntity;

import java.util.Optional;

public interface UserService {
    Optional<UserEntity> findByUsername(String adminUsername);
}
