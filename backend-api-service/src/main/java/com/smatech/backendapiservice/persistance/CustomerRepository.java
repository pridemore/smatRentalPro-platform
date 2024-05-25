package com.smatech.backendapiservice.persistance;

import com.smatech.backendapiservice.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer,Long> {
    Optional<Customer> findByEmail(String email);

    @Query(value = "SELECT count(*) as NumberOfCustomers FROM CUSTOMERS ",nativeQuery = true)
    int getNumberOfCustomers();

}
