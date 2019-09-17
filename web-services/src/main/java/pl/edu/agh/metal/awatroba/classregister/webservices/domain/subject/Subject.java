package pl.edu.agh.metal.awatroba.classregister.webservices.domain.subject;

import pl.edu.agh.metal.awatroba.classregister.webservices.domain.mark.Mark;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "subject", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Mark> marks;

    public Subject() {
        this.marks = new ArrayList<>();
    }

    public Subject(String name) {
        this();
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Mark> getMarks() {
        return marks;
    }

    public void setMarks(List<Mark> marks) {
        this.marks = marks;
    }

    public void addMark(Mark mark) {
        this.marks.add(mark);
        mark.setSubject(this);
    }

    public void removeMark(Mark mark) {
        this.marks.remove(mark);
        mark.setSubject(null);
    }
}
