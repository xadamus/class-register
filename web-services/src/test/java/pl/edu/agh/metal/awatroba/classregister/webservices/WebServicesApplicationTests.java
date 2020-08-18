package pl.edu.agh.metal.awatroba.classregister.webservices;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.edu.agh.metal.awatroba.classregister.webservices.boundary.rest.*;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WebServicesApplicationTests {

    @Autowired
    private AuthenticationController authenticationController;

    @Autowired
    private ContactController contactController;

    @Autowired
    private ProfileController profileController;

    @Autowired
    private SemesterController semesterController;

    @Autowired
    private StudentController studentController;

    @Autowired
    private SubjectController subjectController;

    @Autowired
    private TeacherController teacherController;

    @Autowired
    private UserController userController;

    @Test
    public void contextLoads() {
        assertThat(authenticationController).isNotNull();
        assertThat(contactController).isNotNull();
        assertThat(profileController).isNotNull();
        assertThat(semesterController).isNotNull();
        assertThat(studentController).isNotNull();
        assertThat(subjectController).isNotNull();
        assertThat(teacherController).isNotNull();
        assertThat(userController).isNotNull();
    }

}
