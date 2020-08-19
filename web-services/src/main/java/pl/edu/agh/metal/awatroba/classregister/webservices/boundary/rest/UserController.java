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
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
class UserController {

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
    public Collection<UserPreviewDto> getUsers() {
        return userRepository.findAll().stream().map(UserPreviewDto::of).collect(Collectors.toList());
    }

    @PostMapping
    @Secured("ROLE_ADMIN")
    public ResponseEntity<ApiResponseDto> createUser(@RequestBody UserCreationDto userCreationDto) {
        if (userRepository.existsByUsername(userCreationDto.getUsername())) {
            return ResponseEntity.badRequest().body(new ApiResponseDto(false, "Nazwa użytkownika jest zajęta!"));
        }

        User user = new User(userCreationDto.getUsername(), userCreationDto.getEmail(), passwordEncoder.encode(userCreationDto.getPassword()));
        userCreationDto.getRoles().forEach(r -> user.addAuthority(new Authority(Role.valueOf(r))));

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
                    if (userCreationDto.getPassword() != null && !userCreationDto.getPassword().isEmpty())
                        user.setPassword(passwordEncoder.encode(userCreationDto.getPassword()));
                    user.setEmail(userCreationDto.getEmail());
                    user.getAuthorities().removeIf(a -> !userCreationDto.getRoles().contains(a.getAuthority()));
                    userCreationDto.getRoles().forEach(r -> {
                        if (user.getAuthorities().stream().noneMatch(a -> a.getAuthority().equals(r))) {
                            user.addAuthority(new Authority(Role.valueOf(r)));
                        }
                    });
                    userRepository.save(user);
                    return ResponseEntity.ok().body(new ApiResponseDto(true, "Zaktualizowano dane użytkownika."));
                }).orElseGet(() -> createUser(userCreationDto));
    }

    @DeleteMapping("/{id}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<ApiResponseDto> deleteUser(@PathVariable Long id) {
        return userRepository.findById(id)
                .map(user -> {
                    if (user.getStudent() != null) {
                        user.getStudent().setUser(null);
                        user.setStudent(null);
                    }
                    if (user.getTeacher() != null) {
                        user.getTeacher().setUser(null);
                        user.setTeacher(null);
                    }
                    userRepository.deleteById(id);
                    return ResponseEntity.ok().body(new ApiResponseDto(true, "Usunięto użytkownika."));
                })
                .orElseGet(() -> ResponseEntity.badRequest().body(new ApiResponseDto(false, "Brak użytkownika o podanym id.")));
    }

    @GetMapping("/current")
    public UserPreviewDto getCurrentUser(@AuthenticationPrincipal User user) {
        return UserPreviewDto.of(user);
    }

    @GetMapping("/{username}")
    public UserPreviewDto getUser(@PathVariable String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User " + username + " not found."));
        return UserPreviewDto.of(user);
    }

    @GetMapping("/free")
    @Secured("ROLE_ADMIN")
    public Collection<UserPreviewDto> getFreeUsers() {
        return userRepository.findAllFree().stream().map(UserPreviewDto::of).collect(Collectors.toList());
    }
}
