package pl.edu.agh.metal.awatroba.classregister.webservices.boundary.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.edu.agh.metal.awatroba.classregister.webservices.boundary.rest.dto.ApiResponseDto;
import pl.edu.agh.metal.awatroba.classregister.webservices.boundary.rest.exceptions.ResourceNotFoundException;
import pl.edu.agh.metal.awatroba.classregister.webservices.domain.profile.ProfileService;
import pl.edu.agh.metal.awatroba.classregister.webservices.domain.profile.dto.AllocationPreviewDto;
import pl.edu.agh.metal.awatroba.classregister.webservices.domain.teacher.TeacherService;
import pl.edu.agh.metal.awatroba.classregister.webservices.domain.teacher.dto.TeacherCreationDto;
import pl.edu.agh.metal.awatroba.classregister.webservices.domain.teacher.dto.TeacherPreviewDto;

import java.net.URI;
import java.util.Collection;

@RestController
@RequestMapping("/api/teachers")
public class TeacherController {
    private TeacherService teacherService;
    private ProfileService profileService;

    @Autowired
    public TeacherController(TeacherService teacherService,
                             ProfileService profileService) {
        this.teacherService = teacherService;
        this.profileService = profileService;
    }

    @GetMapping
    @Secured({"ROLE_ADMIN", "ROLE_TEACHER"})
    public ResponseEntity<Collection<TeacherPreviewDto>> getTeachers() {
        return ResponseEntity.ok().body(teacherService.getTeachers());
    }

    @GetMapping("/{teacherId}")
    @Secured({"ROLE_ADMIN", "ROLE_TEACHER"})
    public ResponseEntity<TeacherPreviewDto> getTeacher(@PathVariable Long teacherId) {
        return teacherService.getTeacher(teacherId)
                .map(teacherPreviewDto -> ResponseEntity.ok().body(teacherPreviewDto))
                .orElseThrow(() -> new ResourceNotFoundException("Teacher", "id", String.valueOf(teacherId)));
    }

    @PostMapping
    @Secured("ROLE_ADMIN")
    public ResponseEntity<ApiResponseDto> createTeacher(@RequestBody TeacherCreationDto teacherCreationDto) {
        TeacherPreviewDto teacher = teacherService.createTeacher(teacherCreationDto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/teachers/{id}")
                .buildAndExpand(teacher.getId()).toUri();

        return ResponseEntity.created(location).body(new ApiResponseDto(true, "Utworzono nauczyciela " + teacher.getFirstName() + " " + teacher.getLastName()));
    }

    @PutMapping("/{teacherId}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<ApiResponseDto> updateTeacher(@RequestBody TeacherCreationDto teacherCreationDto, @PathVariable Long teacherId) {
        teacherCreationDto.setId(teacherId);
        TeacherPreviewDto teacherPreviewDto = teacherService.updateTeacher(teacherCreationDto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/teachers/{id}")
                .buildAndExpand(teacherPreviewDto.getId()).toUri();
        return ResponseEntity.created(location).body(new ApiResponseDto(true, "Zaktualizowano nauczyciela " + teacherPreviewDto.getFullName()));
    }

    @DeleteMapping("/{teacherId}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<ApiResponseDto> deleteTeacher(@PathVariable Long teacherId) {
        if (teacherService.deleteTeacher(teacherId)) {
            return ResponseEntity.ok().body(new ApiResponseDto(true, "Usunięto nauczyciela."));
        } else {
            return ResponseEntity.badRequest().body(new ApiResponseDto(false, "Brak nauczyciela o podanym id."));
        }
    }

    @GetMapping("/{teacherId}/allocations")
    @Secured({"ROLE_ADMIN", "ROLE_TEACHER"})
    public ResponseEntity<Collection<AllocationPreviewDto>> getTeacherProfiles(@PathVariable Long teacherId, @RequestParam Long semesterId) {
        return ResponseEntity.ok().body(profileService.getTeacherAllocations(teacherId, semesterId));
    }
}
