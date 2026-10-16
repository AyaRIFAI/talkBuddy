package io.lingua.talkbuddy.service;

import io.lingua.talkbuddy.entity.UserEntity;
import io.lingua.talkbuddy.model.User;
import io.lingua.talkbuddy.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

// dans src/test/java/io/lingua/talkbuddy/service/
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;  // faux repository

    @InjectMocks
    private UserService userService;  // vrai service avec le faux repo injecté

    @Test
    void createUser_utilisateurValide_retourneUserAvecId() {
        // ARRANGE
        User user = new User("raya", "raya@gmail.com", "Espagnol", "Français");

        UserEntity userEntitySaved = new UserEntity("raya", "raya@gmail.com", "Espagnol", "Français");
        userEntitySaved.setUserId(1L);

        when(userRepository.save(any())).thenReturn(userEntitySaved);

        // ACT
        User userSaved = userService.createUser(user);

        // ASSERT
        assertNotNull(userSaved.getUserId());
        assertEquals(1L, userSaved.getUserId());
        assertEquals("raya", userSaved.getName());
    }
    @Test
    void createUser_BDDException() {
        // ARRANGE
        User user = new User("raya", "raya@gmail.com", "Espagnol", "Français");
        when(userRepository.save(any())).thenThrow(new RuntimeException("BDD indisponible"));
        // ACT
        assertThrows(RuntimeException.class, () -> userService.createUser(user));
    }

}