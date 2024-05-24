package com.smatech.backendapiservice.config.systemstartup;

import com.smatech.backendapiservice.common.SystemConstants;
import com.smatech.backendapiservice.common.enums.Gender;
import com.smatech.backendapiservice.common.enums.Status;
import com.smatech.backendapiservice.domain.Role;
import com.smatech.backendapiservice.domain.UserEntity;
import com.smatech.backendapiservice.persistance.RoleRepository;
import com.smatech.backendapiservice.persistance.UserRepository;
import com.smatech.backendapiservice.service.api.EmailService;
import com.smatech.backendapiservice.service.api.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
            UserRepository userRepository,
            UserService userService,
            PasswordEncoder passwordEncoder,
            EmailService emailService
    ) {
        return args -> {
            log.info("**** RUNNING SYSTEM CHECKS! ****");

            final String property = environment.getProperty("server.port");
            log.info("Im running on port : " + property);

            checkUserRoleConfiguration(roleRepository);

            checkAdminConfiguration(userRepository, roleRepository, userService, passwordEncoder, emailService);

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

    private void checkAdminConfiguration(UserRepository userRepository,
                                         RoleRepository roleRepository,
                                         UserService userService,
                                         PasswordEncoder passwordEncoder,
                                         EmailService emailService) {

        Optional<UserEntity> userOptional = userService.findByUsername(ADMIN_USERNAME);

        userOptional.ifPresent(user -> {
            log.info("Found user {}", user.getPhoneNumber());
        });

        userOptional.map(user -> {
            log.info("Super admin profile already initialized, skipping...");
            return user;
        }).orElseGet(() -> createAdminUser(userRepository, roleRepository,
                emailService, passwordEncoder));

    }

    private UserEntity createAdminUser(UserRepository userRepository,
                                       RoleRepository roleRepository,
                                       EmailService emailService,
                                       PasswordEncoder passwordEncoder
    ) {

        Optional<Role> optionalRole = roleRepository.findByName("ADMIN");
        UserEntity adminProfile = UserEntity.builder()
                .name("Super")
                .surname("Admin")
                .username(ADMIN_USERNAME)
                .gender(Gender.MALE)
                .address("123 Smatech Company, Harare")
                .nationalId("07-348746B07")
                .phoneNumber("+263779046518")
                .email(ADMIN_EMAIL)
                .activationToken(UUID.randomUUID().toString())
                .dob(LocalDate.now())
                .dateCreated(OffsetDateTime.now())
                .lastUpdated(OffsetDateTime.now())
                .status(Status.ACTIVE)
                .build();

        optionalRole.ifPresent(role -> adminProfile.setRoles(Arrays.asList(role)));

        adminProfile.setPassword(passwordEncoder.encode(TEMPORARY_PASSWORD));

        UserEntity savedAdminProfile = userRepository.save(adminProfile);
        log.info("Admin ID Generated:: {}", savedAdminProfile.getUserId());

        log.info("Temporary Password: {}", TEMPORARY_PASSWORD);
        String link= BASE_URL+"/api/customers/activate?token=" + adminProfile.getActivationToken();

        //Send email with password
        try {
            emailService.sendEmail(adminProfile.getEmail(),
                    SystemConstants.NEW_USER_EMAIL_SUBJECT,
                    generateNewUserEmailMessage(adminProfile.getUsername(), TEMPORARY_PASSWORD,link));
        } catch (Exception e) {
            log.info("An error occurred sending email: {}", e.getMessage());
        }
        return adminProfile;
    }


}
