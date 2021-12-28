package deanoffice.repositories;

import deanoffice.entities.Student;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface StudentRepository extends CrudRepository<Student, Integer> {
    Student findByIndexNumber(Integer indexNumber);
    Iterable<Student> findBySurname(String surname);
    Optional<Student> findByUsername(String username);
}
