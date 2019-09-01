package pl.edu.agh.metal.awatroba.classregister.webservices.domain.semester;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.agh.metal.awatroba.classregister.webservices.domain.semester.dto.SemesterPreviewDto;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
class SemesterFacade implements SemesterService {
    private static final int MAX_PERIODS = 2;

    private SemesterRepository semesterRepository;
    private ModelMapper modelMapper;

    @Autowired
    public SemesterFacade(SemesterRepository semesterRepository, ModelMapper modelMapper) {
        this.semesterRepository = semesterRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Collection<SemesterPreviewDto> getSemesters() {
        return semesterRepository.findAllSorted().stream().map(semester -> modelMapper.map(semester, SemesterPreviewDto.class)).collect(Collectors.toList());
    }

    @Override
    public Optional<SemesterPreviewDto> getSemester(Long semesterId) {
        return semesterRepository.findById(semesterId).map(semester -> modelMapper.map(semester, SemesterPreviewDto.class));
    }

    @Override
    public Optional<SemesterPreviewDto> getCurrentSemester() {
        return semesterRepository.findByCurrent(true).stream().findFirst().map(semester -> modelMapper.map(semester, SemesterPreviewDto.class));
    }

    @Override
    public void setCurrentSemester(Long semesterId) {
        semesterRepository.findById(semesterId).ifPresent(semester -> {
            semesterRepository.resetCurrent();
            semester.setCurrent(true);
            semesterRepository.save(semester);
        });
    }

    @Override
    public Optional<SemesterPreviewDto> createSemester() {
        Semester semester = semesterRepository.findLast()
                .map(Semester::new)
                .orElseGet(() -> {
                    Semester semesterPrototype = new Semester();
                    semesterPrototype.setYear(LocalDate.now().getYear());
                    semesterPrototype.setPeriod(0);
                    return semesterPrototype;
                });
        semester.setId(null);
        semester.setCurrent(false);
        if (semester.getPeriod() >= MAX_PERIODS) {
            semester.setPeriod(1);
            semester.setYear(semester.getYear() + 1);
        } else {
            semester.setPeriod(semester.getPeriod() + 1);
        }
        return Optional.of(modelMapper.map(semesterRepository.save(semester), SemesterPreviewDto.class));
    }
}
