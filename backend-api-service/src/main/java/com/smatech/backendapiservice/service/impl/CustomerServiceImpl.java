package com.smatech.backendapiservice.service.impl;

import com.smatech.backendapiservice.common.SystemConstants;
import com.smatech.backendapiservice.common.Utility;
import com.smatech.backendapiservice.common.enums.Status;
import com.smatech.backendapiservice.common.response.CommonResponse;
import com.smatech.backendapiservice.domain.Customer;
import com.smatech.backendapiservice.domain.Role;
import com.smatech.backendapiservice.domain.UserEntity;
import com.smatech.backendapiservice.domain.dto.CustomerDto;
import com.smatech.backendapiservice.persistance.CustomerRepository;
import com.smatech.backendapiservice.persistance.RoleRepository;
import com.smatech.backendapiservice.persistance.UserRepository;
import com.smatech.backendapiservice.service.api.CustomerService;
import com.smatech.backendapiservice.service.api.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

import static com.smatech.backendapiservice.common.SystemConstants.FAILURE_MESSAGE;
import static com.smatech.backendapiservice.common.SystemConstants.SUCCESS_MESSAGE;

@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {
    private CustomerRepository customerRepository;

    private PasswordEncoder passwordEncoder;

    private EmailService emailService;

    private UserRepository userRepository;

    private RoleRepository roleRepository;

    private final Executor executor;

    public CustomerServiceImpl(CustomerRepository customerRepository,
                               PasswordEncoder passwordEncoder,
                               EmailService emailService,
                               UserRepository userRepository,
                               RoleRepository roleRepository, Executor executor) {
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.executor = executor;
    }

    @Override
    public CommonResponse createCustomer(CustomerDto customerDto) {
        Optional<Role> foundRole=roleRepository.findByName(customerDto.getRole());
        if(!foundRole.isPresent()){
            return new CommonResponse().buildErrorResponse(FAILURE_MESSAGE,"Role is not Found");
        }

        Optional<Customer> foundCustomer=customerRepository.findByEmail(customerDto.getEmail());
        if(foundCustomer.isPresent()){
            return new CommonResponse().buildErrorResponse(FAILURE_MESSAGE,"Email already exist");
        }

        Customer savedCustomer = getCustomerFromDto(customerDto);

        //Generate User Account
        Runnable userEntityCreationTask = () -> {
            log.info("Generating User Entity");
            CompletableFuture<UserEntity> savedProfileCompletableFuture = generateUserEntity(savedCustomer, customerDto.getPassword());

            savedProfileCompletableFuture.thenApply(userEntity -> {
                linkCustomerProfileWithUserEntity(savedCustomer, userEntity);
                return userEntity;
            });
        };

        executor.execute(userEntityCreationTask);

        return new CommonResponse().buildSuccessResponse(SUCCESS_MESSAGE, savedCustomer);
    }

    @Async
    void linkCustomerProfileWithUserEntity(Customer savedCustomer, UserEntity userEntity) {
        savedCustomer.setUserAccount(userEntity);
        customerRepository.save(savedCustomer);
    }

    @Async
    CompletableFuture<UserEntity> generateUserEntity(Customer savedCustomer, String password) {
        UserEntity userEntity = UserEntity.builder()
                .name(savedCustomer.getName())
                .surname(savedCustomer.getSurname())
                .username(savedCustomer.getUsername())
                .gender(savedCustomer.getGender())
                .address(savedCustomer.getAddress())
                .nationalId(savedCustomer.getNationalId())
                .phoneNumber(savedCustomer.getPhoneNumber())
                .email(savedCustomer.getEmail())
                .password(passwordEncoder.encode(password))
                .dob(LocalDate.now())
                .dateCreated(OffsetDateTime.now())
                .lastUpdated(OffsetDateTime.now())
                .status(Status.ACTIVE)
                .build();

        userRepository.save(userEntity);

        Runnable emailTask = () -> {
            //Send email with credentials
            log.info("Sending email with credentials");
            try {
                emailService.sendEmail(userEntity.getEmail(), SystemConstants.NEW_USER_EMAIL_SUBJECT,
                        Utility.generateNewUserEmailMessage(userEntity.getEmail(), password));
            } catch (Exception ex) {
                log.info("Oops! Couldnt send email. An error occurred");
                log.info(ex.getMessage());
            }
        };
        executor.execute(emailTask);

        return CompletableFuture.completedFuture(userEntity);

    }

    private Customer getCustomerFromDto(CustomerDto customerDto) {
        Role role = roleRepository.findByName(customerDto.getRole()).get();
        log.info("Role : "+ role);
        Customer customer = Customer.builder()
                .name(customerDto.getName())
                .surname(customerDto.getSurname())
                .username(customerDto.getUsername())
                .password(customerDto.getPassword())
                .nationalId(customerDto.getNationalId())
                .gender(customerDto.getGender())
                .dob(customerDto.getDob())
                .email(customerDto.getEmail())
                .address(customerDto.getAddress())
                .phoneNumber(customerDto.getPhoneNumber())
                .role(role)
                .status(Status.ACTIVE)
                .dateCreated(OffsetDateTime.now())
                .lastUpdated(OffsetDateTime.now())
                .build();
        return customerRepository.save(customer);

    }
}
