package pl.edu.agh.metal.awatroba.classregister.webservices.domain.profile;

import pl.edu.agh.metal.awatroba.classregister.webservices.domain.profile.dto.*;

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

    Collection<MembershipPreviewDto> getMemberships(Long profileId);

    MembershipPreviewDto createMembership(MembershipCreationDto membershipCreationDto);

    boolean deleteMembership(Long profileId, Long membershipId);
}
