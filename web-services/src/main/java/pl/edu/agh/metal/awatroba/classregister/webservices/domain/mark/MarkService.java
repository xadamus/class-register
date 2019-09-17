package pl.edu.agh.metal.awatroba.classregister.webservices.domain.mark;

import pl.edu.agh.metal.awatroba.classregister.webservices.domain.mark.dto.MarkCreationDto;
import pl.edu.agh.metal.awatroba.classregister.webservices.domain.mark.dto.MarkPreviewDto;

import java.util.Collection;

public interface MarkService {
    Collection<MarkPreviewDto> getMarks(Long membershipId, Long subjectId);

    MarkPreviewDto createMark(MarkCreationDto markCreationDto);

    boolean deleteMark(Long markId);
}
