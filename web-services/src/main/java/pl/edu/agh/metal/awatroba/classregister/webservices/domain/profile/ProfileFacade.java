package pl.edu.agh.metal.awatroba.classregister.webservices.domain.profile;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.agh.metal.awatroba.classregister.webservices.domain.profile.dto.AllocationCreationDto;
import pl.edu.agh.metal.awatroba.classregister.webservices.domain.profile.dto.AllocationPreviewDto;
import pl.edu.agh.metal.awatroba.classregister.webservices.domain.profile.dto.ProfileCreationDto;
import pl.edu.agh.metal.awatroba.classregister.webservices.domain.profile.dto.ProfilePreviewDto;
import pl.edu.agh.metal.awatroba.classregister.webservices.domain.semester.SemesterRepository;
import pl.edu.agh.metal.awatroba.classregister.webservices.domain.subject.SubjectRepository;
import pl.edu.agh.metal.awatroba.classregister.webservices.domain.teacher.TeacherRepository;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProfileFacade implements ProfileService {
    private ProfileRepository profileRepository;
    private AllocationRepository allocationRepository;

    private SemesterRepository semesterRepository;
    private TeacherRepository teacherRepository;
    private SubjectRepository subjectRepository;
    private ModelMapper modelMapper;

    public ProfileFacade(ProfileRepository profileRepository,
                         AllocationRepository allocationRepository,
                         SemesterRepository semesterRepository,
                         TeacherRepository teacherRepository,
                         SubjectRepository subjectRepository,
                         ModelMapper modelMapper) {
        this.profileRepository = profileRepository;
        this.allocationRepository = allocationRepository;
        this.semesterRepository = semesterRepository;
        this.teacherRepository = teacherRepository;
        this.subjectRepository = subjectRepository;
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

    @Override
    public Collection<AllocationPreviewDto> getAllocations(Long profileId) {
        return profileRepository.findById(profileId)
                .map(profile -> profile.getAllocations().stream()
                        .map(allocation -> modelMapper.map(allocation, AllocationPreviewDto.class)).collect(Collectors.toList()))
                .orElseGet(Collections::emptyList);
    }

    @Override
    public AllocationPreviewDto createAllocation(AllocationCreationDto allocationCreationDto) {
        return profileRepository.findById(allocationCreationDto.getProfileId()).map(profile -> {
            Allocation allocation = new Allocation();
            semesterRepository.findById(allocationCreationDto.getSemesterId()).ifPresent(allocation::setSemester);
            teacherRepository.findById(allocationCreationDto.getTeacherId()).ifPresent(allocation::setTeacher);
            subjectRepository.findById(allocationCreationDto.getSubjectId()).ifPresent(allocation::setSubject);
            profile.addAllocation(allocation);
            profileRepository.save(profile);
            return modelMapper.map(allocation, AllocationPreviewDto.class);
        }).orElse(new AllocationPreviewDto());
    }

    @Override
    public boolean deleteProfileAllocation(Long profileId, Long allocationId) {
        return allocationRepository.findById(allocationId).map(allocation -> {
            allocationRepository.deleteById(allocationId);
            return true;
        }).orElse(false);
    }
}
