package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    private Session session;

    public UserDaoHibernateImpl() {
        session = Util.getSession();
    }


    @Override
    public void createUsersTable() {
        session.beginTransaction();
        session.createSQLQuery("CREATE TABLE users " +
                "(id BIGINT AUTO_INCREMENT PRIMARY KEY," +
                "name VARCHAR(255)," +
                "lastName VARCHAR (50)," +
                "age TINYINT)").executeUpdate();
        session.getTransaction().commit();
    }

    @Override
    public void dropUsersTable() {
        session.beginTransaction();
        session.createSQLQuery("DROP TABLE users").executeUpdate();
        session.getTransaction().commit();
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        session.beginTransaction();
        User user = new User(name, lastName, age);
        session.save(user);
        session.getTransaction().commit();
    }

    @Override
    public void removeUserById(long id) {
        User user = session.get(User.class, id);
        session.beginTransaction();
        session.delete(user);
        session.getTransaction().commit();
    }

    @Override
    public List<User> getAllUsers() {
        String sql = "SELECT name, lastName, age FROM users";
        Query<User> query = session.createSQLQuery(sql).addEntity(User.class);
        List<User> users = query.list();
        return users;
    }

    @Override
    public void cleanUsersTable() {
        try {
            session.beginTransaction();
            session.createSQLQuery("TRUNCATE TABLE users").executeUpdate();
            session.getTransaction().commit();
        } catch (NullPointerException ignored) {
        }
    }
}
