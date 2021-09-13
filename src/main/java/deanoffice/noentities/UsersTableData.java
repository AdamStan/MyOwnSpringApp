package deanoffice.noentities;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class UsersTableData {
    private static final Logger log = Logger.getLogger(UsersTableData.class.getName());

    @Value("${spring.datasource.url}")
    private String base_url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    public ArrayList<User> getAllUsers() {
        ArrayList<User> users = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(getUrl())) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT u.username, u.password, u.enabled, a.authority FROM users u " +
                    "LEFT JOIN authorities a ON a.username = u.username");
            while (rs.next()) {
                users.add(getUserFromResultSet(rs));
            }
        } catch (Exception e) {
            log.info("Got an exception! ");
            log.log(Level.FINEST, e.getMessage());
        }
        return users;
    }

    public User findUserByUsername(String name) {
        User user = new User();
        try (Connection conn = DriverManager.getConnection(getUrl())) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT u.username, u.password, u.enabled, a.authority FROM users u " +
                    "LEFT JOIN authorities a ON a.username = u.username" +
                    " where u.username = '" + name + "'");
            rs.next();
            user = getUserFromResultSet(rs);
        } catch (Exception e) {
            log.info("Got an exception! ");
            log.log(Level.FINEST, e.getMessage());
        }
        return user;
    }

    public void deleteUserByUsername(String username) {
        try (Connection conn = DriverManager.getConnection(getUrl())) {
            Statement stmt = conn.createStatement();
            stmt.execute(" delete from authorities where username = '" +
                    username + "'; " + "delete from users " +
                    " where username = '" + username + "' ");
        } catch (Exception e) {
            log.info("Got an exception! ");
            log.log(Level.FINEST, e.getMessage());
        }
    }

    public void insertUser(String username, String password, String role, String enable) {
        int en = 0;
        if (enable != null && enable.equals("on")) {
            en = 1;
        }

        if (password.length() < 42) {
            password = User.encodePassword(password);
        }

        try (Connection conn = DriverManager.getConnection(getUrl())) {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("insert into users (username, password, enabled) " +
                    "values ('" +
                    username + "', '" + password + "', " + en + ");");
            stmt.executeUpdate("insert into authorities values ('" + username + "', '" + role + "');");
        } catch (Exception e) {
            log.info("Got an exception! ");
            log.log(Level.FINEST, e.getMessage());
        }
    }

    public void updateUser(String username, String password,
                           String role, String enable) {
        int en = 0;
        if (enable.equals("on")) {
            en = 1;
        }
        password = User.encodePassword(password);
        try (Connection conn = DriverManager.getConnection(getUrl())) {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("update users set password = '" + password + "', enabled = " + en + " " +
                    "where username = '" + username + "'; ");
            stmt.executeUpdate("update authorities set authority = '" + role + "'" +
                    " where username = '" + username + "'");
        } catch (Exception e) {
            log.info("Got an exception! ");
            log.log(Level.FINEST, e.getMessage());
        }
    }

    private User getUserFromResultSet(ResultSet rs) throws SQLException {
        User user = new User();
        user.setUsername(rs.getString("username"));
        user.setPassword(rs.getString("password"));
        user.setEnable(rs.getBoolean("enabled"));
        user.setRole(rs.getString("authority"));
        return user;
    }

    private String getUrl() {
        return String.format("%s;user=%s;password=%s", base_url, username, password);
    }
}
