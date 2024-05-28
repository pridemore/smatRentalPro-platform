package com.smatech.backendapiservice.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.smatech.backendapiservice.common.enums.Gender;
import com.smatech.backendapiservice.common.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Set;

@Entity
@Table(name = "customers")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Customer implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long customerId;

    private String name;
    private String surname;

    private String username;

    private String password;

    private String nationalId;

    @Enumerated(EnumType.STRING)
    private Gender gender;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dob;

    private String email;

    private String address;

    private String phoneNumber;


    @OneToOne(targetEntity = UserEntity.class, cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_user_Id", referencedColumnName = "userId")
    UserEntity userAccount;

    @ManyToOne(targetEntity = Role.class, fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name="customer_role_id", referencedColumnName = "roleId")
    Role role;

    @Enumerated(EnumType.STRING)
    private Status status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private OffsetDateTime dateCreated;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @UpdateTimestamp
    private OffsetDateTime lastUpdated;

}
