package com.smatech.backendapiservice.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportStatisticsResponseDto {
    private int totalNumberOfCustomers;

    private int totalRegisteredProperties;

    private int totalNumberOfApplications;

}
