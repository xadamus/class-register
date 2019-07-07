package pl.edu.agh.metal.awatroba.classregister.webservices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import pl.edu.agh.metal.awatroba.classregister.webservices.domain.mark.Mark;
import pl.edu.agh.metal.awatroba.classregister.webservices.domain.subject.Subject;
import pl.edu.agh.metal.awatroba.classregister.webservices.domain.subject.SubjectRepository;

@Component
@ConditionalOnProperty(name = "class-register.db.create-entities", havingValue = "true")
public class Runner implements CommandLineRunner {

    private SubjectRepository subjectRepository;

    @Autowired
    public Runner(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    @Override
    public void run(String... args) {
        Subject subject = new Subject("Informatyka");
        subject.addMark(new Mark(5));
        subject.addMark(new Mark(6));
        subjectRepository.save(subject);
        subject = new Subject("Religia");
        subject.addMark(new Mark(1));
        subject.addMark(new Mark(2));
        subjectRepository.save(subject);
    }

}
