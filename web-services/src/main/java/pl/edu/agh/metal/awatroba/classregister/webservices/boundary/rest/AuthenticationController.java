package pl.edu.agh.metal.awatroba.classregister.webservices.boundary.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.edu.agh.metal.awatroba.classregister.webservices.boundary.rest.dto.ApiResponseDto;
import pl.edu.agh.metal.awatroba.classregister.webservices.boundary.rest.dto.JwtAuthenticationDto;
import pl.edu.agh.metal.awatroba.classregister.webservices.boundary.security.JwtTokenProvider;
import pl.edu.agh.metal.awatroba.classregister.webservices.domain.user.Authority;
import pl.edu.agh.metal.awatroba.classregister.webservices.domain.user.Role;
import pl.edu.agh.metal.awatroba.classregister.webservices.domain.user.User;
import pl.edu.agh.metal.awatroba.classregister.webservices.domain.user.UserRepository;
import pl.edu.agh.metal.awatroba.classregister.webservices.domain.user.dto.UserCreationDto;
import pl.edu.agh.metal.awatroba.classregister.webservices.domain.user.dto.UserLoginDto;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/public/auth")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider tokenProvider;

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager, JwtTokenProvider tokenProvider, PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<JwtAuthenticationDto> authenticateUser(@Valid @RequestBody UserLoginDto userLoginDto) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userLoginDto.getUsername(),
                        userLoginDto.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthenticationDto(jwt));
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponseDto> registerUser(@Valid @RequestBody UserCreationDto userCreationDto) {
        if (userRepository.existsByUsername(userCreationDto.getUsername())) {
            return ResponseEntity.badRequest().body(new ApiResponseDto(false, "Username is already taken!"));
        }

        User user = new User(userCreationDto.getUsername(), userCreationDto.getEmail(), passwordEncoder.encode(userCreationDto.getPassword()));
        user.addAuthority(new Authority(Role.ROLE_STUDENT));

        User result = userRepository.save(user);
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/users/{username}")
                .buildAndExpand(result.getUsername()).toUri();

        return ResponseEntity.created(location).body(new ApiResponseDto(true, "User registered successfully"));
    }

}
