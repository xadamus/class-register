package pl.edu.agh.metal.awatroba.classregister.webservices.boundary.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import pl.edu.agh.metal.awatroba.classregister.webservices.boundary.rest.dto.ApiResponseDto;
import pl.edu.agh.metal.awatroba.classregister.webservices.domain.semester.SemesterService;
import pl.edu.agh.metal.awatroba.classregister.webservices.domain.semester.dto.SemesterPreviewDto;

import java.util.Collection;

@RestController
@RequestMapping("/api/semesters")
public class SemesterController {

    private SemesterService semesterService;

    @Autowired
    public SemesterController(SemesterService semesterService) {
        this.semesterService = semesterService;
    }

    @GetMapping
    @Secured({"ROLE_ADMIN", "ROLE_TEACHER"})
    public ResponseEntity<Collection<SemesterPreviewDto>> getSemesters() {
        return ResponseEntity.ok().body(semesterService.getSemesters());
    }

    @GetMapping("/current")
    public ResponseEntity<SemesterPreviewDto> getCurrentSemester() {
        return semesterService.getCurrentSemester()
                .map(semesterPreviewDto -> ResponseEntity.ok().body(semesterPreviewDto))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/{semesterId}/current")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<ApiResponseDto> setCurrentSemester(@PathVariable Long semesterId) {
        semesterService.setCurrentSemester(semesterId);
        return ResponseEntity.ok().body(new ApiResponseDto(true, "Ustawiono aktualny semestr."));
    }

    @PostMapping
    public ResponseEntity<ApiResponseDto> createSemester() {
        return semesterService.createSemester()
                .map(semesterPreviewDto -> ResponseEntity.ok().body(new ApiResponseDto(true, "Utworzono nowy semestr.")))
                .orElseGet(() -> ResponseEntity.ok().body(new ApiResponseDto(false, "Wystąpił błąd podczas tworzenia semestru.")));
    }
}
