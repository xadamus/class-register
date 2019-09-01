package pl.edu.agh.metal.awatroba.classregister.webservices.domain.semester;

import pl.edu.agh.metal.awatroba.classregister.webservices.domain.semester.dto.SemesterPreviewDto;

import java.util.Collection;
import java.util.Optional;

public interface SemesterService {

    Collection<SemesterPreviewDto> getSemesters();

    Optional<SemesterPreviewDto> getSemester(Long semesterId);

    Optional<SemesterPreviewDto> getCurrentSemester();

    void setCurrentSemester(Long semesterId);

    Optional<SemesterPreviewDto> createSemester();

}
