package deanoffice.noentities;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class UsersTableDataTest {
    @Test
    public void getAllUsersTest(){
        String username = "tutor0";
        UsersTableData usersTableData = new UsersTableData();
        ArrayList<User> username2 = usersTableData.getAllUsers();
        Assert.assertEquals(username, username2.get(2).getUsername());
    }
}
