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
        } catch (SQLException ignored) {

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
        try {
            String query = "INSERT INTO users (name, lastName, age) VALUES (?, ?, ?)";
            PreparedStatement prepStmt = connection.prepareStatement(query);
            prepStmt.setString(1, name);
            prepStmt.setString(2, lastName);
            prepStmt.setByte(3, age);
            prepStmt.execute();
        } catch (SQLException ignored) {
        }
    }

    public void removeUserById(long id) {
        try {
            statement.executeUpdate("DELETE FROM users WHERE id = id");
        } catch (SQLException ignored) {
        }
    }

    public List<User> getAllUsers() {
        List<User> result = new ArrayList<>();
        try {
            ResultSet rs = statement.executeQuery("SELECT * FROM users");
            while (rs.next()) {
                result.add(new User(rs.getString(2), rs.getString(3), rs.getByte(4)));
            }
            return result;
        } catch (SQLException ignored) {
        }
        return null;
    }

    public void cleanUsersTable() {
        try {
            statement.executeUpdate("TRUNCATE TABLE users");
        } catch (SQLException ignored) {
        }
    }
}
