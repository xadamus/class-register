package pl.edu.agh.metal.awatroba.classregister.webservices.domain.student;

import org.junit.Before;
import org.junit.Test;
import org.modelmapper.ModelMapper;
import pl.edu.agh.metal.awatroba.classregister.webservices.domain.student.dto.StudentCreationDto;
import pl.edu.agh.metal.awatroba.classregister.webservices.domain.student.dto.StudentPreviewDto;
import pl.edu.agh.metal.awatroba.classregister.webservices.domain.user.User;
import pl.edu.agh.metal.awatroba.classregister.webservices.domain.user.UserRepositoryMock;

import java.util.Optional;

import static org.junit.Assert.*;

public class StudentFacadeTest {
    private StudentService studentService;
    private ModelMapper modelMapper;

    @Before
    public void setUp() {
        StudentRepositoryMock studentRepository = new StudentRepositoryMock();
        UserRepositoryMock userRepository = new UserRepositoryMock();
        modelMapper = new ModelMapper();
        studentService = new StudentFacade(studentRepository, userRepository, modelMapper);

        User user = new User("test", "", "");
        user.setId(1L);
        userRepository.save(user);

        Student student = new Student();
        student.setId(1L);
        student.setUser(user);
        studentRepository.save(student);
    }

    /**
     * Tests if initially created student is loaded properly with all its properties.
     */
    @Test
    public void loadStudent() {
        Optional<StudentPreviewDto> student = studentService.getStudent(1L);
        assertNotNull(student);
        assertEquals(1L, student.get().getId().longValue());
        assertEquals("test", student.get().getUsername());
    }

    /**
     * Checks if an attempt to load a non-existent student returns an empty Optional.
     */
    @Test
    public void loadNotExistingStudent() {
        assertTrue(studentService.getStudent(2L).isEmpty());
    }

    /**
     * Checks if method throws a suitable exception for a null argument.
     */
    @Test(expected = NullPointerException.class)
    public void loadStudent_nullArgument() {
        studentService.getStudent(null);
    }

    /**
     * Checks if the method {@link StudentFacade#getStudent(Long)} throws a suitable exception for a wrong argument.
     */
    @Test(expected = IllegalArgumentException.class)
    public void loadStudent_illegalArgument() {
        studentService.getStudent(-1L);
    }

    /**
     * Checks if facade returns a correct size of stored students.
     */
    @Test
    public void getStudents() {
        assertEquals(1, studentService.getStudents().size());
    }

    /**
     * The method tries to create a student and validates its properties after receiving.
     */
    @Test
    public void createStudent() {
        StudentCreationDto studentCreationDto = new StudentCreationDto();
        studentCreationDto.setId(2L);
        studentCreationDto.setFirstName("firstName");
        studentCreationDto.setLastName("lastName");
        studentService.createStudent(studentCreationDto);
        assertTrue(studentService.getStudent(2L).isPresent());
    }

    /**
     * Checks if method {@link StudentFacade#createStudent(StudentCreationDto)} throws a suitable exception for a null argument.
     */
    @Test(expected = IllegalArgumentException.class)
    public void createStudent_nullArgument() {
        studentService.createStudent(null);
    }

    /**
     * The attempt of updating a stored student and verifying the result.
     */
    @Test
    public void updateStudent() {
        Optional<StudentPreviewDto> student = studentService.getStudent(1L);
        StudentCreationDto studentCreationDto = student.map(studentPreviewDto -> modelMapper.map(studentPreviewDto, StudentCreationDto.class)).get();
        studentCreationDto.setFirstName("changedFirstName");
        studentService.updateStudent(studentCreationDto);
        studentService.getStudent(1L)
                .ifPresentOrElse(s -> assertEquals("changedFirstName", s.getFirstName()), () -> fail("Updated student not found."));
    }

    /**
     * Checks if method throws an appropriate exception for null argument.
     */
    @Test(expected = NullPointerException.class)
    public void updateStudent_nullArgument() {
        studentService.updateStudent(null);
    }

    /**
     * The attempt to delete a student.
     */
    @Test
    public void deleteStudent() {
        studentService.deleteStudent(1L);
        assertTrue(studentService.getStudent(1L).isEmpty());
    }

    /**
     * Checks if method throws an appropriate exception for null argument.
     */
    @Test(expected = NullPointerException.class)
    public void deleteStudent_nullArgument() {
        studentService.deleteStudent(null);
    }
}
