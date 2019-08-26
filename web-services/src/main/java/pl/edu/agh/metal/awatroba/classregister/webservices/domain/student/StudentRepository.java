package pl.edu.agh.metal.awatroba.classregister.webservices.domain.student;

import java.util.Collection;
import java.util.Optional;

public interface StudentRepository {

    Student save(Student student);

    Collection<Student> findAll();

    Optional<Student> findById(Long id);

    void deleteById(Long id);

}
