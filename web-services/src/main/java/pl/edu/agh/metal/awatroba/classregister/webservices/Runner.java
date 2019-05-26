package pl.edu.agh.metal.awatroba.classregister.webservices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pl.edu.agh.metal.awatroba.classregister.webservices.db.SubjectRepository;
import pl.edu.agh.metal.awatroba.classregister.webservices.model.Subject;

@Component
public class Runner implements CommandLineRunner {

    @Autowired
    private SubjectRepository subjectRepository;

    @Override
    public void run(String... args) throws Exception {
        Subject subject = new Subject("Informatyka");
        subjectRepository.save(subject);
        subject = new Subject("Religia");
        subjectRepository.save(subject);
    }

}
