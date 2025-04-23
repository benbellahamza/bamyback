package org.sid.renaultvisiteursbackend.Service;

import org.sid.renaultvisiteursbackend.Entity.Visiteur;
import org.sid.renaultvisiteursbackend.Repository.VisiteurRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    /**
     * ✅ Ajouter un nouveau visiteur
     */
    public Visiteur ajouterVisiteur(Visiteur visiteur) {
        visiteur.setDateEntree(LocalDateTime.now());      // enregistre automatiquement l'heure d'entrée
        compteurService.incrementerCompteur();            // incrémente le compteur journalier
        return repository.save(visiteur);
    }

    /**
     * ✅ Récupérer la liste complète
     */
    public List<Visiteur> getAll() {
        return repository.findAll();
    }

    /**
     * ✅ Récupérer un visiteur par son ID
     */
    public Optional<Visiteur> getById(Long id) {
        return repository.findById(id);
    }

    /**
     * ✅ Supprimer un visiteur + décrémenter compteur
     */
    public void supprimerVisiteur(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Visiteur à supprimer non trouvé");
        }
        repository.deleteById(id);
        compteurService.decrementerCompteur();
    }

    /**
     * ✅ Récupérer uniquement les visiteurs du jour
     */
    public List<Visiteur> getVisiteursDuJour() {
        return repository.findVisiteursDuJour(); // doit être défini dans VisiteurRepository
    }

    /**
     * ✅ Valider la sortie du visiteur (ajout dateSortie)
     */
    public Visiteur validerSortie(Long id) {
        Visiteur visiteur = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Visiteur non trouvé avec l'ID : " + id));
        visiteur.setDateSortie(LocalDateTime.now());
        return repository.save(visiteur);
    }

    /**
     * ✅ Modifier les informations d'un visiteur
     */
    public Visiteur modifierVisiteur(Long id, Visiteur nouveau) {
        Visiteur existant = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Visiteur non trouvé pour la modification"));

        // Mise à jour des champs
        existant.setNom(nouveau.getNom());
        existant.setPrenom(nouveau.getPrenom());
        existant.setCin(nouveau.getCin());
        existant.setGenre(nouveau.getGenre());
        existant.setDestination(nouveau.getDestination());
        existant.setTelephone(nouveau.getTelephone());
        existant.setTypeVisiteur(nouveau.getTypeVisiteur());
        existant.setMatricule(nouveau.getMatricule());

        return repository.save(existant);
    }
}
