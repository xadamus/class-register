package pl.edu.agh.metal.awatroba.classregister.webservices.boundary.dataproviders;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.agh.metal.awatroba.classregister.webservices.domain.profile.Profile;
import pl.edu.agh.metal.awatroba.classregister.webservices.domain.profile.ProfileRepository;

@Repository
public interface ProfileJpaRepository extends JpaRepository<Profile, Long>, ProfileRepository {
}
