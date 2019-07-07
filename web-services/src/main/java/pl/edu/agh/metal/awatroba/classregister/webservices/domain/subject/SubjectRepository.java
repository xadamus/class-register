package pl.edu.agh.metal.awatroba.classregister.webservices.domain.subject;

import java.util.List;

public interface SubjectRepository {
    List<Subject> findAll();

    Subject save(Subject subject);

    void delete(Subject subject);
}
