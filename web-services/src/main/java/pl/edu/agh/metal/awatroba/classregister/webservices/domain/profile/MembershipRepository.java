package pl.edu.agh.metal.awatroba.classregister.webservices.domain.profile;

import pl.edu.agh.metal.awatroba.classregister.webservices.domain.student.Student;

import java.util.Collection;
import java.util.Optional;

public interface MembershipRepository {
    Membership save(Membership membership);

    Collection<Membership> findAll();

    Optional<Membership> findById(Long id);

    Collection<Membership> findByStudent(Student student);

    void deleteById(Long id);
}
