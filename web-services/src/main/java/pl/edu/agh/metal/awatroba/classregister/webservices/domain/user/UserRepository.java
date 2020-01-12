package pl.edu.agh.metal.awatroba.classregister.webservices.domain.user;

import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.Optional;

public interface UserRepository {

    <T extends User> T save(T user);

    Collection<User> findAll();

    @Query("select u from User u where u.student is null and u.teacher is null")
    Collection<User> findAllFree();

    Optional<User> findById(Long id);

    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

    void deleteById(Long id);

}
