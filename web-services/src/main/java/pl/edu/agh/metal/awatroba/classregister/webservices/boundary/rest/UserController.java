package pl.edu.agh.metal.awatroba.classregister.webservices.boundary.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.edu.agh.metal.awatroba.classregister.webservices.boundary.rest.dto.ApiResponseDto;
import pl.edu.agh.metal.awatroba.classregister.webservices.domain.user.Authority;
import pl.edu.agh.metal.awatroba.classregister.webservices.domain.user.Role;
import pl.edu.agh.metal.awatroba.classregister.webservices.domain.user.User;
import pl.edu.agh.metal.awatroba.classregister.webservices.domain.user.UserRepository;
import pl.edu.agh.metal.awatroba.classregister.webservices.domain.user.dto.UserCreationDto;
import pl.edu.agh.metal.awatroba.classregister.webservices.domain.user.dto.UserPreviewDto;

import java.net.URI;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UserRepository userRepository,
                          PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    @Secured("ROLE_ADMIN")
    public ResponseEntity<Collection<UserPreviewDto>> getUsers() {
        List<UserPreviewDto> userPreviewDtos = userRepository.findAll().stream().map(UserPreviewDto::of).collect(Collectors.toList());
        return ResponseEntity.ok(userPreviewDtos);
    }

    @PostMapping
    @Secured("ROLE_ADMIN")
    public ResponseEntity<ApiResponseDto> createUser(@RequestBody UserCreationDto userCreationDto) {
        if (userRepository.existsByUsername(userCreationDto.getUsername())) {
            return ResponseEntity.badRequest().body(new ApiResponseDto(false, "Nazwa użytkownika jest zajęta!"));
        }

        User user = new User(userCreationDto.getUsername(), userCreationDto.getEmail(), passwordEncoder.encode(userCreationDto.getPassword()));
        user.addAuthority(new Authority(Role.ROLE_STUDENT));

        User result = userRepository.save(user);
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/users/{username}")
                .buildAndExpand(result.getUsername()).toUri();

        return ResponseEntity.created(location).body(new ApiResponseDto(true, "Zarejestrowano użytkownika " + userCreationDto.getUsername() + "."));
    }

    @PutMapping("/{id}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<ApiResponseDto> updateUser(@RequestBody UserCreationDto userCreationDto, @PathVariable Long id) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setUsername(userCreationDto.getUsername());
                    user.setPassword(userCreationDto.getPassword());
                    user.setEmail(userCreationDto.getEmail());
                    userRepository.save(user);
                    return ResponseEntity.ok().body(new ApiResponseDto(true, "Zaktualizowano dane użytkownika."));
                }).orElseGet(() -> createUser(userCreationDto));
    }

    @DeleteMapping("/{id}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<ApiResponseDto> deleteUser(@PathVariable Long id) {
        return userRepository.findById(id)
                .map(user -> {
                    userRepository.deleteById(id);
                    return ResponseEntity.ok().body(new ApiResponseDto(true, "Usunięto użytkownika."));
                })
                .orElseGet(() -> ResponseEntity.badRequest().body(new ApiResponseDto(false, "Brak użytkownika o podanym id.")));
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
