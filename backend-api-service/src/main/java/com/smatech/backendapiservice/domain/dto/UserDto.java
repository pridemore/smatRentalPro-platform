package com.smatech.backendapiservice.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private String name;

    private String surname;

    private String username;

    private String password;

    private String nationalId;

    private String gender;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dob;

    private String address;

    private String phoneNumber;

}
