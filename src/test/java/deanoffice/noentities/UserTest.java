package deanoffice.noentities;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserTest {
    @Test
    public void encodePasswordTest(){
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        for (int i = 0; i < 5; i++) {
            // "123456" - plain text - user input from user interface
            String passwd = encoder.encode("123456");
            // passwd - password from database
            System.out.println(passwd); // print hash
            // true for all 5 iteration
            System.out.println(encoder.matches("123456", passwd));
            Assert.assertTrue(encoder.matches("123456", passwd));
        }
    }
}
