package com.smatech.backendapiservice.service.impl;

import com.smatech.backendapiservice.common.enums.PropertyStatus;
import com.smatech.backendapiservice.common.response.CommonResponse;
import com.smatech.backendapiservice.domain.dto.PropertyDto;
import com.smatech.backendapiservice.service.api.PropertyService;
import org.springframework.stereotype.Service;

@Service
public class PropertyServiceImpl implements PropertyService {

    @Override
    public CommonResponse createProperty(PropertyDto applicationDto) {
        return null;
    }

    @Override
    public CommonResponse findAllProperties(PropertyStatus status) {
        return null;
    }
}
