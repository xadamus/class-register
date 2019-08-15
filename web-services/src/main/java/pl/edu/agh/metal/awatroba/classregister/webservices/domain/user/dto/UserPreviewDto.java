package pl.edu.agh.metal.awatroba.classregister.webservices.domain.user.dto;

import org.springframework.security.core.GrantedAuthority;
import pl.edu.agh.metal.awatroba.classregister.webservices.domain.user.User;

import java.util.List;
import java.util.stream.Collectors;

public class UserPreviewDto {

    private Long id;
    private String username;
    private String email;
    private List<String> roles;

    public static UserPreviewDto of(User user) {
        UserPreviewDto userPreviewDto = new UserPreviewDto();
        userPreviewDto.id = user.getId();
        userPreviewDto.username = user.getUsername();
        userPreviewDto.email = user.getEmail();
        userPreviewDto.roles = user.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        return userPreviewDto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

}
