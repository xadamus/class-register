package pl.edu.agh.metal.awatroba.classregister.webservices.domain.mark;

import pl.edu.agh.metal.awatroba.classregister.webservices.domain.mark.dto.MarkCreationDto;
import pl.edu.agh.metal.awatroba.classregister.webservices.domain.mark.dto.MarkPreviewDto;
import pl.edu.agh.metal.awatroba.classregister.webservices.domain.mark.dto.SubjectMarksDto;

import java.util.Collection;

public interface MarkService {
    Collection<MarkPreviewDto> getMarks(Long membershipId, Long subjectId);

    Collection<SubjectMarksDto> getStudentMarks(Long studentId);

    MarkPreviewDto createMark(MarkCreationDto markCreationDto);

    boolean deleteMark(Long markId);
}
