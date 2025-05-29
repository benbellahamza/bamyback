package org.sid.renaultvisiteursbackend.Controller;

import lombok.RequiredArgsConstructor;
import org.sid.renaultvisiteursbackend.Dto.EntreeCamionDTO;
import org.sid.renaultvisiteursbackend.Dto.SortieCamionDTO;
import org.sid.renaultvisiteursbackend.Entity.Camion;
import org.sid.renaultvisiteursbackend.Service.CamionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/livraison")
@RequiredArgsConstructor
public class CamionController {
    private final CamionService service;

    @PostMapping("/entree")
    public ResponseEntity<Camion> enregistrerEntree(@RequestBody EntreeCamionDTO dto) {
        return ResponseEntity.ok(service.enregistrerEntree(dto));
    }

    @PostMapping("/sortie/{numeroChassis}")
    public ResponseEntity<Camion> enregistrerSortie(
            @PathVariable String numeroChassis,
            @RequestBody SortieCamionDTO dto
    ) {
        return ResponseEntity.ok(service.enregistrerSortie(numeroChassis, dto));
    }

    @GetMapping("/camion/{numeroChassis}")
    public ResponseEntity<Camion> rechercher(@PathVariable String numeroChassis) {
        return service.getCamion(numeroChassis)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/all")
    public ResponseEntity<List<Camion>> getAllCamions() {
        return ResponseEntity.ok(service.getAllCamions());
    }

    // 🆕 NOUVEAU ENDPOINT POUR LA MODIFICATION
    @PutMapping("/modifier/{numeroChassis}")
    public ResponseEntity<?> modifierCamion(
            @PathVariable String numeroChassis,
            @RequestBody Map<String, String> modifications
    ) {
        try {
            Camion camionModifie = service.modifierCamion(numeroChassis, modifications);
            return ResponseEntity.ok(Map.of(
                    "message", "Camion modifié avec succès",
                    "camion", camionModifie
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "message", "Erreur lors de la modification : " + e.getMessage()
            ));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of(
                    "message", "Erreur interne du serveur"
            ));
        }
    }
}