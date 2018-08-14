package deanoffice.repositories;

import deanoffice.entities.Faculty;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FacultyRepositoryTest {
    @Autowired
    private FacultyRepository facultyRepository;
    @Before
    public void setUp(){
        Faculty faculty1 = new Faculty(
                "WEEIA",
                "Wydzial Elektroniki, Elektrotechniki Inforamtyki i Automatyki"
        );
        Faculty faculty2 = new Faculty(
                "WBINOŻ",
                "Wydział Biotechnologii i Nauk o Żywności"
        );
        Assert.assertNull(faculty1.getId());
        Assert.assertNull(faculty2.getId());

        facultyRepository.save(faculty1);
        facultyRepository.save(faculty2);

        Assert.assertNotNull(faculty1.getId());
        Assert.assertNotNull(faculty2.getId());
    }
    @Test
    public void testFetchData(){
        Faculty faculty = facultyRepository.findByName("WEEIA");
        System.out.println(faculty);
        Assert.assertNotNull(faculty);

        Iterable<Faculty> faculties = facultyRepository.findAll();
        for(Faculty f : faculties){
            Assert.assertNotNull(f);
            System.out.println(f);
        }
    }
}
