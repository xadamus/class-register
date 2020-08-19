package pl.edu.agh.metal.awatroba.classregister.webservices.boundary.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.edu.agh.metal.awatroba.classregister.webservices.boundary.rest.dto.ApiResponseDto;
import pl.edu.agh.metal.awatroba.classregister.webservices.boundary.rest.exceptions.ResourceNotFoundException;
import pl.edu.agh.metal.awatroba.classregister.webservices.domain.mark.MarkService;
import pl.edu.agh.metal.awatroba.classregister.webservices.domain.mark.dto.SubjectMarksDto;
import pl.edu.agh.metal.awatroba.classregister.webservices.domain.student.StudentService;
import pl.edu.agh.metal.awatroba.classregister.webservices.domain.student.dto.StudentCreationDto;
import pl.edu.agh.metal.awatroba.classregister.webservices.domain.student.dto.StudentPreviewDto;

import java.net.URI;
import java.util.Collection;

@RestController
@RequestMapping("/api/students")
class StudentController {

    private final StudentService studentService;
    private final MarkService markService;

    @Autowired
    public StudentController(StudentService studentService,
                             MarkService markService) {
        this.studentService = studentService;
        this.markService = markService;
    }

    @GetMapping
    @Secured({"ROLE_ADMIN", "ROLE_TEACHER"})
    public Collection<StudentPreviewDto> getStudents() {
        return studentService.getStudents();
    }

    @GetMapping("/{studentId}")
    @Secured({"ROLE_ADMIN", "ROLE_TEACHER"})
    public StudentPreviewDto getStudent(@PathVariable Long studentId) {
        return studentService.getStudent(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student", "id", String.valueOf(studentId)));
    }

    @PostMapping
    @Secured("ROLE_ADMIN")
    public ResponseEntity<ApiResponseDto> createStudent(@RequestBody StudentCreationDto studentCreationDto) {
        StudentPreviewDto student = studentService.createStudent(studentCreationDto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/students/{id}")
                .buildAndExpand(student.getId()).toUri();

        return ResponseEntity.created(location).body(new ApiResponseDto(true, "Utworzono ucznia " + student.getFirstName() + " " + student.getLastName()));
    }

    @PutMapping("/{studentId}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<ApiResponseDto> updateStudent(@RequestBody StudentCreationDto studentCreationDto, @PathVariable Long studentId) {
        studentCreationDto.setId(studentId);
        StudentPreviewDto studentPreviewDto = studentService.updateStudent(studentCreationDto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/students/{id}")
                .buildAndExpand(studentPreviewDto.getId()).toUri();
        return ResponseEntity.created(location).body(new ApiResponseDto(true, "Zaktualizowano ucznia " + studentPreviewDto.getFullName()));
    }

    @DeleteMapping("/{studentId}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<ApiResponseDto> deleteStudent(@PathVariable Long studentId) {
        if (studentService.deleteStudent(studentId)) {
            return ResponseEntity.ok().body(new ApiResponseDto(true, "Usunięto ucznia."));
        } else {
            return ResponseEntity.badRequest().body(new ApiResponseDto(false, "Brak ucznia o podanym id."));
        }
    }

    @GetMapping("/{studentId}/marks")
    @Secured({"ROLE_ADMIN", "ROLE_TEACHER", "ROLE_PARENT", "ROLE_STUDENT"})
    public Collection<SubjectMarksDto> getStudentMarks(@PathVariable Long studentId) {
        return markService.getStudentMarks(studentId);
    }

}
