package com.smatech.backendapiservice.config.systemstartup;

import com.smatech.backendapiservice.domain.Role;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class RoleList {
    public static List<Role> generateUserRoles() {

        final List<Role> userRoles = Arrays.asList(
                new Role("ADMIN"),
                new Role("USER"),
                new Role("CUSTOMER")
        );

        return new ArrayList<>(Collections.unmodifiableList(userRoles));
    }
}
