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
        assertThat(semesterService.getSemesters()).hasSize(0);
        semesterService.createSemester();
        assertThat(semesterService.getSemesters()).hasSize(1);
    }

    /**
     * Tests if getting an existing semester by its ID works.
     */
    @Test
    public void getSemesterAndCreateSemester() {
        assertThat(semesterService.getSemester(1L)).isEmpty();
        Optional<SemesterPreviewDto> semester = semesterService.createSemester();
        assertThat(semester).isPresent();
        assertThat(semesterService.getSemester(semester.get().getId())).isPresent();
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
        assertThat(semesterService.getCurrentSemester()).isEmpty();
        Optional<SemesterPreviewDto> semester = semesterService.createSemester();
        assertThat(semester).isPresent();
        semesterService.setCurrentSemester(semester.get().getId());
        assertThat(semesterService.getCurrentSemester()).isPresent();
    }

    /**
     * Tests if setting a current semester works fine.
     */
    @Test
    public void setCurrentSemester() {
        Optional<SemesterPreviewDto> semester = semesterService.createSemester();
        assertThat(semester).isPresent();
        semester.ifPresent(semesterPreviewDto -> semesterService.setCurrentSemester(semesterPreviewDto.getId()));
        assertThat(semesterService.getCurrentSemester()).isPresent();
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
