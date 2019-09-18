package pl.edu.agh.metal.awatroba.classregister.webservices.domain.student.dto;

import pl.edu.agh.metal.awatroba.classregister.webservices.domain.student.Student;

public class StudentPreviewDto {
    private Long id;
    private String username;
    private String userId;
    private String firstName;
    private String lastName;

    public static StudentPreviewDto of(Student student) {
        StudentPreviewDto studentPreviewDto = new StudentPreviewDto();
        studentPreviewDto.id = student.getId();
        studentPreviewDto.username = student.getUser().getUsername();
        studentPreviewDto.userId = student.getUser().getId().toString();
        studentPreviewDto.firstName = student.getFirstName();
        studentPreviewDto.lastName = student.getLastName();
        return studentPreviewDto;
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
