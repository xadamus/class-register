package pl.edu.agh.metal.awatroba.classregister.webservices.domain.profile;

import pl.edu.agh.metal.awatroba.classregister.webservices.domain.profile.dto.AllocationCreationDto;
import pl.edu.agh.metal.awatroba.classregister.webservices.domain.profile.dto.AllocationPreviewDto;
import pl.edu.agh.metal.awatroba.classregister.webservices.domain.profile.dto.ProfileCreationDto;
import pl.edu.agh.metal.awatroba.classregister.webservices.domain.profile.dto.ProfilePreviewDto;

import java.util.Collection;
import java.util.Optional;

public interface ProfileService {
    Optional<ProfilePreviewDto> getProfile(Long profileId);

    Collection<ProfilePreviewDto> getProfiles();

    ProfilePreviewDto createProfile(ProfileCreationDto profileCreationDto);

    ProfilePreviewDto updateProfile(ProfileCreationDto profileCreationDto);

    boolean deleteProfile(Long profileId);

    Collection<AllocationPreviewDto> getAllocations(Long profileId);

    AllocationPreviewDto createAllocation(AllocationCreationDto allocationCreationDto);

    boolean deleteProfileAllocation(Long profileId, Long allocationId);
}
