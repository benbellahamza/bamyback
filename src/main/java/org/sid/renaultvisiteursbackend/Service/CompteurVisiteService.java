package org.sid.renaultvisiteursbackend.Service;

import org.sid.renaultvisiteursbackend.Entity.CompteurVisite;
import org.sid.renaultvisiteursbackend.Repository.CompteurVisiteRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class CompteurVisiteService {

    private final CompteurVisiteRepository repository;

    public CompteurVisiteService(CompteurVisiteRepository repository) {
        this.repository = repository;
    }

    // ✅ Incrémenter le compteur pour la date du jour
    public void incrementerCompteur() {
        LocalDate aujourdHui = LocalDate.now();

        CompteurVisite compteur = repository.findByDate(aujourdHui)
                .orElseGet(() -> {
                    CompteurVisite nouveau = new CompteurVisite();
                    nouveau.setDate(aujourdHui);
                    nouveau.setCompteur(0); // important si nouveau !
                    return nouveau;
                });

        compteur.setCompteur(compteur.getCompteur() + 1);
        repository.save(compteur);
    }

    // ✅ Récupérer le compteur pour aujourd’hui
    public int getCompteurDuJour() {
        return repository.findByDate(LocalDate.now())
                .map(CompteurVisite::getCompteur)
                .orElse(0);
    }

    // ✅ Décrémenter le compteur pour aujourd’hui
    public void decrementerCompteur() {
        LocalDate aujourdHui = LocalDate.now();
        Optional<CompteurVisite> compteurOpt = repository.findByDate(aujourdHui);

        if (compteurOpt.isPresent()) {
            CompteurVisite compteur = compteurOpt.get();
            int nouveauCompteur = Math.max(0, compteur.getCompteur() - 1);
            compteur.setCompteur(nouveauCompteur);
            repository.save(compteur);
        }
    }
}
