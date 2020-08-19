package pl.edu.agh.metal.awatroba.classregister.webservices.boundary.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import pl.edu.agh.metal.awatroba.classregister.webservices.boundary.rest.dto.JwtAuthenticationDto;
import pl.edu.agh.metal.awatroba.classregister.webservices.boundary.security.JwtTokenProvider;
import pl.edu.agh.metal.awatroba.classregister.webservices.domain.user.dto.UserLoginDto;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/public/auth")
class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;

    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager, JwtTokenProvider tokenProvider) {
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
    }

    @PostMapping("/authenticate")
    @ResponseStatus(HttpStatus.CREATED)
    public JwtAuthenticationDto authenticateUser(@Valid @RequestBody UserLoginDto userLoginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userLoginDto.getUsername(),
                        userLoginDto.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);
        return new JwtAuthenticationDto(jwt);
    }

}
