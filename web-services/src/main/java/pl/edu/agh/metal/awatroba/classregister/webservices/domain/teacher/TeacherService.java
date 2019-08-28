package pl.edu.agh.metal.awatroba.classregister.webservices.domain.teacher;

import pl.edu.agh.metal.awatroba.classregister.webservices.domain.teacher.dto.TeacherCreationDto;
import pl.edu.agh.metal.awatroba.classregister.webservices.domain.teacher.dto.TeacherPreviewDto;

import java.util.Collection;
import java.util.Optional;

public interface TeacherService {

    Optional<TeacherPreviewDto> getTeacher(Long teacherId);

    Collection<TeacherPreviewDto> getTeachers();

    TeacherPreviewDto saveTeacher(TeacherCreationDto teacherCreationDto);

    TeacherPreviewDto updateTeacher(TeacherCreationDto teacherCreationDto);

    boolean deleteTeacher(Long teacherId);

}
