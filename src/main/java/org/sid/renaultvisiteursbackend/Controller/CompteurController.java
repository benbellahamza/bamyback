package org.sid.renaultvisiteursbackend.Controller;

import org.sid.renaultvisiteursbackend.Service.CompteurVisiteService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/compteur")
@CrossOrigin("*")
public class CompteurController {
    private final CompteurVisiteService compteurService;
    public CompteurController(CompteurVisiteService compteurService) {
        this.compteurService = compteurService;
    }

    @GetMapping
    public int getCompteur() {
        return compteurService.getCompteurDuJour();
    }
}
