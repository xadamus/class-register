package pl.edu.agh.metal.awatroba.classregister.webservices.boundary.dataproviders;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.agh.metal.awatroba.classregister.webservices.domain.semester.Semester;
import pl.edu.agh.metal.awatroba.classregister.webservices.domain.semester.SemesterRepository;

@Repository
public interface SemesterJpaRepository extends JpaRepository<Semester, Long>, SemesterRepository {
}
