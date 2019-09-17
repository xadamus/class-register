package pl.edu.agh.metal.awatroba.classregister.webservices.domain.profile;

import pl.edu.agh.metal.awatroba.classregister.webservices.domain.mark.Mark;
import pl.edu.agh.metal.awatroba.classregister.webservices.domain.semester.Semester;
import pl.edu.agh.metal.awatroba.classregister.webservices.domain.student.Student;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class Membership {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "semester_id")
    private Semester semester;

    @ManyToOne(optional = false)
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne(optional = false)
    @JoinColumn(name = "profile_id")
    private Profile profile;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "membership", cascade = CascadeType.ALL)
    private Collection<Mark> marks;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Semester getSemester() {
        return semester;
    }

    public void setSemester(Semester semester) {
        this.semester = semester;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public Collection<Mark> getMarks() {
        return marks;
    }

    public void setMarks(Collection<Mark> marks) {
        this.marks = marks;
    }
}
