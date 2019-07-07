package pl.edu.agh.metal.awatroba.classregister.webservices.boundary.dataproviders;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.agh.metal.awatroba.classregister.webservices.domain.mark.Mark;
import pl.edu.agh.metal.awatroba.classregister.webservices.domain.mark.MarkRepository;

import java.util.UUID;

@Repository
public interface MarkJpaRepository extends JpaRepository<Mark, UUID>, MarkRepository {
}
