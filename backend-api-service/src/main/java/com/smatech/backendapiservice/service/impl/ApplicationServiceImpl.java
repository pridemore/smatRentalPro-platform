package com.smatech.backendapiservice.service.impl;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.smatech.backendapiservice.common.enums.ApplicationStatus;
import com.smatech.backendapiservice.common.enums.PropertyStatus;
import com.smatech.backendapiservice.common.enums.Status;
import com.smatech.backendapiservice.common.response.CommonResponse;
import com.smatech.backendapiservice.domain.Application;
import com.smatech.backendapiservice.domain.Customer;
import com.smatech.backendapiservice.domain.Property;
import com.smatech.backendapiservice.domain.dto.ApplicationDto;
import com.smatech.backendapiservice.domain.dto.ApplicationResponseDto;
import com.smatech.backendapiservice.persistance.ApplicationRepository;
import com.smatech.backendapiservice.persistance.CustomerRepository;
import com.smatech.backendapiservice.persistance.PropertyRepository;
import com.smatech.backendapiservice.service.api.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.smatech.backendapiservice.common.SystemConstants.FAILURE_MESSAGE;
import static com.smatech.backendapiservice.common.SystemConstants.SUCCESS_MESSAGE;

@Service
public class ApplicationServiceImpl implements ApplicationService {

    private ApplicationRepository applicationRepository;
    private CustomerRepository customerRepository;

    private PropertyRepository propertyRepository;

    @Autowired
    public ApplicationServiceImpl(ApplicationRepository applicationRepository, CustomerRepository customerRepository, PropertyRepository propertyRepository) {
        this.applicationRepository = applicationRepository;
        this.customerRepository = customerRepository;
        this.propertyRepository = propertyRepository;
    }

    @Override
    public CommonResponse createApplication(ApplicationDto applicationDto) {
        Optional<Customer> customer = customerRepository.findById(applicationDto.getCustomerId());
        if (customer.isEmpty()) {
            return new CommonResponse().buildErrorResponse(FAILURE_MESSAGE, "Customer is not registers");
        }

        Optional<Property> property = propertyRepository.findById(applicationDto.getPropertyId());
        if (!property.isPresent()) {
            return new CommonResponse().buildErrorResponse(FAILURE_MESSAGE, "Property not available");
        }

        if (applicationDto.getDepositPrice() < property.get().getRentalAmount()) {
            return new CommonResponse().buildErrorResponse(FAILURE_MESSAGE,
                    "Deposit amount should be equal or above property rental fee.");
        }

        Application application = Application.builder()
                .startDate(applicationDto.getStartDate())
                .endDate(applicationDto.getEndDate())
                .depositPrice(applicationDto.getDepositPrice())
                .customer(customer.get())
                .property(property.get())
                .applicationStatus(ApplicationStatus.IN_PROGRESS)
                .status(Status.ACTIVE)
                .dateCreated(OffsetDateTime.now())
                .lastUpdated(OffsetDateTime.now())
                .build();


        property.get().setHasApplication(true);
        property.get().setPropertyStatus(PropertyStatus.OCCUPIED);
        propertyRepository.save(property.get());

        return new CommonResponse().buildSuccessResponse(SUCCESS_MESSAGE, applicationRepository.save(application));

    }

    @Override
    public CommonResponse getAllApplications() {
        List<Application> all = applicationRepository.findAll();

        List<ApplicationResponseDto> response = all.stream().map(this::getApplicationsResponseDto)
                .collect(Collectors.toList());
        return new CommonResponse().buildSuccessResponse(SUCCESS_MESSAGE,response);
    }

    private ApplicationResponseDto getApplicationsResponseDto(Application application) {
        return ApplicationResponseDto.builder()
                .startDate(application.getStartDate())
                .endDate(application.getEndDate())
                .depositPrice(application.getDepositPrice())
                .customerId(application.getCustomer().getCustomerId())
                .customerName(application.getCustomer().getName())
                .customerSurname(application.getCustomer().getSurname())
                .customerEmail(application.getCustomer().getEmail())
                .propertyType(application.getProperty().getPropertyType())
                .address(application.getProperty().getAddress())
                .size(application.getProperty().getSize())
                .description(application.getProperty().getDescription())
                .rentalAmount(application.getProperty().getRentalAmount())
                .propertyId(application.getProperty().getPropertyId())
                .applicationStatus(application.getApplicationStatus())
                .build();
    }

}
