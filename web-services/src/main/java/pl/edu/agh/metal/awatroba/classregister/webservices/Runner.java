package pl.edu.agh.metal.awatroba.classregister.webservices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import pl.edu.agh.metal.awatroba.classregister.webservices.domain.mark.Mark;
import pl.edu.agh.metal.awatroba.classregister.webservices.domain.subject.Subject;
import pl.edu.agh.metal.awatroba.classregister.webservices.domain.subject.SubjectRepository;
import pl.edu.agh.metal.awatroba.classregister.webservices.domain.user.Authority;
import pl.edu.agh.metal.awatroba.classregister.webservices.domain.user.Role;
import pl.edu.agh.metal.awatroba.classregister.webservices.domain.user.User;
import pl.edu.agh.metal.awatroba.classregister.webservices.domain.user.UserRepository;

@Component
@ConditionalOnProperty(name = "class-register.db.create-entities", havingValue = "true")
public class Runner implements CommandLineRunner {

    private SubjectRepository subjectRepository;

    private UserRepository userRepository;

    @Autowired
    public Runner(SubjectRepository subjectRepository, UserRepository userRepository) {
        this.subjectRepository = subjectRepository;
        this.userRepository = userRepository;
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

        User user = new User();
        user.setUsername("xadamus");
        user.setEmail("xadamus@classregister.com");
        user.setPassword("testing");
        user.addAuthority(new Authority(Role.ROLE_ADMIN));
        userRepository.save(user);
    }

}
