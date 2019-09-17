package pl.edu.agh.metal.awatroba.classregister.webservices.domain.mark.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import pl.edu.agh.metal.awatroba.classregister.webservices.domain.profile.dto.MembershipPreviewDto;
import pl.edu.agh.metal.awatroba.classregister.webservices.domain.subject.dto.SubjectPreviewDto;

public class MarkPreviewDto {
    private Long id;
    private String value;
    private SubjectPreviewDto subject;
    @JsonIgnore
    private MembershipPreviewDto membership;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public SubjectPreviewDto getSubject() {
        return subject;
    }

    public void setSubject(SubjectPreviewDto subject) {
        this.subject = subject;
    }

    public MembershipPreviewDto getMembership() {
        return membership;
    }

    public void setMembership(MembershipPreviewDto membership) {
        this.membership = membership;
    }
}
