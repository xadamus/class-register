package pl.edu.agh.metal.awatroba.classregister.webservices.domain.mark;

import java.util.List;
import java.util.Optional;

public interface MarkRepository {
    List<Mark> findAll();

    Optional<Mark> findById(Long id);

    Mark save(Mark mark);

    void delete(Mark mark);
}
