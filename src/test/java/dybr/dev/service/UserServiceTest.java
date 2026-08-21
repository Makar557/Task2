package dybr.dev.service;

import dybr.dev.dao.UserDAO;
import dybr.dev.entity.UserEntity;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Test
    void findByIdShouldReturnUser() {

        UserDAO userDAO = mock(UserDAO.class);

        UserService userService = new UserService(userDAO);

        UserEntity user = new UserEntity(
                "Makar",
                "makar@gmail.com",
                22
        );

        when(userDAO.findById(1L))
                .thenReturn(Optional.of(user));

        Optional<UserEntity> result = userService.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(user, result.get());

        verify(userDAO).findById(1L);
    }

    @Test
    void findAllShouldReturnListOfUser() {

        UserDAO userDAO = mock(UserDAO.class);

        UserService userService = new UserService(userDAO);

        List<UserEntity> user = List.of(new UserEntity(
                "Makar",
                "makar@gmail.com",
                22
        ),
                new UserEntity(
                        "Ivan",
                        "ivan@gmail.com",
                        19
                )
        );

        when(userDAO.findAll())
                .thenReturn(user);

        List<UserEntity> result = userService.findAll();

        assertEquals(user, result);

        verify(userDAO).findAll();
    }

    @Test
    void deleteByIdShouldDeleteUser() {

        UserDAO userDAO = mock(UserDAO.class);

        UserService userService = new UserService(userDAO);

        UserEntity user = new UserEntity(
                "Makar",
                "makar@gmail.com",
                22
        );

        when(userDAO.deleteById(1L))
                .thenReturn(user);

        UserEntity result = userService.deleteById(1L);

        assertEquals(user, result);

        verify(userDAO, times(1)).deleteById(1L);
    }

    @Test
    void createUserShouldCreateUser() {

        UserDAO userDAO = mock(UserDAO.class);

        UserService userService = new UserService(userDAO);

        UserEntity user = new UserEntity(
                "Makar",
                "makar@gmail.com",
                22
        );

        when(userDAO.createUser(user))
                .thenReturn(user);

        UserEntity result = userService.createUser(user);


        assertEquals(user, result);

        verify(userDAO, times(1)).createUser(user);
    }

    @Test
    void updateShouldUpdateUser() {

        UserDAO userDAO = mock(UserDAO.class);

        UserService userService = new UserService(userDAO);

        UserEntity user = new UserEntity(
                1L,
                "Makar",
                "makar@gmail.com",
                22
        );

        when(userDAO.update(user))
                .thenReturn(user);

        UserEntity result = userService.update(user);

        assertEquals(user, result);

        verify(userDAO, times(1)).update(user);
    }

    @Test
    void createUserShouldThrowExceptionWhenIdIsNotNull() {

        UserDAO userDAO = mock(UserDAO.class);

        UserService userService = new UserService(userDAO);

        UserEntity user = new UserEntity(
                1L,
                "Makar",
                "makar@gmail.com",
                12
        );

        assertThrows(IllegalArgumentException.class, () -> userService.createUser(user));

        verify(userDAO, times(0)).createUser(user);
    }

    @Test
    void updateUserShouldThrowExceptionWhenIdIsNull() {

        UserDAO userDAO = mock(UserDAO.class);

        UserService userService = new UserService(userDAO);

        UserEntity user = new UserEntity(
                "Makar",
                "makar@gmail.com",
                12
        );

        assertThrows(IllegalArgumentException.class, () -> userService.update(user));

        verify(userDAO, times(0)).update(user);
    }

    @Test
    void createUserShouldThrowExceptionWhenNameIsNull() {

        UserDAO userDAO = mock(UserDAO.class);

        UserService userService = new UserService(userDAO);

        UserEntity user = new UserEntity(
                null,
                "makar@gmail.com",
                22
        );

        assertThrows(IllegalArgumentException.class, () -> userService.createUser(user));

        verify(userDAO, times(0)).createUser(user);
    }

    @Test
    void createUserShouldThrowExceptionWhenNameIsBlank() {

        UserDAO userDAO = mock(UserDAO.class);

        UserService userService = new UserService(userDAO);

        UserEntity user = new UserEntity(
                "",
                "makar@gmail.com",
                22
        );

        assertThrows(IllegalArgumentException.class, () -> userService.createUser(user));

        verify(userDAO, times(0)).createUser(user);
    }

    @Test
    void createUserShouldThrowExceptionWhenNameStartsWithLowercase() {

        UserDAO userDAO = mock(UserDAO.class);

        UserService userService = new UserService(userDAO);

        UserEntity user = new UserEntity(
                "makar",
                "makar@gmail.com",
                22
        );

        assertThrows(IllegalArgumentException.class, () -> userService.createUser(user));

        verify(userDAO, times(0)).createUser(user);
    }

    @Test
    void createUserShouldThrowExceptionWhenNameContainsNonLetterCharacters() {

        UserDAO userDAO = mock(UserDAO.class);

        UserService userService = new UserService(userDAO);

        UserEntity user = new UserEntity(
                "Makar123",
                "makar@gmail.com",
                22
        );

        assertThrows(IllegalArgumentException.class, () -> userService.createUser(user));

        verify(userDAO, times(0)).createUser(user);
    }

    @Test
    void createUserShouldThrowExceptionWhenAgeIsLessThan13() {

        UserDAO userDAO = mock(UserDAO.class);

        UserService userService = new UserService(userDAO);

        UserEntity user = new UserEntity(
                "Makar",
                "makar@gmail.com",
                11
        );

        assertThrows(IllegalArgumentException.class, () -> userService.createUser(user));

        verify(userDAO, times(0)).createUser(user);
    }

    @Test
    void createUserShouldThrowExceptionWhenAgeIsGreaterThan110() {

        UserDAO userDAO = mock(UserDAO.class);

        UserService userService = new UserService(userDAO);

        UserEntity user = new UserEntity(
                "Makar",
                "makar@gmail.com",
                111
        );

        assertThrows(IllegalArgumentException.class, () -> userService.createUser(user));

        verify(userDAO, times(0)).createUser(user);
    }

    @Test
    void createUserShouldThrowExceptionWhenEmailDoesNotContainAtSymbol() {

        UserDAO userDAO = mock(UserDAO.class);

        UserService userService = new UserService(userDAO);

        UserEntity user = new UserEntity(
                "Makar",
                "makargmail.com",
                23
        );

        assertThrows(IllegalArgumentException.class, () -> userService.createUser(user));

        verify(userDAO, times(0)).createUser(user);
    }

    @Test
    void createUserShouldThrowExceptionWhenEmailHasInvalidDomain() {

        UserDAO userDAO = mock(UserDAO.class);

        UserService userService = new UserService(userDAO);

        UserEntity user = new UserEntity(
                "Makar",
                "makar@gmail.",
                23
        );

        assertThrows(IllegalArgumentException.class, () -> userService.createUser(user));

        verify(userDAO, times(0)).createUser(user);
    }

    @Test
    void createUserShouldThrowExceptionWhenEmailDoesNotContainDot() {

        UserDAO userDAO = mock(UserDAO.class);

        UserService userService = new UserService(userDAO);

        UserEntity user = new UserEntity(
                "Makar",
                "makar@gmailcom",
                23
        );

        assertThrows(IllegalArgumentException.class, () -> userService.createUser(user));

        verify(userDAO, times(0)).createUser(user);
    }
}