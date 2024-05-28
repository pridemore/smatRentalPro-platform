package com.smatech.backendapiservice.persistance;

import com.smatech.backendapiservice.domain.Customer;
import com.smatech.backendapiservice.domain.UserEntity;
import com.smatech.backendapiservice.domain.dto.UserCustomerDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity,Long> {
    Optional<UserEntity> findByUsername(String username);
    Boolean existsByUsername(String username);

}
