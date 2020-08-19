package pl.edu.agh.metal.awatroba.classregister.webservices.boundary.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.edu.agh.metal.awatroba.classregister.webservices.boundary.rest.dto.ApiResponseDto;
import pl.edu.agh.metal.awatroba.classregister.webservices.boundary.rest.exceptions.ResourceNotFoundException;
import pl.edu.agh.metal.awatroba.classregister.webservices.domain.subject.SubjectService;
import pl.edu.agh.metal.awatroba.classregister.webservices.domain.subject.dto.SubjectCreationDto;
import pl.edu.agh.metal.awatroba.classregister.webservices.domain.subject.dto.SubjectPreviewDto;

import java.net.URI;
import java.util.Collection;

@RestController
@RequestMapping("/api/subjects")
class SubjectController {

    private final SubjectService subjectService;

    @Autowired
    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @GetMapping
    @Secured({"ROLE_ADMIN", "ROLE_TEACHER", "ROLE_PARENT", "ROLE_STUDENT"})
    public Collection<SubjectPreviewDto> getSubjects() {
        return subjectService.getSubjects();
    }

    @GetMapping("/{subjectId}")
    @Secured({"ROLE_ADMIN", "ROLE_TEACHER"})
    public SubjectPreviewDto getSubject(@PathVariable Long subjectId) {
        return subjectService.getSubject(subjectId)
                .orElseThrow(() -> new ResourceNotFoundException("Subject", "id", subjectId));
    }

    @PutMapping("/{subjectId}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<ApiResponseDto> updateSubject(@RequestBody SubjectCreationDto subjectCreationDto, @PathVariable Long subjectId) {
        subjectCreationDto.setId(subjectId);
        SubjectPreviewDto subjectPreviewDto = subjectService.updateSubject(subjectCreationDto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/subjects/{id}")
                .buildAndExpand(subjectPreviewDto.getId()).toUri();
        return ResponseEntity.created(location).body(new ApiResponseDto(true, "Zaktualizowano przedmiot " + subjectPreviewDto.getName()));
    }

    @DeleteMapping("/{subjectId}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<ApiResponseDto> deleteSubject(@PathVariable Long subjectId) {
        if (subjectService.deleteSubject(subjectId))
            return ResponseEntity.ok().body(new ApiResponseDto(true, "Usunięto przedmiot"));
        else
            return ResponseEntity.badRequest().body(new ApiResponseDto(false, "Brak przedmiotu o podanym id"));
    }

    @PostMapping
    @Secured("ROLE_ADMIN")
    public ResponseEntity<ApiResponseDto> createSubject(@RequestBody SubjectCreationDto subjectCreationDto) {
        subjectService.createSubject(subjectCreationDto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/subjects/{id}")
                .buildAndExpand(subjectCreationDto.getId()).toUri();
        return ResponseEntity.created(location).body(new ApiResponseDto(true, "Utworzono przedmiot " + subjectCreationDto.getName()));
    }
}
