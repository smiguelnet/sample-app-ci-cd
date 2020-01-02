package net.smiguel.sample.app.sampleapp.service;

import net.smiguel.sample.app.sampleapp.config.PersonProperties;
import net.smiguel.sample.app.sampleapp.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonService {

    @Autowired
    private PersonProperties personProperties;

    /**
     * Sample Method
     *
     * @return
     */
    public Person getPerson() {
        Person person = Person.builder().build();
        //Just to simulate consul property injection
        if (personProperties != null) {
            person = personProperties.getPerson();
        }
        return person;
    }
}
