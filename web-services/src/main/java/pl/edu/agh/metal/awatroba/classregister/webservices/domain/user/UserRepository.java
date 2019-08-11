package pl.edu.agh.metal.awatroba.classregister.webservices.domain.user;

import java.util.Optional;

public interface UserRepository {

    User save(User user);

    Optional<User> find(String id);

    Optional<User> findByUsername(String username);

}
