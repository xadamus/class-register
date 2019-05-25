package pl.edu.agh.metal.awatroba.classregister.webservices.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import pl.edu.agh.metal.awatroba.classregister.webservices.api.viewmodel.ContactViewModel;

@RestController
@RequestMapping("/api/contact")
@CrossOrigin
public class ContactController {

    private Logger logger = LoggerFactory.getLogger(ContactController.class);

    @PostMapping
    public void sendMessage(@RequestBody ContactViewModel contactViewModel) {
        logger.info(contactViewModel.toString());
    }

}
