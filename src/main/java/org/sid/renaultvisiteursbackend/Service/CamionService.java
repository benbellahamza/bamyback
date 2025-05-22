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
        Livraison livraison = Livraison.builder()
                .destination(dto.getDestination())
                .nomChauffeurSortie(dto.getNomChauffeurSortie())
                .prenomChauffeurSortie(dto.getPrenomChauffeurSortie())
                .cinChauffeurSortie(dto.getCinChauffeurSortie())
                .entreprise(dto.getEntreprise())
                .dateSortie(LocalDateTime.now())
                .build();
        camion.setLivraison(livraison);
        camion.setDateSortie(livraison.getDateSortie());
        return camionRepo.save(camion);
    }
    public Optional<Camion> getCamion(String numeroChassis) {
        return camionRepo.findByNumeroChassis(numeroChassis);
    }
    public List<Camion> getAllCamions() {
        return camionRepo.findAll();
    }
}
