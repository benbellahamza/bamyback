package org.sid.renaultvisiteursbackend.Security;


import org.apache.poi.ss.formula.atp.Switch;
import org.sid.renaultvisiteursbackend.Dto.PersonRequestLogin;
import org.sid.renaultvisiteursbackend.Dto.PersonRequestRegister;
import org.sid.renaultvisiteursbackend.Entity.*;
import org.sid.renaultvisiteursbackend.Entity.Admin;
import org.sid.renaultvisiteursbackend.Repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.Instant;
import java.util.Collection;
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

    JwtClaimsSet jwtClaimsSet,jwtClaimsSetRefresh;


    @Autowired

    private PasswordEncoder passwordEncoder;




    @Autowired

    AdminRepository adminRepository;

    @Autowired
    AgentDeSaisieRepository agentDeSaisieRepository;

    @Autowired
    VisiteurRepository visiteurRepository;

    @Autowired
    PersonRepository personRepository;


    @Autowired
    ResponsableRepository responsableRepository;




    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody PersonRequestLogin personRequestLogin) throws Exception {
        Instant now = Instant.now();
        Map<String, String> tokens = new HashMap<>();
        String subject = "";
        String scope = "";

        try {

                Authentication authenticate = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(personRequestLogin.getEmail(), personRequestLogin.getPassword()));
                subject = personRequestLogin.getEmail();
                scope = authenticate.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.joining(" "));


            JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder()
                    .issuedAt(now)
                    .expiresAt(now.plus(Duration.ofDays(31)))
                    .subject(subject)
                    .claim("scope", scope)
                    .build();

            JwtEncoderParameters jwtEncoderParameters = JwtEncoderParameters.from(
                    JwsHeader.with(MacAlgorithm.HS256).build(),
                    jwtClaimsSet
            );
            String jwt = jwtEncoder.encode(jwtEncoderParameters).getTokenValue();
            tokens.put("access-token", jwt);
            tokens.put("role", scope);



            return ResponseEntity.ok(tokens);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }


    @PostMapping("/register")
    public Map<String, String> register(@RequestBody PersonRequestRegister personRequestRegister) {
        System.out.println("Register");
        System.out.println(personRequestRegister.toString());

        try {
            // 1. Vérifie si l'utilisateur existe
            Person existing = personRepository.findByEmail(personRequestRegister.getEmail());
            if (existing != null) {
                throw new RuntimeException("This User is Already Registered !");
            }

            String role = personRequestRegister.getRole().toUpperCase();
            Person user = null;

            // 2. Crée et sauvegarde l'utilisateur en fonction du rôle
            switch (role) {
                case "ADMIN":
                    System.out.println("Admin");
                    Admin admin = new Admin();
                    admin.setEmail(personRequestRegister.getEmail());
                    admin.setPassword(passwordEncoder.encode(personRequestRegister.getPassword()));
                    admin.setRole(role);
                    admin.setNom(personRequestRegister.getNom());
                    admin.setPrenom(personRequestRegister.getPrenom());
                    adminRepository.save(admin);
                    user = admin;
                    break;
                case "AGENT":
                    AgentDeSaisie agent = new AgentDeSaisie();
                    agent.setEmail(personRequestRegister.getEmail());
                    agent.setPassword(passwordEncoder.encode(personRequestRegister.getPassword()));
                    agent.setRole(role);
                    agent.setNom(personRequestRegister.getNom());
                    agent.setPrenom(personRequestRegister.getPrenom());
                    agentDeSaisieRepository.save(agent);
                    user = agent;
                    break;
                case "VISITEUR":
                    Visiteur visiteur = new Visiteur();
                    visiteur.setEmail(personRequestRegister.getEmail());
                    visiteur.setPassword(passwordEncoder.encode(personRequestRegister.getPassword()));
                    visiteur.setRole(role);
                    visiteur.setNom(personRequestRegister.getNom());
                    visiteur.setPrenom(personRequestRegister.getPrenom());
                    visiteurRepository.save(visiteur);
                    user = visiteur;
                    break;
                case "RESPONSABLE":
                    Responsable resp = new Responsable();
                    resp.setEmail(personRequestRegister.getEmail());
                    resp.setPassword(passwordEncoder.encode(personRequestRegister.getPassword()));
                    resp.setRole(role);
                    resp.setNom(personRequestRegister.getNom());
                    resp.setPrenom(personRequestRegister.getPrenom());
                    responsableRepository.save(resp);
                    user = resp;
                    break;
                default:
                    throw new IllegalArgumentException("Rôle invalide : " + role);
            }

            // 3. Génère le token JWT
            PersonAuth personAuth = new PersonAuth(user);
            Instant instant = Instant.now();
            jwtClaimsSet = JwtClaimsSet.builder()
                    .issuedAt(instant)
                    .expiresAt(instant.plus(Duration.ofMinutes(10)))
                    .subject(personAuth.getUsername())
                    .claim("scope", personAuth.getAuthorities())
                    .build();
            JwtEncoderParameters jwtEncoderParameters = JwtEncoderParameters.from(
                    JwsHeader.with(MacAlgorithm.HS256).build(),
                    jwtClaimsSet
            );
            String jwt = jwtEncoder.encode(jwtEncoderParameters).getTokenValue();

            return Map.of("access-token", jwt);

        } catch (Exception e) {
            e.printStackTrace();
            return Map.of("error", e.getMessage());
        }
    }



}
