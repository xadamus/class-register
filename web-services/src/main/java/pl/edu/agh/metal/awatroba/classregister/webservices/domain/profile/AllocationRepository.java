package pl.edu.agh.metal.awatroba.classregister.webservices.domain.profile;

import pl.edu.agh.metal.awatroba.classregister.webservices.domain.teacher.Teacher;

import java.util.Collection;
import java.util.Optional;

public interface AllocationRepository {
    Allocation save(Allocation allocation);

    Collection<Allocation> findAll();

    Collection<Allocation> findByTeacher(Teacher teacher);

    Optional<Allocation> findById(Long id);

    void deleteById(Long id);
}
