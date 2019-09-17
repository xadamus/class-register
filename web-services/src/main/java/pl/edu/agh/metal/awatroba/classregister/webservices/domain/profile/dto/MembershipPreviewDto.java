package pl.edu.agh.metal.awatroba.classregister.webservices.domain.profile.dto;

import pl.edu.agh.metal.awatroba.classregister.webservices.domain.mark.dto.MarkPreviewDto;
import pl.edu.agh.metal.awatroba.classregister.webservices.domain.semester.dto.SemesterPreviewDto;
import pl.edu.agh.metal.awatroba.classregister.webservices.domain.student.dto.StudentPreviewDto;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class MembershipPreviewDto {
    private Long id;
    private SemesterPreviewDto semester;
    private StudentPreviewDto student;
    private ProfilePreviewDto profile;
    private Collection<MarkPreviewDto> marks;

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

    public StudentPreviewDto getStudent() {
        return student;
    }

    public void setStudent(StudentPreviewDto student) {
        this.student = student;
    }

    public ProfilePreviewDto getProfile() {
        return profile;
    }

    public void setProfile(ProfilePreviewDto profile) {
        this.profile = profile;
    }

    public List<String> getMarks() {
        if (marks != null)
            return marks.stream().map(MarkPreviewDto::getValue).collect(Collectors.toList());
        return Collections.emptyList();
    }

    public void setMarks(Collection<MarkPreviewDto> marks) {
        this.marks = marks;
    }
}
