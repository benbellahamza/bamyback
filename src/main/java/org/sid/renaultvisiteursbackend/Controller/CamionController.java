package org.sid.renaultvisiteursbackend.Controller;

import lombok.RequiredArgsConstructor;
import org.sid.renaultvisiteursbackend.Dto.EntreeCamionDTO;
import org.sid.renaultvisiteursbackend.Dto.SortieCamionDTO;
import org.sid.renaultvisiteursbackend.Entity.Camion;
import org.sid.renaultvisiteursbackend.Service.CamionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}

