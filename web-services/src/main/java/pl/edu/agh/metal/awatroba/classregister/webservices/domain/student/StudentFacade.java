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
    public StudentPreviewDto saveStudent(StudentCreationDto studentCreationDto) {
        Student student = modelMapper.map(studentCreationDto, Student.class);
        userRepository.findById(studentCreationDto.getUserId()).ifPresent(student::setUser);
        return modelMapper.map(studentRepository.save(student), StudentPreviewDto.class);
    }

    @Override
    public StudentPreviewDto updateStudent(StudentCreationDto studentCreationDto) {
        return studentRepository.findById(studentCreationDto.getId()).map(student -> {
            student.setFirstName(studentCreationDto.getFirstName());
            student.setLastName(studentCreationDto.getLastName());
            userRepository.findById(studentCreationDto.getUserId()).ifPresent(user -> student.setUser(user));
            return modelMapper.map(studentRepository.save(student), StudentPreviewDto.class);
        }).orElseGet(() -> saveStudent(studentCreationDto));
    }

    @Override
    public boolean deleteStudent(Long studentId) {
        return studentRepository.findById(studentId).map(student -> {
            studentRepository.deleteById(studentId);
            return true;
        }).orElse(false);
    }
}
