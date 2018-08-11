package deanoffice.repositories;

import deanoffice.entities.Student;
import org.springframework.data.repository.CrudRepository;

public interface StudentRepository extends CrudRepository<Student, Integer> {
    Student findByIndexNumber(Integer indexNumber);
    Student findBySurname(String surname);
    Student findByusername(String username);
}
