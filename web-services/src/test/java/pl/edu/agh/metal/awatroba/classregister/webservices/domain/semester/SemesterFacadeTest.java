package pl.edu.agh.metal.awatroba.classregister.webservices.domain.semester;

import org.junit.Before;
import org.junit.Test;
import org.modelmapper.ModelMapper;
import pl.edu.agh.metal.awatroba.classregister.webservices.domain.semester.dto.SemesterPreviewDto;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class SemesterFacadeTest {

    private SemesterService semesterService;

    @Before
    public void setUp() {
        semesterService = new SemesterFacade(new SemesterRepositoryMock(), new ModelMapper());
    }

    /**
     * Tests if service returns right size of semesters collection.
     */
    @Test
    public void getSemesters() {
        assertThat(semesterService.getSemesters().size()).isEqualTo(0);
        semesterService.createSemester();
        assertThat(semesterService.getSemesters().size()).isEqualTo(1);
    }

    /**
     * Tests if getting an existing semester by its ID works.
     */
    @Test
    public void getSemesterAndCreateSemester() {
        assertThat(semesterService.getSemester(1L).isEmpty()).isTrue();
        Optional<SemesterPreviewDto> semester = semesterService.createSemester();
        assertThat(semester.isPresent()).isTrue();
        assertThat(semesterService.getSemester(semester.get().getId()).isPresent()).isTrue();
    }

    /**
     * Checks if method throws a proper exception for null ID.
     */
    @Test(expected = IllegalArgumentException.class)
    public void getSemester_nullId() {
        semesterService.getSemester(null);
    }

    /**
     * Checks if method throws a proper exception for wrong ID.
     */
    @Test(expected = IllegalArgumentException.class)
    public void getSemester_wrongId() {
        semesterService.getSemester(-1L);
    }

    /**
     * Tries to get current semester in both cases, if it exists or not.
     */
    @Test
    public void getCurrentSemester() {
        assertThat(semesterService.getCurrentSemester().isEmpty()).isTrue();
        Optional<SemesterPreviewDto> semester = semesterService.createSemester();
        assertThat(semester.isPresent()).isTrue();
        semesterService.setCurrentSemester(semester.get().getId());
        assertThat(semesterService.getCurrentSemester().isPresent()).isTrue();
    }

    /**
     * Tests if setting a current semester works fine.
     */
    @Test
    public void setCurrentSemester() {
        Optional<SemesterPreviewDto> semester = semesterService.createSemester();
        assertThat(semester.isPresent()).isTrue();
        semester.ifPresent(semesterPreviewDto -> semesterService.setCurrentSemester(semesterPreviewDto.getId()));
        assertThat(semesterService.getCurrentSemester().isPresent()).isTrue();
        assertThat(semesterService.getCurrentSemester().get().getId().equals(semester.get().getId())).isTrue();
    }

    /**
     * Checks if method throws a proper exception for null ID.
     */
    @Test(expected = IllegalArgumentException.class)
    public void setCurrentSemester_nullId() {
        semesterService.setCurrentSemester(null);
    }
}
