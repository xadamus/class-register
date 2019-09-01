package pl.edu.agh.metal.awatroba.classregister.webservices.domain.student;

import pl.edu.agh.metal.awatroba.classregister.webservices.domain.student.dto.StudentCreationDto;
import pl.edu.agh.metal.awatroba.classregister.webservices.domain.student.dto.StudentPreviewDto;

import java.util.Collection;
import java.util.Optional;

public interface StudentService {

    Optional<StudentPreviewDto> getStudent(Long studentId);

    Collection<StudentPreviewDto> getStudents();

    StudentPreviewDto createStudent(StudentCreationDto studentCreationDto);

    StudentPreviewDto updateStudent(StudentCreationDto studentCreationDto);

    boolean deleteStudent(Long studentId);

}
