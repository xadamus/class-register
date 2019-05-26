package pl.edu.agh.metal.awatroba.classregister.webservices.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.agh.metal.awatroba.classregister.webservices.model.Mark;

import java.util.UUID;

@Repository
public interface MarkRepository extends JpaRepository<Mark, UUID> {
}
