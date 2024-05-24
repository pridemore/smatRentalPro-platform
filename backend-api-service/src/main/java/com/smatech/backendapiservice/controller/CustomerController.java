package com.smatech.backendapiservice.controller;

import com.smatech.backendapiservice.common.response.CommonResponse;
import com.smatech.backendapiservice.domain.Customer;
import com.smatech.backendapiservice.domain.dto.CustomerDto;
import com.smatech.backendapiservice.service.api.CustomerService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public CommonResponse create(@RequestBody CustomerDto customerDto){
        return customerService.createCustomer(customerDto);
    }

}
