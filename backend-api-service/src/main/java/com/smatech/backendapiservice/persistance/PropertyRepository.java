package com.smatech.backendapiservice.persistance;

import com.smatech.backendapiservice.domain.Property;
import com.smatech.backendapiservice.domain.dto.PropertyDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PropertyRepository extends JpaRepository<Property, Long> {

    @Query(value = "select PROPERTIES.PROPERTY_ID,PROPERTIES.DATE_CREATED,PROPERTIES.HAS_APPLICATION" +
            ",PROPERTIES.PROPERTY_TYPE,PROPERTIES.ADDRESS,PROPERTIES.SIZE,PROPERTIES.DESCRIPTION,PROPERTIES.RENTAL_AMOUNT" +
            ",PROPERTIES.PROPERTY_IMAGE,PROPERTIES.LAST_UPDATED,PROPERTIES.PROPERTY_STATUS,PROPERTIES.STATUS \n" +
            " from PROPERTIES \n" +
            " left join  Applications on PROPERTIES.PROPERTY_ID =APPLICATIONS.PROPERTY_ID \n" +
            "left join CUSTOMERS on CUSTOMERS.CUSTOMER_ID =APPLICATIONS.CUSTOMER_ID \n" +
            "where CUSTOMERS.CUSTOMER_ID =:customerId",nativeQuery = true)
    List<Property> findAllCustomerAppliedProperties(long customerId);
}
