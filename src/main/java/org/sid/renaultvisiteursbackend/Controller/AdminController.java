package org.sid.renaultvisiteursbackend.Controller;

import org.sid.renaultvisiteursbackend.Dto.AdminDTO;
import org.sid.renaultvisiteursbackend.Dto.CreateUserRequest;
import org.sid.renaultvisiteursbackend.Dto.ResetPasswordRequest;
import org.sid.renaultvisiteursbackend.Entity.Person;
import org.sid.renaultvisiteursbackend.Service.AdminService;
import org.sid.renaultvisiteursbackend.Repository.PersonRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admins")
@CrossOrigin("*")
public class AdminController {

    private final AdminService adminService;
    private final PersonRepository personRepository;

    public AdminController(AdminService adminService, PersonRepository personRepository) {
        this.adminService = adminService;
        this.personRepository = personRepository;
    }

    /**
     * ✅ Créer un nouvel Admin
     */
    @PostMapping
    public AdminDTO createAdmin(@RequestBody AdminDTO dto) {
        return adminService.createAdmin(dto);
    }

    /**
     * ✅ Liste de tous les Admins
     */
    @GetMapping
    public List<AdminDTO> getAllAdmins() {
        return adminService.getAllAdmins();
    }

    /**
     * ✅ Ajouter un utilisateur (Admin / Agent / Responsable)
     */
    @PostMapping("/ajouter-utilisateur")
    public CreateUserRequest ajouterUtilisateur(@RequestBody CreateUserRequest request) {
        return adminService.ajouterUtilisateur(request);
    }

    /**
     * ✅ Lister tous les utilisateurs (sans password)
     */
    @GetMapping("/utilisateurs")
    public List<UserResponse> getAllUtilisateurs() {
        return personRepository.findAll()
                .stream()
                .map(person -> new UserResponse(
                        person.getId(),
                        person.getNom(),
                        person.getPrenom(),
                        person.getEmail(),
                        person.getRole()
                ))
                .collect(Collectors.toList());
    }

    /**
     * ✅ Modifier les informations d'un utilisateur
     */
    @PutMapping("/modifier-utilisateur/{id}")
    public void modifierUtilisateur(@PathVariable Long id, @RequestBody CreateUserRequest request) {
        adminService.modifierUtilisateur(id, request);
    }

    /**
     * ✅ Réinitialiser le mot de passe d'un utilisateur
     */
    @PatchMapping("/reinitialiser-motdepasse/{id}")
    public void reinitialiserMotDePasse(@PathVariable Long id, @RequestBody ResetPasswordRequest resetRequest) {
        adminService.reinitialiserMotDePasse(id, resetRequest.getNewPassword());
    }

    /**
     * ✅ Classe interne pour sécuriser la réponse utilisateur
     */
    public record UserResponse(
            Long id,
            String nom,
            String prenom,
            String email,
            String role
    ) {}
}
