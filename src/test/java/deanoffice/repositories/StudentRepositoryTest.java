package deanoffice.repositories;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StudentRepositoryTest {
    @Autowired
    private StudentRepository studentRepository;
    @Before
    public void setUp(){

    }
    @Test
    public void testFindUsername(){

    }
    @Test
    public void testFindSurname(){

    }
    @Test
    public void testFindIndexNumber(){

    }
    @Test
    public void testFindAll(){

    }
}
