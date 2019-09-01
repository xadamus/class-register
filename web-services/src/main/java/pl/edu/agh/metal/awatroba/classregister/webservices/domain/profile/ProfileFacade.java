package pl.edu.agh.metal.awatroba.classregister.webservices.domain.profile;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.agh.metal.awatroba.classregister.webservices.domain.profile.dto.ProfileCreationDto;
import pl.edu.agh.metal.awatroba.classregister.webservices.domain.profile.dto.ProfilePreviewDto;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProfileFacade implements ProfileService {
    private ProfileRepository profileRepository;
    private ModelMapper modelMapper;

    @Autowired
    public ProfileFacade(ProfileRepository profileRepository, ModelMapper modelMapper) {
        this.profileRepository = profileRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Optional<ProfilePreviewDto> getProfile(Long profileId) {
        return profileRepository.findById(profileId).map(profile -> modelMapper.map(profile, ProfilePreviewDto.class));
    }

    @Override
    public Collection<ProfilePreviewDto> getProfiles() {
        return profileRepository.findAll().stream().map(profile -> modelMapper.map(profile, ProfilePreviewDto.class)).collect(Collectors.toList());
    }

    @Override
    public ProfilePreviewDto createProfile(ProfileCreationDto profileCreationDto) {
        Profile profile = modelMapper.map(profileCreationDto, Profile.class);
        profile.setId(null);
        return modelMapper.map(profileRepository.save(profile), ProfilePreviewDto.class);
    }

    @Override
    public ProfilePreviewDto updateProfile(ProfileCreationDto profileCreationDto) {
        return profileRepository.findById(profileCreationDto.getId()).map(profile -> {
            profile.setName(profileCreationDto.getName());
            profile.setLevel(profileCreationDto.getLevel());
            return modelMapper.map(profileRepository.save(profile), ProfilePreviewDto.class);
        }).orElseGet(() -> createProfile(profileCreationDto));
    }

    @Override
    public boolean deleteProfile(Long profileId) {
        return profileRepository.findById(profileId).map(profile -> {
            profileRepository.deleteById(profileId);
            return true;
        }).orElse(false);
    }
}
