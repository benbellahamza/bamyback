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

    /**
     * ✅ Ajouter un nouveau visiteur
     */
    @PostMapping
    public ResponseEntity<Visiteur> ajouter(@RequestBody Visiteur visiteur) {
        Visiteur saved = service.ajouterVisiteur(visiteur);
        return ResponseEntity.ok(saved);
    }

    /**
     * ✅ Récupérer la liste complète des visiteurs
     */
    @GetMapping
    public ResponseEntity<List<Visiteur>> liste() {
        return ResponseEntity.ok(service.getAll());
    }

    /**
     * ✅ Supprimer un visiteur (et décrémenter le compteur)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerVisiteur(@PathVariable Long id) {
        service.supprimerVisiteur(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * ✅ Récupérer les visiteurs du jour (filtrés par date d'entrée)
     */
    @GetMapping("/jour")
    public ResponseEntity<List<Visiteur>> getVisiteursDuJour() {
        return ResponseEntity.ok(service.getVisiteursDuJour());
    }

    /**
     * ✅ Valider la sortie d'un visiteur (enregistre date de sortie = now)
     */
    @PatchMapping("/{id}/sortie")
    public ResponseEntity<Visiteur> validerSortie(@PathVariable Long id) {
        Visiteur v = service.validerSortie(id);
        return ResponseEntity.ok(v);
    }

    /**
     * ✅ Modifier les informations d'un visiteur
     */
    @PutMapping("/{id}")
    public ResponseEntity<Visiteur> modifierVisiteur(@PathVariable Long id, @RequestBody Visiteur visiteur) {
        Visiteur updated = service.modifierVisiteur(id, visiteur);
        return ResponseEntity.ok(updated);
    }
}
