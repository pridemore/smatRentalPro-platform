package com.smatech.backendapiservice.controller;

import com.smatech.backendapiservice.common.response.CommonResponse;
import com.smatech.backendapiservice.domain.dto.ApplicationDto;
import com.smatech.backendapiservice.service.api.ApplicationService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/applications")
public class ApplicationController {

    private ApplicationService applicationService;

    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @PostMapping("/create")
    public CommonResponse create(@RequestBody ApplicationDto applicationDto){
        return applicationService.createApplication(applicationDto);
    }

    @GetMapping("/all")
    public CommonResponse customerAppliedProperties(){
        return applicationService.getAllApplications();
    }
}
