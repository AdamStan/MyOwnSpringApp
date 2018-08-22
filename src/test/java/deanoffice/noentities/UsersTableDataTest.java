package deanoffice.noentities;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class UsersTableDataTest {
    @Test
    public void getAllUsersTest(){
        UsersTableData usersTableData = new UsersTableData();
        ArrayList<User> username2 = usersTableData.getAllUsers();
        Assert.assertEquals("tutor0", username2.get(2).getUsername());
    }
    @Test
    public void getUserByUsernameTest(){
        User user = new UsersTableData().findUserByUsername("student0");
        Assert.assertEquals("student0", user.getUsername());
    }
}
