package com.smatech.backendapiservice.service.impl;

import com.smatech.backendapiservice.common.enums.ApplicationStatus;
import com.smatech.backendapiservice.common.response.CommonResponse;
import com.smatech.backendapiservice.domain.dto.ApplicationDto;
import com.smatech.backendapiservice.service.api.ApplicationService;
import org.springframework.stereotype.Service;

@Service
public class ApplicationServiceImpl implements ApplicationService {

    @Override
    public CommonResponse createApplication(ApplicationDto applicationDto) {
        return null;
    }

    @Override
    public CommonResponse findAllApplications(ApplicationStatus status) {
        return null;
    }
}
