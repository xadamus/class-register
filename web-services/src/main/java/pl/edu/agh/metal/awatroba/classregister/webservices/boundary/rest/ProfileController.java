package pl.edu.agh.metal.awatroba.classregister.webservices.boundary.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.edu.agh.metal.awatroba.classregister.webservices.boundary.rest.dto.ApiResponseDto;
import pl.edu.agh.metal.awatroba.classregister.webservices.boundary.rest.exceptions.ResourceNotFoundException;
import pl.edu.agh.metal.awatroba.classregister.webservices.domain.profile.ProfileService;
import pl.edu.agh.metal.awatroba.classregister.webservices.domain.profile.dto.ProfileCreationDto;
import pl.edu.agh.metal.awatroba.classregister.webservices.domain.profile.dto.ProfilePreviewDto;

import java.net.URI;
import java.util.Collection;

@RestController
@RequestMapping("/api/profiles")
public class ProfileController {
    private ProfileService profileService;

    @Autowired
    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping
    @Secured({"ROLE_ADMIN", "ROLE_TEACHER"})
    public ResponseEntity<Collection<ProfilePreviewDto>> getProfiles() {
        return ResponseEntity.ok().body(profileService.getProfiles());
    }

    @GetMapping("/{profileId}")
    @Secured({"ROLE_ADMIN", "ROLE_TEACHER"})
    public ResponseEntity<ProfilePreviewDto> getProfile(@PathVariable Long profileId) {
        return profileService.getProfile(profileId)
                .map(profilePreviewDto -> ResponseEntity.ok().body(profilePreviewDto))
                .orElseThrow(() -> new ResourceNotFoundException("Profile", "id", profileId));
    }

    @PostMapping
    @Secured("ROLE_ADMIN")
    public ResponseEntity<ApiResponseDto> createProfile(@RequestBody ProfileCreationDto profileCreationDto) {
        ProfilePreviewDto profile = profileService.createProfile(profileCreationDto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/profiles/{id}")
                .buildAndExpand(profile.getId()).toUri();
        return ResponseEntity.created(location).body(new ApiResponseDto(true, "Utworzono nową klasę."));
    }

    @PutMapping("/{profileId}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<ApiResponseDto> updateProfile(@RequestBody ProfileCreationDto profileCreationDto, @PathVariable Long profileId) {
        profileCreationDto.setId(profileId);
        ProfilePreviewDto profilePreviewDto = profileService.updateProfile(profileCreationDto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/profiles/{id}")
                .buildAndExpand(profilePreviewDto.getId()).toUri();
        return ResponseEntity.created(location).body(new ApiResponseDto(true, "Zaktualizowano klasę."));
    }

    @DeleteMapping("/{profileId}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<ApiResponseDto> deleteProfile(@PathVariable Long profileId) {
        if (profileService.deleteProfile(profileId))
            return ResponseEntity.ok().body(new ApiResponseDto(true, "Usunięto klasę."));
        else
            return ResponseEntity.badRequest().body(new ApiResponseDto(false, "Brak klasy o podanym id."));
    }
}
