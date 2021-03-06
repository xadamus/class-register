package pl.edu.agh.metal.awatroba.classregister.webservices.domain.student;

import java.util.Collection;
import java.util.Optional;

public interface StudentRepository {

    <T extends Student> T save(T student);

    Collection<Student> findAll();

    Optional<Student> findById(Long id);

    void deleteById(Long id);

}
