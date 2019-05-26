package pl.edu.agh.metal.awatroba.classregister.webservices.api;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.agh.metal.awatroba.classregister.webservices.db.SubjectRepository;
import pl.edu.agh.metal.awatroba.classregister.webservices.model.Subject;

import java.util.List;

@RestController
@RequestMapping("/api/subjects")
@CrossOrigin
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
