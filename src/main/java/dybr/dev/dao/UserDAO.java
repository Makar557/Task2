package dybr.dev.dao;

import dybr.dev.entity.UserEntity;
import jakarta.persistence.EntityNotFoundException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

public class UserDAO {

    private final SessionFactory sessionFactory;
    private static final Logger logger = LoggerFactory.getLogger(UserDAO.class);

    public UserDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public UserEntity createUser(UserEntity user) {

        logger.info("Метод createUser в UserDAO запущен");

        Session session = sessionFactory.openSession();

        Transaction transaction = session.beginTransaction();

        try {
            session.persist(user);
            transaction.commit();
        } catch (RuntimeException e) {
            transaction.rollback();
            logger.error("Ошибка при создании пользователя", e);
            throw e;
        } finally {
            session.close();
        }

        logger.info("Метод createUser в UserDAO завершен без ошибок");

        return user;
    }

    public Optional<UserEntity> findById(Long id) {

        logger.info("Метод findById в UserDAO запущен");

        Session session = sessionFactory.openSession();

        Optional<UserEntity> user;

        try {
            user = Optional.ofNullable(session.find(UserEntity.class, id));
        } finally {
            session.close();
        }

        logger.info("Метод findById в UserDAO завершен без ошибок");

        return user;
    }

    public List<UserEntity> findAll() {

        logger.info("Метод findAll в UserDAO запущен");

        Session session = sessionFactory.openSession();

        List<UserEntity> users;
        try {
            users = session.createQuery(
                    """
                            select u
                            from UserEntity u
                            """,
                    UserEntity.class
            ).getResultList();
        } finally {
            session.close();
        }

        logger.info("Метод findAll в UserDAO завершен без ошибок");

        return users;
    }

    public UserEntity update(UserEntity user) {

        logger.info("Метод update в UserDAO запущен");

        Session session = sessionFactory.openSession();

        Transaction transaction = session.beginTransaction();

        UserEntity newUser;

        try {

            newUser = session.find(UserEntity.class, user.getId());

            if (newUser == null) {
                throw new EntityNotFoundException();
            }

            newUser.setAge(user.getAge());
            newUser.setName(user.getName());
            newUser.setEmail(user.getEmail());

            transaction.commit();

        } catch (EntityNotFoundException e) {
            transaction.rollback();
            logger.warn("Несуществующий пользователь с id={}", user.getId());
            throw e;
        } catch (RuntimeException e) {
            transaction.rollback();
            logger.error("Произошла ошибка при попытке сохранить пользователя с id={}", user.getId(), e);
            throw e;
        } finally {
            session.close();
        }

        logger.info("Метод update в UserDAO завершен без ошибок");

        return newUser;
    }

    public UserEntity deleteById(Long id) {

        logger.info("Метод deleteById в UserDAO запущен");

        Session session = sessionFactory.openSession();

        Transaction transaction = session.beginTransaction();

        UserEntity deleteUser;

        try {
            deleteUser = session.find(UserEntity.class, id);

            if (deleteUser == null) {
                throw new EntityNotFoundException();
            }

            session.remove(deleteUser);
            transaction.commit();
        } catch (EntityNotFoundException e) {
            transaction.rollback();
            logger.warn("Несуществующий пользователь с id={}", id);
            throw e;
        } catch (RuntimeException e) {
            transaction.rollback();
            logger.error("Произошла ошибка при попытке удалить пользователя с id={}", id, e);
            throw e;
        } finally {
            session.close();
        }

        logger.info("Метод deleteById в UserDAO завершен без ошибок");

        return deleteUser;
    }
}