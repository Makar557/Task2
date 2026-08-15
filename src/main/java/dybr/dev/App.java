package dybr.dev;

import dybr.dev.console.UserConsole;
import dybr.dev.dao.UserDAO;
import dybr.dev.entity.UserEntity;
import dybr.dev.service.UserService;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class App {

    public static void main(String[] args) {

        Configuration configuration = new Configuration()
                .addAnnotatedClass(UserEntity.class);

        SessionFactory sessionFactory = configuration.buildSessionFactory();

        UserDAO userDAO = new UserDAO(sessionFactory);
        UserService userService = new UserService(userDAO);
        UserConsole console = new UserConsole(userService);

        console.run();
    }
}