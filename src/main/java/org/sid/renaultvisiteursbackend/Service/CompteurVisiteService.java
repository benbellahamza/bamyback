package org.sid.renaultvisiteursbackend.Service;

import org.sid.renaultvisiteursbackend.Entity.CompteurVisite;
import org.sid.renaultvisiteursbackend.Repository.CompteurVisiteRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class CompteurVisiteService {

    private final CompteurVisiteRepository repository;

    public CompteurVisiteService(CompteurVisiteRepository repository) {
        this.repository = repository;
    }

    public void incrementerCompteur() {
        LocalDate aujourdHui = LocalDate.now();

        CompteurVisite compteur = repository.findByDate(aujourdHui)
                .orElseGet(() -> {
                    CompteurVisite nouveau = new CompteurVisite();
                    nouveau.setDate(aujourdHui);
                    return nouveau;
                });

        compteur.setCompteur(compteur.getCompteur() + 1);
        repository.save(compteur);
    }

    public int getCompteurDuJour() {
        return repository.findByDate(LocalDate.now())
                .map(CompteurVisite::getCompteur)
                .orElse(0);
    }
}
