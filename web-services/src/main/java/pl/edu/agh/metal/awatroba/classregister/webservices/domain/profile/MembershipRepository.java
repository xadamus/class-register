package pl.edu.agh.metal.awatroba.classregister.webservices.domain.profile;

import java.util.Collection;
import java.util.Optional;

public interface MembershipRepository {
    Membership save(Membership membership);

    Collection<Membership> findAll();

    Optional<Membership> findById(Long id);

    void deleteById(Long id);
}
