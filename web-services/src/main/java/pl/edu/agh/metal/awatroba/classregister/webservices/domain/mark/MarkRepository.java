package pl.edu.agh.metal.awatroba.classregister.webservices.domain.mark;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.Optional;

public interface MarkRepository {
    Collection<Mark> findAll();

    Optional<Mark> findById(Long id);

    Mark save(Mark mark);

    void deleteById(Long markId);

    @Query("select m from Mark m where m.membership.id = :membershipId and m.subject.id = :subjectId")
    Collection<Mark> findByAllocationAndSubject(@Param("membershipId") Long membershipId,
                                                @Param("subjectId") Long subjectId);
}
