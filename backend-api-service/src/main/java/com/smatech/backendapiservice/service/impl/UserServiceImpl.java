package com.smatech.backendapiservice.service.impl;

import com.smatech.backendapiservice.common.enums.Gender;
import com.smatech.backendapiservice.common.enums.Status;
import com.smatech.backendapiservice.common.response.CommonResponse;
import com.smatech.backendapiservice.domain.Role;
import com.smatech.backendapiservice.domain.UserEntity;
import com.smatech.backendapiservice.domain.dto.LoginDto;
import com.smatech.backendapiservice.domain.dto.LoginResponseDto;
import com.smatech.backendapiservice.domain.dto.UserDto;
import com.smatech.backendapiservice.persistance.RoleRepository;
import com.smatech.backendapiservice.persistance.UserRepository;
import com.smatech.backendapiservice.security.JWTTokenGenerator;
import com.smatech.backendapiservice.service.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static com.smatech.backendapiservice.common.SystemConstants.FAILURE_MESSAGE;
import static com.smatech.backendapiservice.common.SystemConstants.SUCCESS_MESSAGE;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    private AuthenticationManager authenticationManager;
    private JWTTokenGenerator jwtGenerator;

    public UserServiceImpl(RoleRepository roleRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JWTTokenGenerator jwtGenerator) {
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtGenerator = jwtGenerator;
    }

    @Override
    public Optional<UserEntity> findByUsername(String adminUsername) {
        return userRepository.findByUsername(adminUsername);
    }

    @Override
    public CommonResponse registerUser(UserDto registerDto) {

        if (userRepository.existsByUsername(registerDto.getUsername()).equals(Boolean.TRUE)) {
            return new CommonResponse().buildErrorResponse(FAILURE_MESSAGE, "Username is taken already exist");
        }
        UserEntity user = UserEntity.builder()
                .username(registerDto.getUsername())
                .password(passwordEncoder.encode((registerDto.getPassword())))
                .name(registerDto.getName())
                .surname(registerDto.getSurname())
                .username(registerDto.getUsername())
                .gender(Gender.valueOf(registerDto.getGender()))
                .address(registerDto.getAddress())
                .nationalId(registerDto.getNationalId())
                .phoneNumber(registerDto.getPhoneNumber())
                .email(registerDto.getEmail())
                .password(passwordEncoder.encode(registerDto.getPassword()))
                .activationToken(UUID.randomUUID().toString())
                .dob(LocalDate.now())
                .dateCreated(OffsetDateTime.now())
                .lastUpdated(OffsetDateTime.now())
                .status(Status.ACTIVE)
                .build();

        Role roles = roleRepository.findByName(registerDto.getRole()).get();
        user.setRoles(Collections.singletonList(roles));

        return new CommonResponse().buildSuccessResponse(SUCCESS_MESSAGE, userRepository.save(user));
    }

    @Override
    public CommonResponse login(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getUsername(),
                        loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtGenerator.generateToken(authentication);
        return new CommonResponse().buildSuccessResponse(SUCCESS_MESSAGE, new LoginResponseDto(token));
    }
}
