package com.smatech.backendapiservice.service.impl;

import com.smatech.backendapiservice.common.SystemConstants;
import com.smatech.backendapiservice.common.Utility;
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
import com.smatech.backendapiservice.service.api.EmailService;
import com.smatech.backendapiservice.service.api.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
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
import java.util.concurrent.Executor;

import static com.smatech.backendapiservice.common.SystemConstants.*;
import static com.smatech.backendapiservice.common.Utility.generateNewUserEmailMessage;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;
    private JWTTokenGenerator jwtGenerator;
    private final Executor executor;
    private EmailService emailService;

    public UserServiceImpl(RoleRepository roleRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JWTTokenGenerator jwtGenerator, Executor executor, EmailService emailService) {
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtGenerator = jwtGenerator;
        this.executor = executor;
        this.emailService = emailService;
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
                .activationToken(UUID.randomUUID().toString())
                .dob(LocalDate.now())
                .dateCreated(OffsetDateTime.now())
                .lastUpdated(OffsetDateTime.now())
                .status(Status.ACTIVE)
                .build();

        Role roles = roleRepository.findByName(registerDto.getRole()).get();
        user.setRoles(Collections.singletonList(roles));

        UserEntity savedUserEntity = userRepository.save(user);
        sentEmailAfterUserCreation(savedUserEntity,registerDto.getPassword());

        return new CommonResponse().buildSuccessResponse(SUCCESS_MESSAGE, savedUserEntity);
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

    @Async
    void sentEmailAfterUserCreation(UserEntity userEntity,String password){

        Runnable emailTask = () -> {
            //Send email with credentials
            log.info("Sending email with credentials");
            try {

                String link= BASE_URL+"/api/customers/activate?token=" + userEntity.getActivationToken();
                emailService.sendEmail(userEntity.getEmail(), SystemConstants.NEW_USER_EMAIL_SUBJECT,
                        Utility.generateNewUserEmailMessage(userEntity.getUsername(), password,link));
            } catch (Exception ex) {
                log.info("Oops! Couldnt send email. An error occurred");
                log.info(ex.getMessage());
            }
        };
        executor.execute(emailTask);
    }


}
