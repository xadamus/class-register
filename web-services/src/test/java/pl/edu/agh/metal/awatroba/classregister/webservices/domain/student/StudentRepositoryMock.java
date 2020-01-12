package pl.edu.agh.metal.awatroba.classregister.webservices.domain.student;

import org.springframework.data.repository.NoRepositoryBean;
import pl.edu.agh.metal.awatroba.classregister.webservices.InMemoryRepository;

@NoRepositoryBean
public class StudentRepositoryMock extends InMemoryRepository<Student, Long> implements StudentRepository {
}
