package com.smatech.backendapiservice.domain.dto;

import lombok.Data;

@Data
public class LoginResponseDto {
    private long userId;
    private long customerId;
    private String username;
    private String accessToken;
    private String tokenType = "Bearer ";

    public LoginResponseDto(String accessToken) {
        this.accessToken = accessToken;
    }
}
