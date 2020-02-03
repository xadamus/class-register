package pl.edu.agh.metal.awatroba.classregister.webservices.domain.semester.dto;

import com.google.common.base.Objects;

import static java.lang.Boolean.TRUE;

public class SemesterPreviewDto {
    private Long id;

    private Integer year;

    private Integer period;

    private Boolean current;

    public String getName() {
        StringBuilder builder = new StringBuilder();
        builder
                .append(year).append('/').append(year + 1)
                .append(" (semestr ").append(period).append(")");
        return builder.toString();
    }

    public String getFullName() {
        StringBuilder builder = new StringBuilder();
        builder
                .append(year).append('/').append(year + 1)
                .append(" (semestr ").append(period).append(")");
        if (TRUE.equals(current))
            builder.append(" [aktualny]");
        return builder.toString();
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

    @Override
    public int hashCode() {
        return Objects.hashCode(id, year, period, current);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof SemesterPreviewDto)) return false;
        if (this == obj) return true;
        SemesterPreviewDto semester = (SemesterPreviewDto) obj;
        return Objects.equal(this.id, semester.id)
                && Objects.equal(this.year, semester.year)
                && Objects.equal(this.period, semester.period)
                && Objects.equal(this.current, semester.current);
    }
}
