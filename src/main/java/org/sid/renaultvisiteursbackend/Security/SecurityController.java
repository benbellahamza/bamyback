package org.sid.renaultvisiteursbackend.Security;

import org.sid.renaultvisiteursbackend.Dto.PersonRequestLogin;
import org.sid.renaultvisiteursbackend.Dto.PersonRequestRegister;
import org.sid.renaultvisiteursbackend.Entity.*;
import org.sid.renaultvisiteursbackend.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RequestMapping("/auth")
@RestController
@EnableMethodSecurity(prePostEnabled = true)
@CrossOrigin("*")
public class SecurityController {

    @Autowired
    private JwtEncoder jwtEncoder;
    @Autowired
    private JwtDecoder jwtDecoder;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private AgentDeSaisieRepository agentDeSaisieRepository;
    @Autowired
    private ResponsableRepository responsableRepository;
    @Autowired
    private VisiteurRepository visiteurRepository;
    @Autowired
    private PersonRepository personRepository;

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody PersonRequestLogin personRequestLogin) {
        Instant now = Instant.now();
        Map<String, String> tokens = new HashMap<>();

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            personRequestLogin.getEmail(),
                            personRequestLogin.getPassword()
                    )
            );

            String email = authentication.getName();
            String scope = authentication.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.joining(" ")); // Ex: "ADMIN"

            // 🔐 Construction du token
            JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder()
                    .issuedAt(now)
                    .expiresAt(now.plus(Duration.ofDays(30)))
                    .subject(email) // important pour retrouver l'utilisateur
                    .claim("scope", scope)
                    .build();

            JwtEncoderParameters jwtEncoderParameters = JwtEncoderParameters.from(
                    JwsHeader.with(MacAlgorithm.HS256).build(),
                    jwtClaimsSet
            );

            String jwt = jwtEncoder.encode(jwtEncoderParameters).getTokenValue();

            tokens.put("access-token", jwt);
            tokens.put("role", scope); // utilisé par Angular

            return ResponseEntity.ok(tokens);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    //new modification f securitycontroller

    @PostMapping("/update-password")
    public ResponseEntity<Map<String, String>> updatePassword(@RequestBody Map<String, String> body, Authentication authentication) {
        try {
            String email = authentication.getName(); // récupère l'utilisateur connecté
            String ancien = body.get("ancienMotDePasse");
            String nouveau = body.get("nouveauMotDePasse");

            Person user = personRepository.findByEmail(email);

            if (user == null || !passwordEncoder.matches(ancien, user.getPassword())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of(
                        "error", "Ancien mot de passe incorrect"
                ));
            }

            user.setPassword(passwordEncoder.encode(nouveau));
            personRepository.save(user);

            return ResponseEntity.ok(Map.of("message", "Mot de passe mis à jour avec succès"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "error", "Erreur lors de la mise à jour"
            ));
        }
    }




    @PostMapping("/register")
    public Map<String, String> register(@RequestBody PersonRequestRegister request) {
        try {
            Person existing = personRepository.findByEmail(request.getEmail());
            if (existing != null) {
                throw new RuntimeException("This User is Already Registered !");
            }

            String role = request.getRole().toUpperCase();
            Person user;

            switch (role) {
                case "ADMIN":
                    Admin admin = new Admin();
                    admin.setEmail(request.getEmail());
                    admin.setPassword(passwordEncoder.encode(request.getPassword()));
                    admin.setRole(role);
                    admin.setNom(request.getNom());
                    admin.setPrenom(request.getPrenom());
                    user = adminRepository.save(admin);
                    break;
                case "AGENT":
                    AgentDeSaisie agent = new AgentDeSaisie();
                    agent.setEmail(request.getEmail());
                    agent.setPassword(passwordEncoder.encode(request.getPassword()));
                    agent.setRole(role);
                    agent.setNom(request.getNom());
                    agent.setPrenom(request.getPrenom());
                    user = agentDeSaisieRepository.save(agent);
                    break;
                case "RESPONSABLE":
                    Responsable responsable = new Responsable();
                    responsable.setEmail(request.getEmail());
                    responsable.setPassword(passwordEncoder.encode(request.getPassword()));
                    responsable.setRole(role);
                    responsable.setNom(request.getNom());
                    responsable.setPrenom(request.getPrenom());
                    user = responsableRepository.save(responsable);
                    break;
                case "VISITEUR":
                    Visiteur visiteur = new Visiteur();
                    visiteur.setEmail(request.getEmail());
                    visiteur.setPassword(passwordEncoder.encode(request.getPassword()));
                    visiteur.setRole(role);
                    visiteur.setNom(request.getNom());
                    visiteur.setPrenom(request.getPrenom());
                    user = visiteurRepository.save(visiteur);
                    break;
                default:
                    throw new IllegalArgumentException("Rôle invalide : " + role);
            }

            // 🔐 Génération du token
            Instant now = Instant.now();
            JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder()
                    .issuedAt(now)
                    .expiresAt(now.plus(Duration.ofDays(30)))
                    .subject(user.getEmail())
                    .claim("scope", role)
                    .build();

            JwtEncoderParameters jwtEncoderParameters = JwtEncoderParameters.from(
                    JwsHeader.with(MacAlgorithm.HS256).build(),
                    jwtClaimsSet
            );

            String jwt = jwtEncoder.encode(jwtEncoderParameters).getTokenValue();
            return Map.of("access-token", jwt);

        } catch (Exception e) {
            return Map.of("error", e.getMessage());
        }
    }
}
