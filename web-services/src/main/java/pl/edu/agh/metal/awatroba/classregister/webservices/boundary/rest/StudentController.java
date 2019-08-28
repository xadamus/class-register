package pl.edu.agh.metal.awatroba.classregister.webservices.boundary.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.edu.agh.metal.awatroba.classregister.webservices.boundary.rest.dto.ApiResponseDto;
import pl.edu.agh.metal.awatroba.classregister.webservices.boundary.rest.exceptions.ResourceNotFoundException;
import pl.edu.agh.metal.awatroba.classregister.webservices.domain.student.StudentService;
import pl.edu.agh.metal.awatroba.classregister.webservices.domain.student.dto.StudentCreationDto;
import pl.edu.agh.metal.awatroba.classregister.webservices.domain.student.dto.StudentPreviewDto;

import java.net.URI;
import java.util.Collection;

@RestController
@RequestMapping("/api/students")
public class StudentController {
    private StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    @Secured({"ROLE_ADMIN", "ROLE_TEACHER"})
    public ResponseEntity<Collection<StudentPreviewDto>> getStudents() {
        return ResponseEntity.ok().body(studentService.getStudents());
    }

    @GetMapping("/{studentId}")
    @Secured({"ROLE_ADMIN", "ROLE_TEACHER"})
    public ResponseEntity<StudentPreviewDto> getStudent(@PathVariable Long studentId) {
        return studentService.getStudent(studentId)
                .map(studentPreviewDto -> ResponseEntity.ok().body(studentPreviewDto))
                .orElseThrow(() -> new ResourceNotFoundException("Student", "id", String.valueOf(studentId)));
    }

    @PostMapping
    @Secured("ROLE_ADMIN")
    public ResponseEntity<ApiResponseDto> createStudent(@RequestBody StudentCreationDto studentCreationDto) {
        StudentPreviewDto student = studentService.saveStudent(studentCreationDto);

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

}
