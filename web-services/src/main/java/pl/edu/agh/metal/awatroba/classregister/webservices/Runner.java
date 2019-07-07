package pl.edu.agh.metal.awatroba.classregister.webservices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
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
        subjectRepository.save(subject);
        subject = new Subject("Religia");
        subjectRepository.save(subject);
    }

}
