package pl.edu.agh.metal.awatroba.classregister.webservices.boundary.dataproviders;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.agh.metal.awatroba.classregister.webservices.domain.profile.Membership;
import pl.edu.agh.metal.awatroba.classregister.webservices.domain.profile.MembershipRepository;

@Repository
public interface MembershipJpaRepository extends JpaRepository<Membership, Long>, MembershipRepository {
}
