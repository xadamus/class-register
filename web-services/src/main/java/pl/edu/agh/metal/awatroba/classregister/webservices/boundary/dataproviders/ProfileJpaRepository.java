package pl.edu.agh.metal.awatroba.classregister.webservices.boundary.dataproviders;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.agh.metal.awatroba.classregister.webservices.domain.profile.Profile;
import pl.edu.agh.metal.awatroba.classregister.webservices.domain.profile.ProfileRepository;

public interface ProfileJpaRepository extends JpaRepository<Profile, Long>, ProfileRepository {
}
