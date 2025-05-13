package org.sid.renaultvisiteursbackend.Controller;

import org.sid.renaultvisiteursbackend.Dto.AdminDTO;
import org.sid.renaultvisiteursbackend.Dto.CreateUserRequest;
import org.sid.renaultvisiteursbackend.Dto.PasswordChangeRequest;
import org.sid.renaultvisiteursbackend.Dto.ResetPasswordRequest;
import org.sid.renaultvisiteursbackend.Entity.HistoriqueAction;
import org.sid.renaultvisiteursbackend.Repository.PersonRepository;
import org.sid.renaultvisiteursbackend.Service.AdminService;
import org.sid.renaultvisiteursbackend.Service.HistoriqueService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admins")
@CrossOrigin("*")
public class AdminController {

    private final AdminService adminService;
    private final HistoriqueService historiqueService;

    public AdminController(AdminService adminService, PersonRepository personRepository, HistoriqueService historiqueService) {
        this.adminService = adminService;
        this.historiqueService = historiqueService;
    }

    @PostMapping
    public AdminDTO createAdmin(@RequestBody AdminDTO dto) {
        return adminService.createAdmin(dto);
    }

    @GetMapping
    public List<AdminDTO> getAllAdmins() {
        return adminService.getAllAdmins();
    }

    @PostMapping("/ajouter-utilisateur")
    public CreateUserRequest ajouterUtilisateur(@RequestBody CreateUserRequest request) {
        return adminService.ajouterUtilisateur(request);
    }

    @GetMapping("/utilisateurs")
    public List<UserResponse> getAllUtilisateurs() {
        return adminService.getUtilisateursAvecRole();
    }

    @PutMapping("/modifier-utilisateur/{id}")
    public void modifierUtilisateur(@PathVariable Long id, @RequestBody CreateUserRequest request) {
        adminService.modifierUtilisateur(id, request);
    }

    @PatchMapping("/reinitialiser-motdepasse/{id}")
    public void reinitialiserMotDePasse(@PathVariable Long id, @RequestBody ResetPasswordRequest resetRequest) {
        adminService.reinitialiserMotDePasse(id, resetRequest.getNewPassword());
    }

    @PatchMapping("/desactiver-utilisateur/{id}")
    public void desactiverUtilisateur(@PathVariable Long id) {
        adminService.desactiverUtilisateur(id);
    }

    // ✅ Nouvelle route : afficher historique des actions des agents
    @GetMapping("/historique")
    public List<HistoriqueAction> getHistorique() {
        return historiqueService.getAllActions();
    }
    @PostMapping("/changer-motdepasse")
    public void changerMotDePasse(@RequestBody PasswordChangeRequest request) {
        adminService.changerMotDePasse(
                request.getEmail(),
                request.getAncienMotDePasse(),
                request.getNouveauMotDePasse()
        );
    }
    public record UserResponse(
            Long id,
            String nom,
            String prenom,
            String email,
            String role,
            boolean actif
    ) {}
}
