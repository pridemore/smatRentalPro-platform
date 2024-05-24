package com.smatech.backendapiservice.domain.dto;

import com.smatech.backendapiservice.common.enums.PropertyType;
import com.smatech.backendapiservice.domain.Application;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PropertyDto {

    private String propertyType;

    private String address;

    private String size;

    private String description;


}
