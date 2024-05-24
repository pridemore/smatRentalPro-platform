package com.smatech.backendapiservice.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.smatech.backendapiservice.common.enums.Gender;
import com.smatech.backendapiservice.domain.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDto {
    private String name;
    private String surname;

    private String username;

    private String password;

    private String nationalId;
    private Gender gender;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dob;

    private String email;

    private String address;

    private String phoneNumber;

    private String role;

}
