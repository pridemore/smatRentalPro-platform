package com.smatech.backendapiservice.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.smatech.backendapiservice.common.enums.ApplicationStatus;
import com.smatech.backendapiservice.common.enums.PropertyType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationResponseDto {
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;
    private double depositPrice;
    private long customerId;
    private String customerName;
    private String customerSurname;
    private String customerEmail;
    private PropertyType propertyType;
    private String address;
    private String size;
    private String description;
    private double rentalAmount;
    private long propertyId;
     private ApplicationStatus applicationStatus;
}
