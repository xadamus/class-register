package pl.edu.agh.metal.awatroba.classregister.webservices.domain.teacher.dto;

import pl.edu.agh.metal.awatroba.classregister.webservices.domain.teacher.Teacher;

public class TeacherPreviewDto {
    private Long id;
    private String username;
    private String userId;
    private String firstName;
    private String lastName;

    public static TeacherPreviewDto of(Teacher teacher) {
        TeacherPreviewDto teacherPreviewDto = new TeacherPreviewDto();
        teacherPreviewDto.id = teacher.getId();
        teacherPreviewDto.username = teacher.getUser().getUsername();
        teacherPreviewDto.userId = teacher.getUser().getId().toString();
        teacherPreviewDto.firstName = teacher.getFirstName();
        teacherPreviewDto.lastName = teacher.getLastName();
        return teacherPreviewDto;
    }

    public String getFullName() {
        return firstName + " " + lastName;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
