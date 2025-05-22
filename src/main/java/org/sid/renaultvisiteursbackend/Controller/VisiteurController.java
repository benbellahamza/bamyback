package org.sid.renaultvisiteursbackend.Controller;

import lombok.RequiredArgsConstructor;
import org.sid.renaultvisiteursbackend.Entity.Visiteur;
import org.sid.renaultvisiteursbackend.Service.VisiteurService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/visiteurs")
@CrossOrigin("*")
public class VisiteurController {
    private final VisiteurService service;
    @PostMapping
    public ResponseEntity<Visiteur> ajouter(@RequestBody Visiteur visiteur) {
        Visiteur saved = service.ajouterVisiteur(visiteur);
        return ResponseEntity.ok(saved);
    }
    @GetMapping
    public ResponseEntity<List<Visiteur>> liste() {
        return ResponseEntity.ok(service.getAll());
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerVisiteur(@PathVariable Long id) {
        service.supprimerVisiteur(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/jour")
    public ResponseEntity<List<Visiteur>> getVisiteursDuJour() {
        return ResponseEntity.ok(service.getVisiteursDuJour());
    }
    @PatchMapping("/{id}/sortie")
    public ResponseEntity<Visiteur> validerSortie(@PathVariable Long id) {
        Visiteur v = service.validerSortie(id);
        return ResponseEntity.ok(v);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Visiteur> modifierVisiteur(@PathVariable Long id, @RequestBody Visiteur visiteur) {
        Visiteur updated = service.modifierVisiteur(id, visiteur);
        return ResponseEntity.ok(updated);
    }
}
