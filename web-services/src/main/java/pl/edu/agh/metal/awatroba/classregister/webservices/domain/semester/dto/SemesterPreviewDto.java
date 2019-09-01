package pl.edu.agh.metal.awatroba.classregister.webservices.domain.semester.dto;

import static java.lang.Boolean.TRUE;

public class SemesterPreviewDto {
    private Long id;

    private Integer year;

    private Integer period;

    private Boolean current;

    public String getFullName() {
        StringBuilder builder = new StringBuilder();
        builder
                .append(year).append('/').append(year+1)
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
}
