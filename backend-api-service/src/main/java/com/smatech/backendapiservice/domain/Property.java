package com.smatech.backendapiservice.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.smatech.backendapiservice.common.enums.PropertyStatus;
import com.smatech.backendapiservice.common.enums.PropertyType;
import com.smatech.backendapiservice.common.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.OffsetDateTime;

@Entity
@Table(name = "properties")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long propertyId;

    @Enumerated(EnumType.STRING)
    private PropertyType propertyType;

    private String address;

    private String size;

    private String description;

    private double rentalAmount;

    private boolean hasApplication;

    @Enumerated(EnumType.STRING)
    private PropertyStatus propertyStatus;

    private String propertyImage;

    @Enumerated(EnumType.STRING)
    private Status status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private OffsetDateTime dateCreated;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @UpdateTimestamp
    private OffsetDateTime lastUpdated;
}
