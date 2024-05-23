package com.smatech.backendapiservice.persistance;

import com.smatech.backendapiservice.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,Long> {
}
