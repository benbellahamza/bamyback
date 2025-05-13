package org.sid.renaultvisiteursbackend.Service;

import org.sid.renaultvisiteursbackend.Controller.AdminController;
import org.sid.renaultvisiteursbackend.Dto.AdminDTO;
import org.sid.renaultvisiteursbackend.Dto.CreateUserRequest;
import org.sid.renaultvisiteursbackend.Entity.Admin;
import org.sid.renaultvisiteursbackend.Entity.AgentDeSaisie;
import org.sid.renaultvisiteursbackend.Entity.Person;
import org.sid.renaultvisiteursbackend.Entity.Responsable;
import org.sid.renaultvisiteursbackend.Mapper.AdminMapper;
import org.sid.renaultvisiteursbackend.Repository.AdminRepository;
import org.sid.renaultvisiteursbackend.Repository.PersonRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminService {

    private final AdminRepository adminRepository;
    private final AdminMapper adminMapper;
    private final PersonRepository personRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminService(AdminRepository adminRepository,
                        AdminMapper adminMapper,
                        PersonRepository personRepository,
                        PasswordEncoder passwordEncoder) {
        this.adminRepository = adminRepository;
        this.adminMapper = adminMapper;
        this.personRepository = personRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public AdminDTO createAdmin(AdminDTO dto) {
        Admin saved = adminRepository.save(adminMapper.toEntity(dto));
        return adminMapper.toDTO(saved);
    }

    public List<AdminDTO> getAllAdmins() {
        return adminRepository.findAll()
                .stream()
                .map(adminMapper::toDTO)
                .collect(Collectors.toList());
    }

    public CreateUserRequest ajouterUtilisateur(CreateUserRequest request) {
        if (personRepository.findByEmail(request.getEmail()) != null) {
            throw new RuntimeException("Un utilisateur avec cet email existe déjà.");
        }

        Person user;
        switch (request.getRole().toUpperCase()) {
            case "ADMIN":
                user = new Admin();
                break;
            case "AGENT":
                user = new AgentDeSaisie();
                break;
            case "RESPONSABLE":
                user = new Responsable();
                break;
            default:
                throw new RuntimeException("Rôle non valide : " + request.getRole());
        }

        user.setNom(request.getNom());
        user.setPrenom(request.getPrenom());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole().toUpperCase());
        user.setActif(true);

        personRepository.save(user);
        return request;
    }

    public void modifierUtilisateur(Long id, CreateUserRequest request) {
        Person utilisateur = personRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        utilisateur.setNom(request.getNom());
        utilisateur.setPrenom(request.getPrenom());
        utilisateur.setEmail(request.getEmail());
        utilisateur.setRole(request.getRole().toUpperCase());

        personRepository.save(utilisateur);
    }

    public void reinitialiserMotDePasse(Long id, String newPassword) {
        Person utilisateur = personRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        utilisateur.setPassword(passwordEncoder.encode(newPassword));
        personRepository.save(utilisateur);
    }

    public void desactiverUtilisateur(Long id) {
        Person utilisateur = personRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));

        if ("ADMIN".equalsIgnoreCase(utilisateur.getRole())) {
            throw new RuntimeException("Impossible de désactiver un administrateur.");
        }

        utilisateur.setActif(!utilisateur.isActif());
        personRepository.save(utilisateur);
    }

    public List<AdminController.UserResponse> getUtilisateursAvecRole() {
        return personRepository.findAll().stream()
                .filter(person -> person.getRole() != null)
                .map(p -> new AdminController.UserResponse(
                        p.getId(),
                        p.getNom(),
                        p.getPrenom(),
                        p.getEmail(),
                        p.getRole(),
                        p.isActif()
                ))
                .collect(Collectors.toList());
    }

    public void changerMotDePasse(String email, String ancien, String nouveau) {
        if (ancien == null || nouveau == null) {
            throw new IllegalArgumentException("Les champs mot de passe ne peuvent pas être null");
        }

        Person utilisateur = personRepository.findByEmail(email);
        if (utilisateur == null) {
            throw new RuntimeException("Utilisateur introuvable.");
        }

        if (!passwordEncoder.matches(ancien, utilisateur.getPassword())) {
            throw new RuntimeException("Ancien mot de passe incorrect.");
        }

        utilisateur.setPassword(passwordEncoder.encode(nouveau));
        personRepository.save(utilisateur);
    }
}
