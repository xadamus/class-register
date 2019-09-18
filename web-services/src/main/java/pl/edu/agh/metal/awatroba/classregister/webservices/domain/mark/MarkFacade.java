package pl.edu.agh.metal.awatroba.classregister.webservices.domain.mark;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.agh.metal.awatroba.classregister.webservices.domain.mark.dto.MarkCreationDto;
import pl.edu.agh.metal.awatroba.classregister.webservices.domain.mark.dto.MarkPreviewDto;
import pl.edu.agh.metal.awatroba.classregister.webservices.domain.mark.dto.SubjectMarksDto;
import pl.edu.agh.metal.awatroba.classregister.webservices.domain.profile.MembershipRepository;
import pl.edu.agh.metal.awatroba.classregister.webservices.domain.profile.dto.MembershipPreviewDto;
import pl.edu.agh.metal.awatroba.classregister.webservices.domain.semester.SemesterService;
import pl.edu.agh.metal.awatroba.classregister.webservices.domain.semester.dto.SemesterPreviewDto;
import pl.edu.agh.metal.awatroba.classregister.webservices.domain.student.StudentRepository;
import pl.edu.agh.metal.awatroba.classregister.webservices.domain.subject.SubjectRepository;
import pl.edu.agh.metal.awatroba.classregister.webservices.domain.subject.SubjectService;
import pl.edu.agh.metal.awatroba.classregister.webservices.domain.subject.dto.SubjectPreviewDto;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class MarkFacade implements MarkService {
    private MarkRepository markRepository;
    private MembershipRepository membershipRepository;
    private SubjectRepository subjectRepository;
    private StudentRepository studentRepository;
    private SubjectService subjectService;
    private SemesterService semesterService;
    private ModelMapper modelMapper;

    @Autowired
    public MarkFacade(MarkRepository markRepository,
                      MembershipRepository membershipRepository,
                      SubjectRepository subjectRepository,
                      SubjectService subjectService,
                      StudentRepository studentRepository,
                      SemesterService semesterService,
                      ModelMapper modelMapper) {
        this.markRepository = markRepository;
        this.membershipRepository = membershipRepository;
        this.subjectRepository = subjectRepository;
        this.subjectService = subjectService;
        this.studentRepository = studentRepository;
        this.semesterService = semesterService;
        this.modelMapper = modelMapper;
    }

    @Override
    public Collection<MarkPreviewDto> getMarks(Long membershipId, Long subjectId) {
        return markRepository.findByAllocationAndSubject(membershipId, subjectId)
                .stream().map(mark -> modelMapper.map(mark, MarkPreviewDto.class)).collect(Collectors.toList());
    }

    @Override
    public Collection<SubjectMarksDto> getStudentMarks(Long studentId) {
        Long semesterId = semesterService.getCurrentSemester()
                .map(SemesterPreviewDto::getId)
                .orElse(0L);
        Collection<SubjectPreviewDto> subjects = subjectService.getSubjects();
        List<MembershipPreviewDto> membershipPreviewDtos = studentRepository.findById(studentId).map(student -> membershipRepository.findByStudent(student)
                .stream().filter(membership -> membership.getSemester().getId().equals(semesterId))
                .map(membership -> modelMapper.map(membership, MembershipPreviewDto.class))
                .collect(Collectors.toList())).orElse(Collections.emptyList());
        Map<String, SubjectMarksDto> subjectMarksMap = new HashMap<>();

        for (SubjectPreviewDto subject : subjects) {
            subjectMarksMap.put(subject.getId(), new SubjectMarksDto(subject));
        }
        for (MembershipPreviewDto membership : membershipPreviewDtos) {
            for (MarkPreviewDto mark : membership.getMarks()) {
                subjectMarksMap.get(mark.getSubject().getId()).addMark(mark);
            }
        }
        return subjectMarksMap.values();
    }

    @Override
    public MarkPreviewDto createMark(MarkCreationDto markCreationDto) {
        markCreationDto.setId(null);
        Mark mark = modelMapper.map(markCreationDto, Mark.class);
        membershipRepository.findById(markCreationDto.getMembershipId()).ifPresent(mark::setMembership);
        subjectRepository.findById(markCreationDto.getSubjectId()).ifPresent(mark::setSubject);
        Mark savedMark = markRepository.save(mark);
        return modelMapper.map(savedMark, MarkPreviewDto.class);
    }

    @Override
    public boolean deleteMark(Long markId) {
        return markRepository.findById(markId)
                .map(mark -> {
                    markRepository.deleteById(markId);
                    return true;
                }).orElse(false);
    }
}
