package pl.edu.agh.metal.awatroba.classregister.webservices.domain.mark;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.agh.metal.awatroba.classregister.webservices.domain.mark.dto.MarkCreationDto;
import pl.edu.agh.metal.awatroba.classregister.webservices.domain.mark.dto.MarkPreviewDto;
import pl.edu.agh.metal.awatroba.classregister.webservices.domain.profile.MembershipRepository;
import pl.edu.agh.metal.awatroba.classregister.webservices.domain.subject.SubjectRepository;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@Transactional
public class MarkFacade implements MarkService {
    private MarkRepository markRepository;
    private MembershipRepository membershipRepository;
    private SubjectRepository subjectRepository;
    private ModelMapper modelMapper;

    @Autowired
    public MarkFacade(MarkRepository markRepository, MembershipRepository membershipRepository, SubjectRepository subjectRepository, ModelMapper modelMapper) {
        this.markRepository = markRepository;
        this.membershipRepository = membershipRepository;
        this.subjectRepository = subjectRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Collection<MarkPreviewDto> getMarks(Long membershipId, Long subjectId) {
        return markRepository.findByAllocationAndSubject(membershipId, subjectId)
                .stream().map(mark -> modelMapper.map(mark, MarkPreviewDto.class)).collect(Collectors.toList());
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
