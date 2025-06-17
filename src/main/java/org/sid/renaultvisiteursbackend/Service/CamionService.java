package org.sid.renaultvisiteursbackend.Service;

import lombok.RequiredArgsConstructor;
import org.sid.renaultvisiteursbackend.Dto.EntreeCamionDTO;
import org.sid.renaultvisiteursbackend.Dto.SortieCamionDTO;
import org.sid.renaultvisiteursbackend.Entity.Camion;
import org.sid.renaultvisiteursbackend.Entity.Chauffeur;
import org.sid.renaultvisiteursbackend.Entity.Livraison;
import org.sid.renaultvisiteursbackend.Repository.CamionRepository;
import org.sid.renaultvisiteursbackend.Repository.LivraisonRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CamionService {
    private final CamionRepository camionRepo;
    private final LivraisonRepository livraisonRepo;

    public Camion enregistrerEntree(EntreeCamionDTO dto) {
        Chauffeur chauffeur = Chauffeur.builder()
                .nom(dto.getNomChauffeur())
                .prenom(dto.getPrenomChauffeur())
                .build();
        Camion camion = Camion.builder()
                .numeroChassis(dto.getNumeroChassis())
                .marque(dto.getMarque())
                .modele(dto.getModele())
                .chauffeurEntree(chauffeur)
                .dateEntree(LocalDateTime.now())
                .build();
        return camionRepo.save(camion);
    }

    public Camion enregistrerSortie(String numeroChassis, SortieCamionDTO dto) {
        Camion camion = camionRepo.findByNumeroChassis(numeroChassis)
                .orElseThrow(() -> new RuntimeException("Camion non trouvé"));

        // 🔧 NORMALISATION DE LA DESTINATION - VOICI LA CORRECTION !
        String destinationNormalisee = normaliserDestination(dto.getDestination());

        Livraison livraison = Livraison.builder()
                .destination(destinationNormalisee) // ✅ Utiliser la destination normalisée
                .nomChauffeurSortie(dto.getNomChauffeurSortie())
                .prenomChauffeurSortie(dto.getPrenomChauffeurSortie())
                .cinChauffeurSortie(dto.getCinChauffeurSortie())
                .entreprise(dto.getEntreprise())
                .dateSortie(LocalDateTime.now())
                .build();

        camion.setLivraison(livraison);
        camion.setDateSortie(livraison.getDateSortie());

        // 🔧 DEBUG - Afficher les valeurs pour vérifier
        System.out.println("🔍 DESTINATION REÇUE: " + dto.getDestination());
        System.out.println("🔍 DESTINATION NORMALISÉE: " + destinationNormalisee);

        return camionRepo.save(camion);
    }

    // 🆕 MÉTHODE POUR NORMALISER LES DESTINATIONS
    private String normaliserDestination(String destination) {
        if (destination == null || destination.trim().isEmpty()) {
            return "PARK"; // Valeur par défaut
        }

        String dest = destination.toLowerCase().trim();

        // Normalisation selon les valeurs envoyées par Angular
        switch (dest) {
            case "park":
                return "PARK";
            case "livraison finale":
                return "LIVRAISON_FINALE";
            case "prestation extérieure":
                return "PRESTATION_EXTERIEURE";
            default:
                // Fallback intelligent
                if (dest.contains("park") || dest.contains("parking")) {
                    return "PARK";
                } else if (dest.contains("livraison") || dest.contains("final")) {
                    return "LIVRAISON_FINALE";
                } else if (dest.contains("prestation") || dest.contains("extérieur")) {
                    return "PRESTATION_EXTERIEURE";
                } else {
                    return "PARK"; // Valeur par défaut
                }
        }
    }

    public Optional<Camion> getCamion(String numeroChassis) {
        return camionRepo.findByNumeroChassis(numeroChassis);
    }

    public List<Camion> getAllCamions() {
        return camionRepo.findAll();
    }

    // Méthode de modification existante...
    public Camion modifierCamion(String numeroChassis, Map<String, String> modifications) {
        Camion camion = camionRepo.findByNumeroChassis(numeroChassis)
                .orElseThrow(() -> new RuntimeException("Camion non trouvé avec le châssis : " + numeroChassis));

        if (modifications.containsKey("marque") && modifications.get("marque") != null) {
            camion.setMarque(modifications.get("marque"));
        }

        if (modifications.containsKey("modele") && modifications.get("modele") != null) {
            camion.setModele(modifications.get("modele"));
        }

        if (modifications.containsKey("nomChauffeur") && modifications.get("nomChauffeur") != null) {
            if (camion.getChauffeurEntree() != null) {
                camion.getChauffeurEntree().setNom(modifications.get("nomChauffeur"));
            } else {
                Chauffeur chauffeur = Chauffeur.builder()
                        .nom(modifications.get("nomChauffeur"))
                        .prenom(camion.getChauffeurEntree() != null ?
                                camion.getChauffeurEntree().getPrenom() : "")
                        .build();
                camion.setChauffeurEntree(chauffeur);
            }
        }

        if (modifications.containsKey("prenomChauffeur") && modifications.get("prenomChauffeur") != null) {
            if (camion.getChauffeurEntree() != null) {
                camion.getChauffeurEntree().setPrenom(modifications.get("prenomChauffeur"));
            } else {
                Chauffeur chauffeur = Chauffeur.builder()
                        .nom(camion.getChauffeurEntree() != null ?
                                camion.getChauffeurEntree().getNom() : "")
                        .prenom(modifications.get("prenomChauffeur"))
                        .build();
                camion.setChauffeurEntree(chauffeur);
            }
        }

        return camionRepo.save(camion);
    }
}