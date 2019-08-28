package pl.edu.agh.metal.awatroba.classregister.webservices.boundary.dataproviders;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.agh.metal.awatroba.classregister.webservices.domain.teacher.Teacher;
import pl.edu.agh.metal.awatroba.classregister.webservices.domain.teacher.TeacherRepository;

@Repository
public interface TeacherJpaRepository extends JpaRepository<Teacher, Long>, TeacherRepository {
}
