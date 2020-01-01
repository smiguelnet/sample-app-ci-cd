package net.smiguel.sample.app.sampleapp;

import net.smiguel.sample.app.sampleapp.model.Person;
import net.smiguel.sample.app.sampleapp.service.PersonService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SampleAppApplicationTests {

    @Autowired
    private PersonService personService;

    @Test
    void test_GetPerson() {
        Person personLocal = Person.builder().build();
        Person person = personService.getPerson();

        Assertions.assertEquals(personLocal, person);
    }

}
