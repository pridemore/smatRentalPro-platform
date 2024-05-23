package com.smatech.backendapiservice.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.smatech.backendapiservice.common.enums.Status;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.OffsetDateTime;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;

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

    @Enumerated(EnumType.STRING)
    private Status status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private OffsetDateTime dateCreated;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @UpdateTimestamp
    private OffsetDateTime lastUpdated;

}