package com.smatech.backendapiservice.service.impl;

import com.smatech.backendapiservice.common.response.CommonResponse;
import com.smatech.backendapiservice.domain.dto.ReportStatisticsResponseDto;
import com.smatech.backendapiservice.persistance.ApplicationRepository;
import com.smatech.backendapiservice.persistance.CustomerRepository;
import com.smatech.backendapiservice.persistance.PropertyRepository;
import com.smatech.backendapiservice.service.api.ApplicationService;
import com.smatech.backendapiservice.service.api.CustomerService;
import com.smatech.backendapiservice.service.api.PropertyService;
import com.smatech.backendapiservice.service.api.ReportService;
import org.springframework.stereotype.Service;

import static com.smatech.backendapiservice.common.SystemConstants.SUCCESS_MESSAGE;

@Service
public class ReportServiceImpl implements ReportService {

    private PropertyRepository propertyRepository;

    private ApplicationRepository applicationRepository;

    private CustomerRepository customerRepository;

    public ReportServiceImpl(PropertyRepository propertyRepository, ApplicationRepository applicationRepository, CustomerRepository customerRepository) {
        this.propertyRepository = propertyRepository;
        this.applicationRepository = applicationRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public CommonResponse getReportStatistics() {

        ReportStatisticsResponseDto report = ReportStatisticsResponseDto.builder()
                .totalNumberOfApplications(applicationRepository.getNumberOfApplications())
                .totalRegisteredProperties(propertyRepository.totalRegisteredProperties())
                .totalNumberOfCustomers(customerRepository.getNumberOfCustomers())
                .build();
        return new CommonResponse().buildSuccessResponse(SUCCESS_MESSAGE,report);
    }
}
