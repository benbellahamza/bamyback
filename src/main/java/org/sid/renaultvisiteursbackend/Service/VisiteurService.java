package org.sid.renaultvisiteursbackend.Service;

import org.sid.renaultvisiteursbackend.Entity.ActiviteAgent;
import org.sid.renaultvisiteursbackend.Entity.HistoriqueAction;
import org.sid.renaultvisiteursbackend.Entity.Person;
import org.sid.renaultvisiteursbackend.Entity.Visiteur;
import org.sid.renaultvisiteursbackend.Repository.VisiteurRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class VisiteurService {

    private final VisiteurRepository repository;
    private final CompteurVisiteService compteurService;
    private final ActiviteAgentService activiteAgentService;
    private final PersonService personService;
    private final HistoriqueService historiqueService;

    public VisiteurService(VisiteurRepository repository,
                           CompteurVisiteService compteurService,
                           ActiviteAgentService activiteAgentService,
                           PersonService personService,
                           HistoriqueService historiqueService) {
        this.repository = repository;
        this.compteurService = compteurService;
        this.activiteAgentService = activiteAgentService;
        this.personService = personService;
        this.historiqueService = historiqueService;
    }

    public Visiteur ajouterVisiteur(Visiteur visiteur) {
        visiteur.setDateEntree(LocalDateTime.now());
        compteurService.incrementerCompteur();
        Visiteur saved = repository.save(visiteur);

        enregistrerActivite("Ajout Visiteur", "Ajout du visiteur " + visiteur.getNom() + " " + visiteur.getPrenom());

        return saved;
    }

    public List<Visiteur> getAll() {
        return repository.findAll();
    }

    public Optional<Visiteur> getById(Long id) {
        return repository.findById(id);
    }

    public void supprimerVisiteur(Long id) {
        Visiteur visiteur = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Visiteur à supprimer non trouvé"));

        repository.deleteById(id);
        compteurService.decrementerCompteur();

        enregistrerActivite("Suppression Visiteur", "Suppression du visiteur " + visiteur.getNom() + " " + visiteur.getPrenom());
    }

    public List<Visiteur> getVisiteursDuJour() {
        return repository.findVisiteursDuJour();
    }

    public Visiteur validerSortie(Long id) {
        Visiteur visiteur = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Visiteur non trouvé avec l'ID : " + id));

        visiteur.setDateSortie(LocalDateTime.now());
        Visiteur saved = repository.save(visiteur);

        enregistrerActivite("Validation Sortie", "Validation de la sortie du visiteur " + visiteur.getNom() + " " + visiteur.getPrenom());

        return saved;
    }

    public Visiteur modifierVisiteur(Long id, Visiteur nouveau) {
        Visiteur existant = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Visiteur non trouvé pour la modification"));

        existant.setNom(nouveau.getNom());
        existant.setPrenom(nouveau.getPrenom());
        existant.setCin(nouveau.getCin());
        existant.setGenre(nouveau.getGenre());
        existant.setDestination(nouveau.getDestination());
        existant.setTelephone(nouveau.getTelephone());
        existant.setTypeVisiteur(nouveau.getTypeVisiteur());
        existant.setMatricule(nouveau.getMatricule());

        Visiteur saved = repository.save(existant);

        enregistrerActivite("Modification Visiteur", "Modification du visiteur " + existant.getNom() + " " + existant.getPrenom());

        return saved;
    }

    private void enregistrerActivite(String action, String description) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("🔍 AUTHENTICATION: " + authentication);

        String email = null;

        if (authentication.getPrincipal() instanceof org.springframework.security.oauth2.jwt.Jwt jwt) {
            email = jwt.getSubject(); // 🔑 C’est ici qu’on récupère l’email
            System.out.println("📧 Email extrait du JWT : " + email);
        }

        if (email == null || email.isEmpty()) {
            System.err.println("❌ Aucun utilisateur connecté (JWT) - action non journalisée.");
            return;
        }

        Person agent = personService.findByEmail(email);

        if (agent == null) {
            System.err.println("❌ Personne non trouvée pour l'email: " + email);
            return;
        }

        ActiviteAgent activite = new ActiviteAgent();
        activite.setAction(action);
        activite.setDescription(description);
        activite.setAgent(agent);
        activite.setDateAction(LocalDateTime.now());

        activiteAgentService.saveActivity(activite);

        historiqueService.enregistrerAction(agent.getNom() + " " + agent.getPrenom(), action + " : " + description);
    }

}
