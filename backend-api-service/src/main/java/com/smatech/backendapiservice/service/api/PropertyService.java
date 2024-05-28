package com.smatech.backendapiservice.service.api;

import com.smatech.backendapiservice.common.enums.PropertyStatus;
import com.smatech.backendapiservice.common.response.CommonResponse;
import com.smatech.backendapiservice.domain.dto.PropertyDto;

public interface PropertyService {
    CommonResponse createProperty(PropertyDto applicationDto);

    CommonResponse findAllProperties(PropertyStatus status);

    CommonResponse getAllCustomerAppliedProperties(long customerId);

    CommonResponse getAllListedProperties();

    CommonResponse getAllAvailableListedProperties();
}
