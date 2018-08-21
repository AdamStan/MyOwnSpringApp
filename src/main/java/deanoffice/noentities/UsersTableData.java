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
            rs = stmt.executeQuery("SELECT * FROM users"); // there will be join
            while ( rs.next() ) {
                User u = new User();
                u.setUsername(rs.getString("username"));
                u.setPassword(rs.getString("password"));
                users.add(u);
                System.out.println(u);
            }
            conn.close();
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        } finally {
            return users;
        }
    }
    public User findUserByUsername(String name) throws Exception {
        throw new Exception("NOT DECLARATED YET");
    }
}
