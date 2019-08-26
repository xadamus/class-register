package pl.edu.agh.metal.awatroba.classregister.webservices.boundary.dataproviders;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.agh.metal.awatroba.classregister.webservices.domain.student.Student;
import pl.edu.agh.metal.awatroba.classregister.webservices.domain.student.StudentRepository;

@Repository
public interface StudentJpaRepository extends JpaRepository<Student, Long>, StudentRepository {
}
