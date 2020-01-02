package net.smiguel.sample.app.sampleapp.controller;

import net.smiguel.sample.app.sampleapp.model.Person;
import net.smiguel.sample.app.sampleapp.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"/", "/person"})
public class PersonController {

    @Autowired
    private PersonService personService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Person> getPerson() {
        return ResponseEntity.ok(personService.getPerson());
    }

}
