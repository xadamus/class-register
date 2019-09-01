package pl.edu.agh.metal.awatroba.classregister.webservices.domain.profile.dto;

import pl.edu.agh.metal.awatroba.classregister.webservices.domain.semester.dto.SemesterPreviewDto;
import pl.edu.agh.metal.awatroba.classregister.webservices.domain.subject.dto.SubjectPreviewDto;
import pl.edu.agh.metal.awatroba.classregister.webservices.domain.teacher.dto.TeacherPreviewDto;

public class AllocationPreviewDto {
    private Long id;
    private SemesterPreviewDto semester;
    private TeacherPreviewDto teacher;
    private ProfilePreviewDto profile;
    private SubjectPreviewDto subject;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SemesterPreviewDto getSemester() {
        return semester;
    }

    public void setSemester(SemesterPreviewDto semester) {
        this.semester = semester;
    }

    public TeacherPreviewDto getTeacher() {
        return teacher;
    }

    public void setTeacher(TeacherPreviewDto teacher) {
        this.teacher = teacher;
    }

    public ProfilePreviewDto getProfile() {
        return profile;
    }

    public void setProfile(ProfilePreviewDto profile) {
        this.profile = profile;
    }

    public SubjectPreviewDto getSubject() {
        return subject;
    }

    public void setSubject(SubjectPreviewDto subject) {
        this.subject = subject;
    }
}
