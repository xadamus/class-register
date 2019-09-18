package pl.edu.agh.metal.awatroba.classregister.webservices.domain.mark.dto;

import pl.edu.agh.metal.awatroba.classregister.webservices.domain.subject.dto.SubjectPreviewDto;

import java.util.ArrayList;
import java.util.Collection;

public class SubjectMarksDto {
    private SubjectPreviewDto subject;
    private Collection<MarkPreviewDto> marks = new ArrayList<>();

    public SubjectMarksDto(SubjectPreviewDto subject) {
        this.subject = subject;
    }

    public SubjectPreviewDto getSubject() {
        return subject;
    }

    public void setSubject(SubjectPreviewDto subject) {
        this.subject = subject;
    }

    public void addMark(MarkPreviewDto mark) {
        marks.add(mark);
    }

    public Collection<MarkPreviewDto> getMarks() {
        return marks;
    }

    public void setMarks(Collection<MarkPreviewDto> marks) {
        this.marks = marks;
    }
}
