package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private Connection connection = null;
    private Statement statement = null;

    public UserDaoJDBCImpl() {
        try {
            connection = Util.getConnection();
            statement = Util.getStatement();
        } catch (SQLException | ClassNotFoundException ignored) {

        }
    }

    public void createUsersTable() {
        try {
            statement.executeUpdate("CREATE TABLE users " +
                    "(id BIGINT AUTO_INCREMENT PRIMARY KEY," +
                    "name VARCHAR(255)," +
                    "lastName VARCHAR (50)," +
                    "age TINYINT)");
        } catch (SQLException ignored) {
        }
    }

    public void dropUsersTable() {
        try {
            statement.executeUpdate("DROP TABLE users");
        } catch (SQLException ignored) {
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (Connection connection = new Util().getConnection()) {
            String sql = "INSERT users(name, lastName, age) VALUES(?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
        } catch (SQLException | ClassNotFoundException ignored) {
        }

    }

    public void removeUserById(long id) {
        try (Connection connection = new Util().getConnection()) {
            Statement statement = connection.createStatement();
            statement.executeUpdate("DELETE FROM users WHERE id = id");
        } catch (SQLException | ClassNotFoundException ignored) {
        }
    }

    public List<User> getAllUsers() {
        List<User> result = new ArrayList<User>();
        try (Connection connection = new Util().getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("select name, lastName, age from users");
            while (rs.next()) {
                result.add(new User(rs.getString(2), rs.getString(3), rs.getByte(4)));
            }
            return result;
        } catch (SQLException | ClassNotFoundException ignored) {
        }
        return null;
    }

    public void cleanUsersTable() {
        try (Connection connection = new Util().getConnection()) {
            Statement statement = connection.createStatement();
            statement.executeUpdate("TRUNCATE TABLE users");
        } catch (SQLException | ClassNotFoundException ignored) {
        }
    }
}
