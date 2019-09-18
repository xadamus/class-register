package pl.edu.agh.metal.awatroba.classregister.webservices.domain.user.dto;

import org.springframework.security.core.GrantedAuthority;
import pl.edu.agh.metal.awatroba.classregister.webservices.domain.student.dto.StudentPreviewDto;
import pl.edu.agh.metal.awatroba.classregister.webservices.domain.teacher.dto.TeacherPreviewDto;
import pl.edu.agh.metal.awatroba.classregister.webservices.domain.user.User;

import java.util.List;
import java.util.stream.Collectors;

public class UserPreviewDto {

    private Long id;
    private String username;
    private String email;
    private List<String> roles;

    private TeacherPreviewDto teacher;
    private StudentPreviewDto student;
    private StudentPreviewDto child;

    public static UserPreviewDto of(User user) {
        UserPreviewDto userPreviewDto = new UserPreviewDto();
        userPreviewDto.id = user.getId();
        userPreviewDto.username = user.getUsername();
        userPreviewDto.email = user.getEmail();
        userPreviewDto.roles = user.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        if (user.getTeacher() != null)
            userPreviewDto.teacher = TeacherPreviewDto.of(user.getTeacher());
        if (user.getStudent() != null)
            userPreviewDto.student = StudentPreviewDto.of(user.getStudent());
        if (user.getChild() != null)
            userPreviewDto.child = StudentPreviewDto.of(user.getChild());
        return userPreviewDto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public TeacherPreviewDto getTeacher() {
        return teacher;
    }

    public void setTeacher(TeacherPreviewDto teacher) {
        this.teacher = teacher;
    }

    public StudentPreviewDto getStudent() {
        return student;
    }

    public void setStudent(StudentPreviewDto student) {
        this.student = student;
    }

    public StudentPreviewDto getChild() {
        return child;
    }

    public void setChild(StudentPreviewDto child) {
        this.child = child;
    }
}
