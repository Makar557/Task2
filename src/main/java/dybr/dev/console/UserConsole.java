package dybr.dev.console;

import dybr.dev.entity.UserEntity;
import dybr.dev.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import org.slf4j.Logger;

public class UserConsole {

    private final UserService userService;
    private final Menu menu;
    private final Scanner scanner = new Scanner(System.in);
    private static final Logger logger = LoggerFactory.getLogger(UserConsole.class);

    public UserConsole(UserService userService) {
        this.userService = userService;
        menu = new Menu();
    }

    public void run() {

        logger.info("Программа запущена");

        while (true) {

            System.out.println("Введите команду или info, чтобы узнать список команд:");

            Optional<Command> command = menu.userInput();

            if (command.isEmpty()) {
                System.out.println("Неправильная команда");
                continue;
            }

            switch (command.get()) {
                case info -> menu.printBasicCommands();
                case CREATE_USER -> createUser();
                case FIND_USER_BY_ID -> findById();
                case FIND_ALL_USERS -> findAll();
                case UPDATE_USER -> update();
                case DELETE_USER -> deleteById();
                case EXIT -> {
                    logger.info("Программа завершилась");
                    return;
                }
            }
        }
    }

    private void createUser() {

        System.out.println("Введите имя:");
        String name = scanner.nextLine();

        System.out.println("Введите email:");
        String email = scanner.nextLine();

        System.out.println("Введите возраст:");
        int age = readInt();

        UserEntity user = new UserEntity(name, email, age);

        try {
            System.out.println(userService.createUser(user));
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        } catch (RuntimeException e) {
            System.out.println("Что-то пошло не так ((");
        }

    }

    private void findById() {

        try {
            System.out.println("Введите id:");
            long id = readLong();

            Optional<UserEntity> user = userService.findById(id);

            if (user.isEmpty()) {
                System.out.println("Такого пользователя нет, попробуйте еще раз ((");
            } else {
                System.out.println(user.get());
            }
        } catch (RuntimeException e) {
            logger.error("Ошибка при получении пользователя");
            System.out.println("Что-то пошло не так ((");
        }

    }

    private void findAll() {

        try {
            List<UserEntity> users = userService.findAll();
            if (users.isEmpty()) {
                System.out.println("Пользователей нет");
            } else {
                users.forEach(System.out::println);
            }
        } catch (RuntimeException e) {
            logger.error("Ошибка при получении пользователей", e);
            System.out.println("Что-то пошло не так ((");
        }
    }

    private void update() {

        System.out.println("Введите id:");
        long id = readLong();

        System.out.println("Введите имя:");
        String name = scanner.nextLine();

        System.out.println("Введите email:");
        String email = scanner.nextLine();

        System.out.println("Введите возраст:");
        int age = readInt();

        UserEntity user = new UserEntity(id, name, email, age);

        try {
            System.out.println(userService.update(user));
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage() + " выберите пользователя и попробуйте еще раз");
        } catch (EntityNotFoundException e) {
            System.out.println(" выберите существующего пользователя и попробуйте еще раз");
        } catch (RuntimeException e) {
            System.out.println("Что-то пошло нет ((");
        }
    }

    private void deleteById() {

        System.out.println("Введите id:");
        long id = readLong();

        try {
            System.out.println(userService.deleteById(id));
        } catch (EntityNotFoundException e) {
            System.out.println(" выберите существующего пользователя и попробуйте еще раз");
        } catch (RuntimeException e) {
            System.out.println("Что-то пошло нет ((");
        }
    }

    private int readInt() {

        while (true) {

            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Введите целое число:");
            }
        }
    }

    private long readLong() {

        while (true) {

            try {
                return Long.parseLong(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Введите корректный id:");
            }
        }
    }
}