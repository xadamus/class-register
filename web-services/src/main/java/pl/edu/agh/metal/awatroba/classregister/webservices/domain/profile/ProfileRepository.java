package pl.edu.agh.metal.awatroba.classregister.webservices.domain.profile;

import java.util.Collection;
import java.util.Optional;

public interface ProfileRepository {
    Profile save(Profile profile);

    Collection<Profile> findAll();

    Optional<Profile> findById(Long id);

    void deleteById(Long id);
}
