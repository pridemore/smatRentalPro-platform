package com.smatech.backendapiservice.persistance;

import com.smatech.backendapiservice.domain.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity,Long> {
}
