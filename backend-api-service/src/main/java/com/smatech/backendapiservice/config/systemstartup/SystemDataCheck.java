package com.smatech.backendapiservice.config.systemstartup;

import com.smatech.backendapiservice.common.SystemConstants;
import com.smatech.backendapiservice.common.enums.Gender;
import com.smatech.backendapiservice.common.enums.Status;
import com.smatech.backendapiservice.common.response.CommonResponse;
import com.smatech.backendapiservice.domain.Role;
import com.smatech.backendapiservice.domain.UserEntity;
import com.smatech.backendapiservice.domain.dto.UserDto;
import com.smatech.backendapiservice.persistance.RoleRepository;
import com.smatech.backendapiservice.service.api.EmailService;
import com.smatech.backendapiservice.service.api.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.smatech.backendapiservice.common.SystemConstants.BASE_URL;
import static com.smatech.backendapiservice.common.Utility.generateNewUserEmailMessage;

@Slf4j
@Configuration
public class SystemDataCheck {

    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_EMAIL = "pridemoreviriri@gmail.com";
    private static final String TEMPORARY_PASSWORD = "123";

    @Bean
    public CommandLineRunner checkDefaultConfigs(
            Environment environment,
            RoleRepository roleRepository,
            UserService userService,
            PasswordEncoder passwordEncoder,
            EmailService emailService
    ) {
        return args -> {
            log.info("**** RUNNING SYSTEM CHECKS! ****");

            final String property = environment.getProperty("server.port");
            log.info("Im running on port : " + property);

            checkUserRoleConfiguration(roleRepository);

            checkAdminConfiguration(userService, roleRepository,passwordEncoder, emailService);

            log.info("**** SYSTEM READY ****");
        };
    }

    private void checkUserRoleConfiguration(RoleRepository roleRepository) {
        final List<Role> userRoles = roleRepository.findAll();

        if (userRoles.isEmpty()) {
            log.info("No roles defined in DB.");
            roleRepository.saveAll(RoleList.generateUserRoles());
            log.info("Generated User roles.");
        } else {
            log.info("Configured User roles Are:");
            userRoles.forEach(role -> {
                System.out.println(role.getName() + ",");
            });
        }
    }

    private void checkAdminConfiguration(UserService userService,
                                         RoleRepository roleRepository,
                                         PasswordEncoder passwordEncoder,
                                         EmailService emailService) {

        Optional<UserEntity> userOptional = userService.findByUsername(ADMIN_USERNAME);

        userOptional.ifPresent(user -> {
            log.info("Found user {}", user.getPhoneNumber());
        });

        userOptional.map(user -> {
            log.info("Super admin profile already initialized, skipping...");
            return user;
        }).orElseGet(() -> createAdminUser(userService,emailService));

    }

    private UserEntity createAdminUser(UserService userService,
                                       EmailService emailService
    ) {

        UserDto adminProfile = UserDto.builder()
                .name("Super")
                .surname("Admin")
                .password(TEMPORARY_PASSWORD)
                .username(ADMIN_USERNAME)
                .gender(Gender.MALE.name())
                .address("123 Smatech Company, Harare")
                .nationalId("07-348746B07")
                .phoneNumber("+263779046518")
                .email(ADMIN_EMAIL)
                .role("ADMIN")
                .dob(LocalDate.now())
                .build();

        CommonResponse response = userService.registerUser(adminProfile);
        UserEntity savedAdminProfile=(UserEntity)response.getResult();

        return savedAdminProfile;
    }


}
