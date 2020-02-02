package pl.edu.agh.metal.awatroba.classregister.webservices.domain.semester;

import org.springframework.data.repository.NoRepositoryBean;
import pl.edu.agh.metal.awatroba.classregister.webservices.InMemoryRepository;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Optional;
import java.util.stream.Collectors;

@NoRepositoryBean
public class SemesterRepositoryMock extends InMemoryRepository<Semester, Long> implements SemesterRepository {

    @Override
    public Collection<Semester> findAllSorted() {
        return database.values().stream()
                .sorted(Comparator.comparingInt(Semester::getYear).thenComparingInt(Semester::getPeriod).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Semester> findByCurrent(Boolean current) {
        return database.values().stream()
                .filter(semester -> Boolean.TRUE.equals(semester.getCurrent()))
                .collect(Collectors.toList());
    }

    @Override
    public void resetCurrent() {
        database.values().forEach(semester -> semester.setCurrent(false));
    }

    @Override
    public Optional<Semester> findLast() {
        Iterator<Semester> iterator = database.values().iterator();
        Semester result = null;
        while (iterator.hasNext()) {
            result = iterator.next();
        }
        return Optional.ofNullable(result);
    }

}
