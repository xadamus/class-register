package pl.edu.agh.metal.awatroba.classregister.webservices.boundary.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.agh.metal.awatroba.classregister.webservices.domain.contact.dto.ContactViewModel;

@RestController
@RequestMapping("/api/contact")
public class ContactController {

    private Logger logger = LoggerFactory.getLogger(ContactController.class);

    @PostMapping
    public void sendMessage(@RequestBody ContactViewModel contactViewModel) {
        logger.info(contactViewModel.toString());
    }

}
