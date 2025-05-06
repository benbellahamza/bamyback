package org.sid.renaultvisiteursbackend.Service;

import org.sid.renaultvisiteursbackend.Entity.HistoriqueAction;
import org.sid.renaultvisiteursbackend.Repository.HistoriqueRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class HistoriqueService {

    private final HistoriqueRepository historiqueRepository;

    public HistoriqueService(HistoriqueRepository historiqueRepository) {
        this.historiqueRepository = historiqueRepository;
    }

    public List<HistoriqueAction> getAllActions() {
        return historiqueRepository.findAll();
    }
    public void enregistrerAction(String agent, String action) {
        System.out.println("✅ Enregistrement action : " + agent + " → " + action);
        HistoriqueAction h = new HistoriqueAction();
        h.setAgent(agent);
        h.setAction(action);
        h.setDateAction(LocalDateTime.now());
        historiqueRepository.save(h);
    }
}
