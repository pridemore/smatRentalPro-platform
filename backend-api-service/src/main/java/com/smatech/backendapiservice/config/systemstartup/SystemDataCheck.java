package com.smatech.backendapiservice.config.systemstartup;

import com.smatech.backendapiservice.domain.Role;
import com.smatech.backendapiservice.persistance.RoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.List;

@Slf4j
@Configuration
public class SystemDataCheck {

    @Bean
    public CommandLineRunner checkDefaultConfigs(
            Environment environment,
            RoleRepository roleRepository
    ){
       return args -> {
           log.info("**** RUNNING SYSTEM CHECKS! ****");

           final String property = environment.getProperty("server.port");
           log.info("Im running on port : " + property);

           checkUserRoleConfiguration(roleRepository);

           log.info("**** SYSTEM READY ****");
       };
    }

    private void checkUserRoleConfiguration(RoleRepository roleRepository) {
        final List<Role> userRoles = roleRepository.findAll();

        if(userRoles.isEmpty()){
            log.info("No roles defined in DB.");
            roleRepository.saveAll(RoleList.generateUserRoles());
            log.info("Generated User roles.");
        }else {
            log.info("Configured User roles Are:");
            userRoles.forEach(role -> {
                System.out.println(role.getName() + ",");
            });
        }
    }
}
