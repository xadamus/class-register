package pl.edu.agh.metal.awatroba.classregister.webservices.domain.teacher;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.agh.metal.awatroba.classregister.webservices.domain.teacher.dto.TeacherCreationDto;
import pl.edu.agh.metal.awatroba.classregister.webservices.domain.teacher.dto.TeacherPreviewDto;
import pl.edu.agh.metal.awatroba.classregister.webservices.domain.user.UserRepository;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class TeacherFacade implements TeacherService {

    private TeacherRepository teacherRepository;
    private UserRepository userRepository;
    private ModelMapper modelMapper;

    @Autowired
    public TeacherFacade(TeacherRepository teacherRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.teacherRepository = teacherRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        defineMappings();
    }

    @Override
    public Optional<TeacherPreviewDto> getTeacher(Long teacherId) {
        return teacherRepository.findById(teacherId).map(teacher -> modelMapper.map(teacher, TeacherPreviewDto.class));
    }

    @Override
    public Collection<TeacherPreviewDto> getTeachers() {
        return teacherRepository.findAll().stream().map(teacher -> modelMapper.map(teacher, TeacherPreviewDto.class)).collect(Collectors.toList());
    }

    @Override
    public TeacherPreviewDto saveTeacher(TeacherCreationDto teacherCreationDto) {
        Teacher teacher = modelMapper.map(teacherCreationDto, Teacher.class);
        mapUser(teacherCreationDto, teacher);
        return modelMapper.map(teacherRepository.save(teacher), TeacherPreviewDto.class);
    }

    @Override
    public TeacherPreviewDto updateTeacher(TeacherCreationDto teacherCreationDto) {
        return teacherRepository.findById(teacherCreationDto.getId()).map(teacher -> {
            teacher.setFirstName(teacherCreationDto.getFirstName());
            teacher.setLastName(teacherCreationDto.getLastName());
            mapUser(teacherCreationDto, teacher);
            return modelMapper.map(teacherRepository.save(teacher), TeacherPreviewDto.class);
        }).orElseGet(() -> saveTeacher(teacherCreationDto));
    }

    @Override
    public boolean deleteTeacher(Long teacherId) {
        return teacherRepository.findById(teacherId).map(teacher -> {
            if (teacher.getUser() != null) {
                teacher.getUser().setTeacher(null);
                teacher.setUser(null);
            }
            teacherRepository.deleteById(teacherId);
            return true;
        }).orElse(false);
    }

    private void defineMappings() {
        this.modelMapper
                .createTypeMap(Teacher.class, TeacherPreviewDto.class)
                .addMapping(teacher -> teacher.getUser().getUsername(), TeacherPreviewDto::setUsername)
                .addMapping(teacher -> teacher.getUser().getId(), TeacherPreviewDto::setUserId);
    }

    private void mapUser(TeacherCreationDto teacherCreationDto, Teacher teacher) {
        if (teacherCreationDto.getUserId() != null)
            userRepository.findById(teacherCreationDto.getUserId()).ifPresent(user -> {
                teacher.setUser(user);
                user.setTeacher(teacher);
            });
        else {
            if (teacher.getUser() != null)
                teacher.getUser().setTeacher(null);
            teacher.setUser(null);
        }
    }
}
