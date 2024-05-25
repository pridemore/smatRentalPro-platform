package com.smatech.backendapiservice.persistance;

import com.smatech.backendapiservice.domain.Application;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationRepository extends JpaRepository<Application,Long> {
}
