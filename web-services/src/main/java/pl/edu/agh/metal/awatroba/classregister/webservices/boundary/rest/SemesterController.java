package pl.edu.agh.metal.awatroba.classregister.webservices.boundary.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import pl.edu.agh.metal.awatroba.classregister.webservices.boundary.rest.dto.ApiResponseDto;
import pl.edu.agh.metal.awatroba.classregister.webservices.boundary.rest.exceptions.ResourceNotFoundException;
import pl.edu.agh.metal.awatroba.classregister.webservices.domain.semester.SemesterService;
import pl.edu.agh.metal.awatroba.classregister.webservices.domain.semester.dto.SemesterPreviewDto;

import java.util.Collection;

@RestController
@RequestMapping("/api/semesters")
class SemesterController {

    private final SemesterService semesterService;

    @Autowired
    public SemesterController(SemesterService semesterService) {
        this.semesterService = semesterService;
    }

    @GetMapping
    @Secured({"ROLE_ADMIN", "ROLE_TEACHER"})
    public Collection<SemesterPreviewDto> getSemesters() {
        return semesterService.getSemesters();
    }

    @GetMapping("/current")
    public SemesterPreviewDto getCurrentSemester() {
        return semesterService.getCurrentSemester()
                .orElseThrow(() -> new ResourceNotFoundException("Semester"));
    }

    @PostMapping("/{semesterId}/current")
    @Secured("ROLE_ADMIN")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseDto setCurrentSemester(@PathVariable Long semesterId) {
        semesterService.setCurrentSemester(semesterId);
        return new ApiResponseDto(true, "Ustawiono aktualny semestr.");
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseDto createSemester() {
        return semesterService.createSemester()
                .map(semesterPreviewDto -> new ApiResponseDto(true, "Utworzono nowy semestr."))
                .orElseGet(() -> new ApiResponseDto(false, "Wystąpił błąd podczas tworzenia semestru."));
    }
}
