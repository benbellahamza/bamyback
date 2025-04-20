package org.sid.renaultvisiteursbackend.Controller;

import org.sid.renaultvisiteursbackend.Entity.Visiteur;
import org.sid.renaultvisiteursbackend.Service.VisiteurService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/visiteurs")
@CrossOrigin("*")
public class VisiteurController {

    private final VisiteurService service;

    public VisiteurController(VisiteurService service) {
        this.service = service;
    }

    @PostMapping
    public Visiteur ajouter(@RequestBody Visiteur visiteur) {
        return service.ajouterVisiteur(visiteur);
    }

    @GetMapping
    public List<Visiteur> liste() {
        return service.getAll();
    }

    @DeleteMapping("/{id}")
    public void supprimer(@PathVariable Long id) {
        service.supprimer(id);
    }
}
