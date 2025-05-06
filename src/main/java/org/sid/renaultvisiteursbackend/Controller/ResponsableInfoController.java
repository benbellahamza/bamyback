package org.sid.renaultvisiteursbackend.Controller;

import org.sid.renaultvisiteursbackend.Entity.Person;
import org.sid.renaultvisiteursbackend.Entity.Responsable;
import org.sid.renaultvisiteursbackend.Repository.PersonRepository;
import org.sid.renaultvisiteursbackend.Repository.ResponsableRepository;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/responsables")
@CrossOrigin("*")
public class ResponsableInfoController {

    private final PersonRepository personRepository;
    private final ResponsableRepository responsableRepository;

    public ResponsableInfoController(PersonRepository personRepository, ResponsableRepository responsableRepository) {
        this.personRepository = personRepository;
        this.responsableRepository = responsableRepository;
    }

    @GetMapping("/me")
    public Map<String, Object> getConnectedResponsable(@AuthenticationPrincipal Jwt jwt) {
        String email = jwt.getSubject();
        Person person = personRepository.findByEmail(email);

        if (person == null || !(person instanceof Responsable)) {
            throw new RuntimeException("Responsable non trouvé.");
        }

        return Map.of(
                "nom", person.getNom(),
                "prenom", person.getPrenom(),
                "email", person.getEmail(),
                "role", person.getRole()
        );
    }
}
