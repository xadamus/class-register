package pl.edu.agh.metal.awatroba.classregister.webservices.boundary.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.edu.agh.metal.awatroba.classregister.webservices.boundary.rest.dto.ApiResponseDto;
import pl.edu.agh.metal.awatroba.classregister.webservices.boundary.rest.exceptions.ResourceNotFoundException;
import pl.edu.agh.metal.awatroba.classregister.webservices.domain.mark.MarkService;
import pl.edu.agh.metal.awatroba.classregister.webservices.domain.mark.dto.MarkCreationDto;
import pl.edu.agh.metal.awatroba.classregister.webservices.domain.mark.dto.MarkPreviewDto;
import pl.edu.agh.metal.awatroba.classregister.webservices.domain.profile.ProfileService;
import pl.edu.agh.metal.awatroba.classregister.webservices.domain.profile.dto.*;

import java.net.URI;
import java.util.Collection;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/profiles")
class ProfileController {
    public static final String BAD_REQUEST_MESSAGE = "Błąd zapytania.";

    private final ProfileService profileService;
    private final MarkService markService;

    @Autowired
    public ProfileController(ProfileService profileService,
                             MarkService markService) {
        this.profileService = profileService;
        this.markService = markService;
    }

    @GetMapping
    @Secured({"ROLE_ADMIN", "ROLE_TEACHER"})
    public Collection<ProfilePreviewDto> getProfiles() {
        return profileService.getProfiles();
    }

    @GetMapping("/{profileId}")
    @Secured({"ROLE_ADMIN", "ROLE_TEACHER"})
    public ProfilePreviewDto getProfile(@PathVariable Long profileId) {
        return profileService.getProfile(profileId)
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

    @GetMapping("/{profileId}/allocations")
    @Secured({"ROLE_ADMIN", "ROLE_TEACHER"})
    public Collection<AllocationPreviewDto> getProfileAllocations(@PathVariable Long profileId) {
        return profileService.getAllocations(profileId);
    }

    @PostMapping("/{profileId}/allocations")
    @Secured({"ROLE_ADMIN", "ROLE_TEACHER"})
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseDto createProfileAllocation(@RequestBody AllocationCreationDto allocationCreationDto, @PathVariable Long profileId) {
        allocationCreationDto.setProfileId(profileId);
        profileService.createAllocation(allocationCreationDto);
        return new ApiResponseDto(true, "Utworzono nowy przydział.");
    }

    @DeleteMapping("/{profileId}/allocations/{allocationId}")
    @Secured({"ROLE_ADMIN", "ROLE_TEACHER"})
    public ResponseEntity<ApiResponseDto> deleteProfileAllocation(@PathVariable Long profileId, @PathVariable Long allocationId) {
        if (profileService.deleteProfileAllocation(profileId, allocationId))
            return ResponseEntity.ok().body(new ApiResponseDto(true, "Usunięto przydział."));
        else
            return ResponseEntity.badRequest().body(new ApiResponseDto(false, BAD_REQUEST_MESSAGE));
    }

    @GetMapping("/{profileId}/memberships")
    @Secured({"ROLE_ADMIN", "ROLE_TEACHER"})
    public Collection<MembershipPreviewDto> getProfileMembership(@PathVariable Long profileId,
                                                                                 @RequestParam(required = false) Long subjectId) {
        Collection<MembershipPreviewDto> memberships = profileService.getMemberships(profileId);
        if (subjectId != null)
            memberships.forEach(membership -> membership.setMarks(
                    membership.getMarks().stream().filter(mark -> mark.getSubject().getId().equals(subjectId.toString())).collect(Collectors.toList())
            ));
        return memberships;
    }

    @PostMapping("/{profileId}/memberships")
    @Secured({"ROLE_ADMIN", "ROLE_TEACHER"})
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseDto createProfileMembership(@RequestBody MembershipCreationDto membershipCreationDto, @PathVariable Long profileId) {
        membershipCreationDto.setProfileId(profileId);
        profileService.createMembership(membershipCreationDto);
        return new ApiResponseDto(true, "Utworzono członkostwo.");
    }

    @DeleteMapping("/{profileId}/memberships/{membershipId}")
    @Secured({"ROLE_ADMIN", "ROLE_TEACHER"})
    public ResponseEntity<ApiResponseDto> deleteProfileMembership(@PathVariable Long profileId, @PathVariable Long membershipId) {
        if (profileService.deleteMembership(profileId, membershipId))
            return ResponseEntity.ok().body(new ApiResponseDto(true, "Usunięto członkostwo."));
        else
            return ResponseEntity.badRequest().body(new ApiResponseDto(false, BAD_REQUEST_MESSAGE));
    }

    // TODO: to be improved
    @GetMapping("/{profileId}/memberships/{membershipId}/marks")
    @Secured({"ROLE_ADMIN", "ROLE_TEACHER", "ROLE_PARENT", "ROLE_STUDENT"})
    public ResponseEntity<Collection<MarkPreviewDto>> getMarks(@PathVariable Long profileId,
                                                               @PathVariable Long membershipId,
                                                               @RequestParam Long subjectId) {
        return ResponseEntity.ok(markService.getMarks(membershipId, subjectId));
    }

    // TODO: to be improved
    @PostMapping("/{profileId}/memberships/{membershipId}/marks")
    @Secured({"ROLE_ADMIN", "ROLE_TEACHER"})
    public ResponseEntity<ApiResponseDto> addMark(@PathVariable Long profileId,
                                                  @PathVariable Long membershipId,
                                                  @RequestParam Long subjectId,
                                                  @RequestBody MarkCreationDto markCreationDto) {
        markCreationDto.setMembershipId(membershipId);
        markCreationDto.setSubjectId(subjectId);
        markService.createMark(markCreationDto);
        return ResponseEntity.ok(new ApiResponseDto(true, "Dodano ocenę."));
    }

    // TODO: to be improved
    @DeleteMapping("/{profileId}/memberships/{membershipId}/marks/{markId}")
    @Secured({"ROLE_ADMIN", "ROLE_TEACHER"})
    public ResponseEntity<ApiResponseDto> deleteMark(@PathVariable Long profileId,
                                                  @PathVariable Long membershipId,
                                                  @PathVariable Long markId,
                                                  @RequestParam Long subjectId) {
        if (markService.deleteMark(markId)) {
            return ResponseEntity.ok().body(new ApiResponseDto(true, "Usunięto ocenę."));
        } else {
            return ResponseEntity.badRequest().body(new ApiResponseDto(false, BAD_REQUEST_MESSAGE));
        }
    }
}
