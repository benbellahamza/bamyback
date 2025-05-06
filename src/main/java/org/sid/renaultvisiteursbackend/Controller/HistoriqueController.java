package org.sid.renaultvisiteursbackend.Controller;

import lombok.RequiredArgsConstructor;
import org.sid.renaultvisiteursbackend.Entity.HistoriqueAction;
import org.sid.renaultvisiteursbackend.Service.HistoriqueService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/historique")
@CrossOrigin("*")
public class HistoriqueController {

    private final HistoriqueService historiqueService;

    @GetMapping
    public List<HistoriqueAction> getAll() {
        return historiqueService.getAllActions();
    }
}
