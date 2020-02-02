package pl.edu.agh.metal.awatroba.classregister.webservices.domain.semester;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.Optional;

public interface SemesterRepository {

    <T extends Semester> T save(T semester);

    @Query("select s from Semester s order by s.year desc, s.period desc")
    Collection<Semester> findAllSorted();

    Collection<Semester> findAll();

    Optional<Semester> findById(Long id);

    Collection<Semester> findByCurrent(Boolean current);

    void deleteById(Long id);

    @Modifying
    @Query("update Semester s set s.current = false")
    void resetCurrent();

    @Query(value = "select * from semester order by year desc, period desc limit 1", nativeQuery = true)
    Optional<Semester> findLast();
    
}
