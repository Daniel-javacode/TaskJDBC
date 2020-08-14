package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.PreparedStatement;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    private Session session;

    public UserDaoHibernateImpl() {
        session = Util.getSession();
    }


    @Override
    public void createUsersTable() {
        Transaction tr = session.beginTransaction();
        session.createSQLQuery("CREATE TABLE users " +
                "(id BIGINT AUTO_INCREMENT PRIMARY KEY," +
                "name VARCHAR(255)," +
                "lastName VARCHAR (50)," +
                "age TINYINT)").executeUpdate();
        tr.commit();
    }

    @Override
    public void dropUsersTable() {
        Transaction tr = session.beginTransaction();
        session.createSQLQuery("DROP TABLE IF EXISTS users").executeUpdate();
        tr.commit();
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction tr = session.beginTransaction();
        User user = new User(name, lastName, age);
        session.save(user);
        tr.commit();
    }

    @Override
    public void removeUserById(long id) {
        User user = session.get(User.class, id);
        Transaction tr = session.beginTransaction();
        session.delete(user);
        tr.commit();
    }

    @Override
    public List<User> getAllUsers() {
        return session.createQuery("SELECT a FROM User a", User.class).getResultList();
    }

    @Override
    public void cleanUsersTable() {
        try {
            Transaction tr = session.beginTransaction();
            session.createSQLQuery("DELETE FROM users").executeUpdate();
            tr.commit();
        } catch (NullPointerException ignored) {
        }
    }
}
