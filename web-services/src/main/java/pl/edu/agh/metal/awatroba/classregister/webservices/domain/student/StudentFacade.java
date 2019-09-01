package pl.edu.agh.metal.awatroba.classregister.webservices.domain.student;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.agh.metal.awatroba.classregister.webservices.domain.student.dto.StudentCreationDto;
import pl.edu.agh.metal.awatroba.classregister.webservices.domain.student.dto.StudentPreviewDto;
import pl.edu.agh.metal.awatroba.classregister.webservices.domain.user.UserRepository;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class StudentFacade implements StudentService {

    private StudentRepository studentRepository;
    private UserRepository userRepository;
    private ModelMapper modelMapper;

    @Autowired
    public StudentFacade(StudentRepository studentRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.studentRepository = studentRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        defineMappings();
    }

    @Override
    public Optional<StudentPreviewDto> getStudent(Long studentId) {
        return studentRepository.findById(studentId).map(student -> modelMapper.map(student, StudentPreviewDto.class));
    }

    @Override
    public Collection<StudentPreviewDto> getStudents() {
        return studentRepository.findAll().stream().map(student -> modelMapper.map(student, StudentPreviewDto.class)).collect(Collectors.toList());
    }

    @Override
    public StudentPreviewDto createStudent(StudentCreationDto studentCreationDto) {
        Student student = modelMapper.map(studentCreationDto, Student.class);
        mapUser(studentCreationDto, student);
        return modelMapper.map(studentRepository.save(student), StudentPreviewDto.class);
    }

    @Override
    public StudentPreviewDto updateStudent(StudentCreationDto studentCreationDto) {
        return studentRepository.findById(studentCreationDto.getId()).map(student -> {
            student.setFirstName(studentCreationDto.getFirstName());
            student.setLastName(studentCreationDto.getLastName());
            mapUser(studentCreationDto, student);
            return modelMapper.map(studentRepository.save(student), StudentPreviewDto.class);
        }).orElseGet(() -> createStudent(studentCreationDto));
    }

    @Override
    public boolean deleteStudent(Long studentId) {
        return studentRepository.findById(studentId).map(student -> {
            if (student.getUser() != null) {
                student.getUser().setStudent(null);
                student.setUser(null);
            }
            studentRepository.deleteById(studentId);
            return true;
        }).orElse(false);
    }

    private void defineMappings() {
        this.modelMapper
                .createTypeMap(Student.class, StudentPreviewDto.class)
                .addMapping(student -> student.getUser().getUsername(), StudentPreviewDto::setUsername)
                .addMapping(student -> student.getUser().getId(), StudentPreviewDto::setUserId);
    }

    private void mapUser(StudentCreationDto studentCreationDto, Student student) {
        if (studentCreationDto.getUserId() != null)
            userRepository.findById(studentCreationDto.getUserId()).ifPresent(user -> {
                student.setUser(user);
                user.setStudent(student);
            });
        else {
            if (student.getUser() != null)
                student.getUser().setStudent(null);
            student.setUser(null);
        }
    }
}
