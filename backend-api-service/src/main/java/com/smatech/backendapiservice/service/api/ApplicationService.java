package com.smatech.backendapiservice.service.api;

import com.smatech.backendapiservice.common.enums.ApplicationStatus;
import com.smatech.backendapiservice.common.response.CommonResponse;
import com.smatech.backendapiservice.domain.dto.ApplicationDto;

public interface ApplicationService {
    CommonResponse createApplication(ApplicationDto applicationDto);

    CommonResponse findAllApplications(ApplicationStatus status);
}
