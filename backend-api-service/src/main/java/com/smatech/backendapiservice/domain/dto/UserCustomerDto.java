package com.smatech.backendapiservice.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCustomerDto {
    @JsonProperty
    private long userId;
    @JsonProperty
    private long customerId;
}
