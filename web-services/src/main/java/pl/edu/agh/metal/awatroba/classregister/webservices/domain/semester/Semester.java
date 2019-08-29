package pl.edu.agh.metal.awatroba.classregister.webservices.domain.semester;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Semester {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer year;

    private Integer period;

    private Boolean current;

    public Semester() {
    }

    public Semester(Semester semester) {
        this.year = semester.year;
        this.period = semester.period;
        this.current = semester.current;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public Boolean getCurrent() {
        return current;
    }

    public void setCurrent(Boolean current) {
        this.current = current;
    }
}
