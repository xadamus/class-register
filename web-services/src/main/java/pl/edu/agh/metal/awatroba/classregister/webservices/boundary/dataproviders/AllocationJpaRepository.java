package pl.edu.agh.metal.awatroba.classregister.webservices.boundary.dataproviders;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.agh.metal.awatroba.classregister.webservices.domain.profile.Allocation;
import pl.edu.agh.metal.awatroba.classregister.webservices.domain.profile.AllocationRepository;

@Repository
public interface AllocationJpaRepository extends JpaRepository<Allocation, Long>, AllocationRepository {
}
