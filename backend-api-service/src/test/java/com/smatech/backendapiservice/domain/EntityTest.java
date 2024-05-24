package com.smatech.backendapiservice.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class EntityTest {
    @Test
    @DisplayName("Test Role Entity")
    void testRoleUserList(){
        UserEntity user=new UserEntity();
        Role roleMock=mock(Role.class);

        assertThat(user.getRoles().isEmpty());
        user.getRoles().add(roleMock);
        assertThat(user.getRoles()).hasSize(1);

    }


}
