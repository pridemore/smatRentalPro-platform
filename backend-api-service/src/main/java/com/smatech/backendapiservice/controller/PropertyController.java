package com.smatech.backendapiservice.controller;

import com.smatech.backendapiservice.common.response.CommonResponse;
import com.smatech.backendapiservice.domain.dto.PropertyDto;
import com.smatech.backendapiservice.service.api.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/properties")
public class PropertyController {

    private final PropertyService propertyService;

    @Autowired
    public PropertyController(PropertyService propertyService) {
        this.propertyService = propertyService;
    }

    @PostMapping("/create")
    public CommonResponse create(@RequestBody PropertyDto propertyDto){
        return propertyService.createProperty(propertyDto);
    }

    @GetMapping("/customer-applied/{customerId}")
    public CommonResponse customerAppliedProperties(@PathVariable("customerId")long customerId){
        return propertyService.getAllCustomerAppliedProperties(customerId);
    }
}
