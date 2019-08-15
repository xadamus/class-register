package pl.edu.agh.metal.awatroba.classregister.webservices.boundary.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.agh.metal.awatroba.classregister.webservices.domain.user.User;
import pl.edu.agh.metal.awatroba.classregister.webservices.domain.user.UserRepository;
import pl.edu.agh.metal.awatroba.classregister.webservices.domain.user.dto.UserPreviewDto;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    @Secured("ROLE_ADMIN")
    public ResponseEntity<Collection<UserPreviewDto>> getUsers() {
        List<UserPreviewDto> userPreviewDtos = userRepository.findAll().stream().map(UserPreviewDto::of).collect(Collectors.toList());
        return ResponseEntity.ok(userPreviewDtos);
    }

    @GetMapping("/current")
    public ResponseEntity<UserPreviewDto> getCurrentUser(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(UserPreviewDto.of(user));
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserPreviewDto> getUser(@PathVariable String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User " + username + " not found."));
        return ResponseEntity.ok(UserPreviewDto.of(user));
    }
}
