package pl.edu.agh.metal.awatroba.classregister.webservices.domain.subject;

import java.util.List;
import java.util.Optional;

public interface SubjectRepository {
    List<Subject> findAll();

    Optional<Subject> findById(Long id);

    Subject save(Subject subject);

    void delete(Subject subject);
}
