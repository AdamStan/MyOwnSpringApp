package deanoffice.noentities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class UsersTableData {
    private static String url = "jdbc:sqlserver://DESKTOP-8G4C4MF:1433;databaseName=DeanOffice;user=sa;password=praktyka";

    public ArrayList<User> getAllUsers(){
        ArrayList<User> users = new ArrayList<>();
        try {
            Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            ResultSet rs;
            rs = stmt.executeQuery("SELECT u.username, u.password, u.enabled, a.authority FROM users u " +
                    "LEFT JOIN authorities a ON a.username = u.username");
            while ( rs.next() ) {
                User u = new User();
                u.setUsername(rs.getString("username"));
                u.setPassword(rs.getString("password"));
                u.setEnable(rs.getBoolean("enabled"));
                u.setRole(rs.getString("authority"));
                users.add(u);
            }
            conn.close();
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        } finally {
            return users;
        }
    }
    public User findUserByUsername(String name){
        User user = new User();
        try {
            Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            ResultSet rs;
            rs = stmt.executeQuery("SELECT u.username, u.password, u.enabled, a.authority FROM users u " +
                    "LEFT JOIN authorities a ON a.username = u.username" +
                    " where u.username = '" + name + "'");
            rs.next();
            user.setUsername(rs.getString("username"));
            user.setPassword(rs.getString("password"));
            user.setEnable(rs.getBoolean("enabled"));
            user.setRole(rs.getString("authority"));
            conn.close();
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        } finally {
            return user;
        }
    }
    public void deleteUser(String username){
        try {
            Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            stmt.execute("delete from users " +
                    " where u.username = '" + username + "' " +
                    " delete from authorities where username = '" +
                    username +
                    "'");
            conn.close();
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
    }
}
