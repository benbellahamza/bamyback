package org.sid.renaultvisiteursbackend.Service;

import org.sid.renaultvisiteursbackend.Entity.Visiteur;
import org.sid.renaultvisiteursbackend.Repository.VisiteurRepository;
import org.springframework.stereotype.Service;
import org.sid.renaultvisiteursbackend.Service.CompteurVisiteService;
import java.util.List;
import java.util.Optional;

@Service
public class VisiteurService {

    private final VisiteurRepository repository;
    private final CompteurVisiteService compteurService;

    public VisiteurService(VisiteurRepository repository, CompteurVisiteService compteurService) {
        this.repository = repository;
        this.compteurService = compteurService;
    }

    public Visiteur ajouterVisiteur(Visiteur visiteur) {
        compteurService.incrementerCompteur(); // <-- mise à jour compteur
        return repository.save(visiteur);
    }

    public List<Visiteur> getAll() {
        return repository.findAll();
    }

    public Optional<Visiteur> getById(Long id) {
        return repository.findById(id);
    }

    public void supprimer(Long id) {
        repository.deleteById(id);
    }
}
