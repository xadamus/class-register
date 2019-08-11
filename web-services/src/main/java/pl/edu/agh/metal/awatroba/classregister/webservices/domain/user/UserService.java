package pl.edu.agh.metal.awatroba.classregister.webservices.domain.user;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    User loadUserById(Long id);

}
