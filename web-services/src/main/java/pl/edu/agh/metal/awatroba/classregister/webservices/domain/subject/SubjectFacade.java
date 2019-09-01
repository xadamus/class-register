package pl.edu.agh.metal.awatroba.classregister.webservices.domain.subject;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.agh.metal.awatroba.classregister.webservices.domain.subject.dto.SubjectCreationDto;
import pl.edu.agh.metal.awatroba.classregister.webservices.domain.subject.dto.SubjectPreviewDto;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class SubjectFacade implements SubjectService {
    private SubjectRepository subjectRepository;
    private ModelMapper modelMapper;

    @Autowired
    public SubjectFacade(SubjectRepository subjectRepository, ModelMapper modelMapper) {
        this.subjectRepository = subjectRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Optional<SubjectPreviewDto> getSubject(Long subjectId) {
        return subjectRepository.findById(subjectId).map(subject -> modelMapper.map(subject, SubjectPreviewDto.class));
    }

    @Override
    public Collection<SubjectPreviewDto> getSubjects() {
        return subjectRepository.findAll().stream().map(subject -> modelMapper.map(subject, SubjectPreviewDto.class)).collect(Collectors.toList());
    }

    @Override
    public SubjectPreviewDto createSubject(SubjectCreationDto subjectCreationDto) {
        Subject subject = modelMapper.map(subjectCreationDto, Subject.class);
        subject.setId(null);
        return modelMapper.map(subjectRepository.save(subject), SubjectPreviewDto.class);
    }

    @Override
    public SubjectPreviewDto updateSubject(SubjectCreationDto subjectCreationDto) {
        return subjectRepository.findById(subjectCreationDto.getId()).map(subject -> {
            subject.setName(subjectCreationDto.getName());
            subjectRepository.save(subject);
            return modelMapper.map(subject, SubjectPreviewDto.class);
        }).orElseGet(() -> createSubject(subjectCreationDto));
    }

    @Override
    public boolean deleteSubject(Long subjectId) {
        return subjectRepository.findById(subjectId).map(subject -> {
            subjectRepository.delete(subject);
            return true;
        }).orElse(false);
    }
}
