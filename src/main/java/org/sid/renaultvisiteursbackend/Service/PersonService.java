package org.sid.renaultvisiteursbackend.Service;

import org.sid.renaultvisiteursbackend.Entity.Person;
import org.sid.renaultvisiteursbackend.Repository.PersonRepository;
import org.springframework.stereotype.Service;

@Service
public class PersonService {

    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    /**
     * Trouver une personne par email
     */
    public Person findByEmail(String email) {
        return personRepository.findByEmail(email);
    }
}
