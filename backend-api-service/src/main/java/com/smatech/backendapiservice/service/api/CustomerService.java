package com.smatech.backendapiservice.service.api;

import com.smatech.backendapiservice.common.response.CommonResponse;
import com.smatech.backendapiservice.domain.dto.CustomerDto;

public interface CustomerService {
    CommonResponse createCustomer(CustomerDto customerDto);
}
