package pl.edu.agh.metal.awatroba.classregister.webservices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
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

    private PasswordEncoder passwordEncoder;

    @Autowired
    public Runner(SubjectRepository subjectRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.subjectRepository = subjectRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        Subject subject = new Subject("Informatyka");
        subjectRepository.save(subject);
        subject = new Subject("Religia");
        subjectRepository.save(subject);

        User user = new User("xadamus", "xadamus@classregister.com", passwordEncoder.encode("testing"));
        user.addAuthority(new Authority(Role.ROLE_ADMIN));
        userRepository.save(user);
    }

}
