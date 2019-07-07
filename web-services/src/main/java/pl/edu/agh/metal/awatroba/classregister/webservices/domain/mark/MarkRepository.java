package pl.edu.agh.metal.awatroba.classregister.webservices.domain.mark;

import java.util.List;

public interface MarkRepository {
    List<Mark> findAll();

    Mark save(Mark mark);

    void delete(Mark mark);
}
