package com.smatech.backendapiservice.service.impl;

import com.smatech.backendapiservice.common.response.CommonResponse;
import com.smatech.backendapiservice.domain.Customer;
import com.smatech.backendapiservice.domain.UserEntity;
import com.smatech.backendapiservice.domain.dto.LoginDto;
import com.smatech.backendapiservice.domain.dto.LoginResponseDto;
import com.smatech.backendapiservice.security.JWTTokenGenerator;
import com.smatech.backendapiservice.service.api.AuthService;
import com.smatech.backendapiservice.service.api.CustomerService;
import com.smatech.backendapiservice.service.api.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import static com.smatech.backendapiservice.common.SystemConstants.SUCCESS_MESSAGE;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {
    private AuthenticationManager authenticationManager;
    private JWTTokenGenerator jwtGenerator;

    private UserService userService;

    private CustomerService customerService;

    public AuthServiceImpl(AuthenticationManager authenticationManager, JWTTokenGenerator jwtGenerator, UserService userService, CustomerService customerService) {
        this.authenticationManager = authenticationManager;
        this.jwtGenerator = jwtGenerator;
        this.userService = userService;
        this.customerService = customerService;
    }

    @Override
    public CommonResponse login(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getUsername(),
                        loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtGenerator.generateToken(authentication);
        User principal = (User) authentication.getPrincipal();

        LoginResponseDto response = new LoginResponseDto(token);


        if (principal.getAuthorities().stream().anyMatch(role -> role.getAuthority().equals("CUSTOMER"))) {
            Customer userCustomer = customerService.getCustomerByUsername(principal.getUsername());
            response.setCustomerId(userCustomer.getCustomerId());
            response.setUserId(userCustomer.getUserAccount().getUserId());
            response.setUsername(userCustomer.getUsername());
        }else{
            UserEntity byUsername = userService.findByUsername(principal.getUsername()).get();
            response.setUserId(byUsername.getUserId());
            response.setUsername(byUsername.getUsername());
        }

        return new CommonResponse().buildSuccessResponse(SUCCESS_MESSAGE, response);
    }
}
