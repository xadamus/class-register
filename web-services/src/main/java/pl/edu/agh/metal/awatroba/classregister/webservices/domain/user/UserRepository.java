package pl.edu.agh.metal.awatroba.classregister.webservices.domain.user;

import java.util.Collection;
import java.util.Optional;

public interface UserRepository {

    User save(User user);

    Collection<User> findAll();

    Optional<User> findById(Long id);

    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

    void deleteById(Long id);

}
