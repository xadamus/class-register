package pl.edu.agh.metal.awatroba.classregister.webservices.domain.user;

import java.util.Collection;
import java.util.Optional;

public interface TeacherRepository {

    Teacher save(Teacher teacher);

    Collection<Teacher> findAll();

    Optional<Teacher> findById(Long id);

    void deleteById(Long id);

}
