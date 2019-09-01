package pl.edu.agh.metal.awatroba.classregister.webservices.domain.subject;

import pl.edu.agh.metal.awatroba.classregister.webservices.domain.subject.dto.SubjectCreationDto;
import pl.edu.agh.metal.awatroba.classregister.webservices.domain.subject.dto.SubjectPreviewDto;

import java.util.Collection;
import java.util.Optional;

public interface SubjectService {
    Optional<SubjectPreviewDto> getSubject(Long subjectId);

    Collection<SubjectPreviewDto> getSubjects();

    SubjectPreviewDto createSubject(SubjectCreationDto subjectCreationDto);

    SubjectPreviewDto updateSubject(SubjectCreationDto subjectCreationDto);

    boolean deleteSubject(Long subjectId);
}
