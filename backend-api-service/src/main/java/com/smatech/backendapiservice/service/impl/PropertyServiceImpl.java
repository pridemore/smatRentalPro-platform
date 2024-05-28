package com.smatech.backendapiservice.service.impl;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.smatech.backendapiservice.common.SystemConstants;
import com.smatech.backendapiservice.common.enums.PropertyStatus;
import com.smatech.backendapiservice.common.enums.PropertyType;
import com.smatech.backendapiservice.common.enums.Status;
import com.smatech.backendapiservice.common.response.CommonResponse;
import com.smatech.backendapiservice.domain.Property;
import com.smatech.backendapiservice.domain.dto.PropertyDto;
import com.smatech.backendapiservice.persistance.PropertyRepository;
import com.smatech.backendapiservice.service.api.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

import static com.smatech.backendapiservice.common.SystemConstants.SUCCESS_MESSAGE;

@Service
public class PropertyServiceImpl implements PropertyService {

    private final PropertyRepository propertyRepository;

    @Autowired
    public PropertyServiceImpl(PropertyRepository propertyRepository) {
        this.propertyRepository = propertyRepository;
    }

    @Override
    public CommonResponse createProperty(PropertyDto propertyDto) {
        Property property = Property.builder()
                .propertyType(PropertyType.valueOf(propertyDto.getPropertyType()))
                .address(propertyDto.getAddress())
                .size(propertyDto.getSize())
                .description(propertyDto.getDescription())
                .hasApplication(false)
                .propertyStatus(PropertyStatus.AVAILABLE)
                .propertyImage(propertyDto.getPropertyImage())
                .rentalAmount(propertyDto.getRentalAmount())
                .status(Status.ACTIVE)
                .dateCreated(OffsetDateTime.now())
                .lastUpdated(OffsetDateTime.now())
                .build();
        return new CommonResponse<>().buildSuccessResponse(SUCCESS_MESSAGE,propertyRepository.save(property));

    }

    @Override
    public CommonResponse findAllProperties(PropertyStatus status) {
        return null;
    }

    @Override
    public CommonResponse getAllCustomerAppliedProperties(long customerId) {
        List<Property> allCustomerAppliedProperties = propertyRepository.findAllCustomerAppliedProperties(customerId);
        return new CommonResponse().buildSuccessResponse(SUCCESS_MESSAGE,allCustomerAppliedProperties);
    }

    @Override
    public CommonResponse getAllListedProperties() {
        return new CommonResponse().buildErrorResponse(SUCCESS_MESSAGE,propertyRepository.findAll());
    }

    @Override
    public CommonResponse getAllAvailableListedProperties() {
        return new CommonResponse().buildErrorResponse(SUCCESS_MESSAGE,
                propertyRepository.findAllByPropertyStatus(PropertyStatus.AVAILABLE)) ;
    }
}
