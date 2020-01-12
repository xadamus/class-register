package pl.edu.agh.metal.awatroba.classregister.webservices.domain.user;

import org.springframework.data.repository.NoRepositoryBean;
import pl.edu.agh.metal.awatroba.classregister.webservices.InMemoryRepository;

import java.util.Collection;
import java.util.Optional;

@NoRepositoryBean
public class UserRepositoryMock extends InMemoryRepository<User, Long> implements UserRepository {
    @Override
    public Collection<User> findAllFree() {
        throw new RuntimeException("Method not implemented.");
    }

    @Override
    public Optional<User> findByUsername(String username) {
        throw new RuntimeException("Method not implemented.");
    }

    @Override
    public boolean existsByUsername(String username) {
        throw new RuntimeException("Method not implemented.");
    }
}
