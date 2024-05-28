package com.smatech.backendapiservice.service.api;

import com.smatech.backendapiservice.common.response.CommonResponse;
import com.smatech.backendapiservice.domain.dto.LoginDto;

public interface AuthService {
    CommonResponse login(LoginDto loginDto);
}
