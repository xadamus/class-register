package pl.edu.agh.metal.awatroba.classregister.webservices.domain.user.dto;

import org.springframework.security.core.GrantedAuthority;
import pl.edu.agh.metal.awatroba.classregister.webservices.domain.user.User;

import java.util.Collection;

public class UserPreviewDto {

    private String username;
    private String email;
    private Collection<? extends GrantedAuthority> authorities;

    public static UserPreviewDto of(User user) {
        UserPreviewDto userPreviewDto = new UserPreviewDto();
        userPreviewDto.username = user.getUsername();
        userPreviewDto.email = user.getEmail();
        userPreviewDto.authorities = user.getAuthorities();
        return userPreviewDto;
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

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

}
