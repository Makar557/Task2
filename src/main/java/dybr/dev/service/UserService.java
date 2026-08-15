package dybr.dev.service;

import dybr.dev.dao.UserDAO;
import dybr.dev.entity.UserEntity;

import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserService {

    private final UserDAO userDAO;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public UserEntity createUser(UserEntity user) {

        if (user.getId() != null) {
            IllegalArgumentException e = new IllegalArgumentException("id должен быть пустым");
            logger.warn("Пользователь передан с id={}", user.getId());
            throw e;
        }

        nameValidate(user.getName());
        ageValidate(user.getAge());
        emailValidate(user.getEmail());

        return userDAO.createUser(user);
    }

    public Optional<UserEntity> findById(Long id) {
        return userDAO.findById(id);
    }

    public List<UserEntity> findAll() {
        return userDAO.findAll();
    }

    public UserEntity update(UserEntity user) {

        if (user.getId() == null) {
            IllegalArgumentException e = new IllegalArgumentException("не передан id пользователя");
            logger.warn("Пользователь передан с id={}", user.getId());
            throw e;
        }

        nameValidate(user.getName());
        ageValidate(user.getAge());
        emailValidate(user.getEmail());

        return userDAO.update(user);
    }

    public UserEntity deleteById(Long id) {
        return userDAO.deleteById(id);
    }

    private void ageValidate(int age) {
        if(age < 13 || age > 110) {
            IllegalArgumentException e = new IllegalArgumentException("указан некорректный возраст ");
            logger.warn("Пользователь с возростом age={} не может быть создан", age);
            throw e;
        }
    }

    private void nameValidate(String name) {

        if (name == null || name.isBlank()) {
            IllegalArgumentException e = new IllegalArgumentException("имя не может быть пустым ");
            logger.warn("Пользователь с именем name={} не может быть создан", name);
            throw e;
        }

        char[] stringToChar = name.toCharArray();

        if(Character.isLowerCase(stringToChar[0])) {
            IllegalArgumentException e = new IllegalArgumentException("имя должно начинаться с заглавной буквы ");
            logger.warn("Пользователь с именем name={} не может быть создан", name);
            throw e;
        }
        for (char letter : name.toCharArray()) {
            if (!Character.isLetter(letter)) {
                IllegalArgumentException e = new IllegalArgumentException("имя должно состоять из букв ");
                logger.warn("Пользователь с именем name={} не может быть создан", name);
                throw e;
            }
        }
    }

    private void emailValidate(String email) {

        if (email == null || !email.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            IllegalArgumentException e = new IllegalArgumentException("Некорректный email ");
            logger.warn("Пользователь с почтой email={} не может быть создан", email);
            throw e;
        }
    }
}
