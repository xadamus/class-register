package pl.edu.agh.metal.awatroba.classregister.webservices.domain.subject;

import com.fasterxml.jackson.annotation.JsonIgnore;
import pl.edu.agh.metal.awatroba.classregister.webservices.domain.mark.Mark;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class Subject {
    @Id
    private UUID id;
    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "subject", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Mark> marks;

    protected Subject() {
        this.id = UUID.randomUUID();
        this.marks = new ArrayList<>();
    }

    public Subject(String name) {
        this();
        this.name = name;
    }

    public Subject(String id, String name) {
        this();
        if (id != null) {
            this.id = UUID.fromString(id);
        }
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
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
}
