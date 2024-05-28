package com.smatech.backendapiservice.persistance;

import com.smatech.backendapiservice.domain.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ApplicationRepository extends JpaRepository<Application,Long> {

    @Query(value = "SELECT count(*) as NumberOfApplications FROM APPLICATIONS ",nativeQuery = true)
    int getNumberOfApplications();
}
