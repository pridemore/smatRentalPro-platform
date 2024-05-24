package com.smatech.backendapiservice.service.impl;

import com.smatech.backendapiservice.domain.UserEntity;
import com.smatech.backendapiservice.persistance.UserRepository;
import com.smatech.backendapiservice.service.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public Optional<UserEntity> findByUsername(String adminUsername) {
        return userRepository.findByUsername(adminUsername);
    }
}
