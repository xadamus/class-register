package pl.edu.agh.metal.awatroba.classregister.webservices.domain.mark;

import pl.edu.agh.metal.awatroba.classregister.webservices.domain.subject.Subject;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Date;
import java.util.UUID;

@Entity
public class Mark {
    @Id
    private UUID id;

    private Integer value;

    @ManyToOne(fetch = FetchType.LAZY)
    private Subject subject;

    private Date lastModifiedOn;

    protected Mark() {
        this.id = UUID.randomUUID();
        this.lastModifiedOn = new Date();
    }

    public Mark(Integer value, Subject subject) {
        this();
        this.value = value;
        this.subject = subject;
    }

    public Mark(String id, Integer value, Subject subject) {
        this(value, subject);
        if (id != null) {
            this.id = UUID.fromString(id);
        }
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Date getLastModifiedOn() {
        return lastModifiedOn;
    }

    public void setLastModifiedOn(Date lastModifiedOn) {
        this.lastModifiedOn = lastModifiedOn;
    }
}
