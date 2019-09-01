package pl.edu.agh.metal.awatroba.classregister.webservices.domain.profile;

import java.util.Collection;
import java.util.Optional;

public interface AllocationRepository {
    Allocation save(Allocation allocation);

    Collection<Allocation> findAll();

    Optional<Allocation> findById(Long id);

    void deleteById(Long id);
}
