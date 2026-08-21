package dybr.dev.dao;

import dybr.dev.entity.UserEntity;
import jakarta.persistence.EntityNotFoundException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.hibernate.cfg.Configuration;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class UserDAOTest {

    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:latest");

    private static SessionFactory sessionFactory;
    private static UserDAO userDAO;

    @BeforeAll
    static void setUp() {
        postgres.start();

        String url = postgres.getJdbcUrl();
        String username = postgres.getUsername();
        String password = postgres.getPassword();

        Configuration configuration = new Configuration();
        configuration.setProperty("hibernate.connection.url", url);
        configuration.setProperty("hibernate.connection.username", username);
        configuration.setProperty("hibernate.connection.password", password);
        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        configuration.setProperty("hibernate.hbm2ddl.auto", "create-drop");
        configuration.addAnnotatedClass(UserEntity.class);

        sessionFactory = configuration.buildSessionFactory();
        userDAO = new UserDAO(sessionFactory);
    }

    @AfterAll
    static void end() {
        sessionFactory.close();
        postgres.stop();
    }

    @BeforeEach
    void cleanDatabase() {
        Session session = sessionFactory.openSession();

        try {

            Transaction transaction = session.beginTransaction();

            session.createQuery("""
                            delete from UserEntity""")
                    .executeUpdate();
            transaction.commit();

        } finally {
            session.close();
        }
    }

    @Test
    void createUserShouldCreateUser() {

        UserEntity user = new UserEntity(
                "Makar",
                "makar@gmail.com",
                22
        );

        UserEntity result = userDAO.createUser(user);

        Optional<UserEntity> createdUser = userDAO.findById(result.getId());

        assertTrue(createdUser.isPresent());
    }

    @Test
    void findByIdShouldReturnUser() {

        UserEntity user = new UserEntity(
                "Makar",
                "makar@gmail.com",
                22
        );

        UserEntity createdUser = userDAO.createUser(user);

        Optional<UserEntity> result = userDAO.findById(createdUser.getId());

        assertTrue(result.isPresent());

    }

    @Test
    void findByIdShouldReturnEmptyWhenUserNotFound() {

        Optional<UserEntity> result = userDAO.findById(-1L);

        assertTrue(result.isEmpty());
    }

    @Test
    void findAllShouldReturnAllUsers() {

        List<UserEntity> users = List.of(new UserEntity(
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

        for (UserEntity u : users) {
            userDAO.createUser(u);
        }

        List<UserEntity> result = userDAO.findAll();

        assertEquals(result, users);
    }

    @Test
    void findAllShouldReturnEmptyWhenNoUsersExist() {

        List<UserEntity> result = userDAO.findAll();

        assertTrue(result.isEmpty());
    }

    @Test
    void updateShouldUpdateUser() {

        UserEntity user = new UserEntity(
                "Makar",
                "makar@gmail.com",
                22
        );

        UserEntity createdUser = userDAO.createUser(user);

        createdUser.setName("Ivan");

        UserEntity result = userDAO.update(createdUser);

        assertNotEquals(result.getName(), user.getName());
    }

    @Test
    void updateShouldThrowExceptionWhenIdIsNull() {

        UserEntity user = new UserEntity(
                "Makar",
                "makar@gmail.com",
                22
        );

        assertThrows(IllegalArgumentException.class, () -> userDAO.update(user));
    }

    @Test
    void updateShouldThrowExceptionWhenUserNotFound() {

        UserEntity user = new UserEntity(
                1L,
                "Makar",
                "makar@gmail.com",
                22
        );

        assertThrows(EntityNotFoundException.class, () -> userDAO.update(user));
    }


    @Test
    void deleteByIdShouldDeleteUser() {

        UserEntity user = new UserEntity(
                "Makar",
                "makar@gmail.com",
                22
        );

        UserEntity createdUser = userDAO.createUser(user);

        userDAO.deleteById(createdUser.getId());

        assertTrue(userDAO.findAll().isEmpty());
    }

    @Test
    void deleteByIdShouldThrowExceptionWhenUserNotFound() {
        assertThrows(EntityNotFoundException.class, () -> userDAO.deleteById(1L));
    }
}