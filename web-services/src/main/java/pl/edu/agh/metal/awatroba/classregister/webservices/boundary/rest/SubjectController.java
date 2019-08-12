package pl.edu.agh.metal.awatroba.classregister.webservices.boundary.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.agh.metal.awatroba.classregister.webservices.domain.subject.Subject;
import pl.edu.agh.metal.awatroba.classregister.webservices.domain.subject.SubjectRepository;

import java.util.List;

@RestController
@RequestMapping("/api/subjects")
public class SubjectController {

    private SubjectRepository subjectRepository;

    public SubjectController(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    @GetMapping("/all")
    public List<Subject> all() {
        return this.subjectRepository.findAll();
    }

}
