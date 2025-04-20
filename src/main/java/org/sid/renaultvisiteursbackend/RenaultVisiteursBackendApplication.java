package org.sid.renaultvisiteursbackend;

import org.sid.renaultvisiteursbackend.Entity.*;
import org.sid.renaultvisiteursbackend.Repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;

@SpringBootApplication
public class RenaultVisiteursBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(RenaultVisiteursBackendApplication.class, args);
    }



    @Bean
    public CommandLineRunner start(AdminRepository adminRepository,
                                   AgentDeSaisieRepository agentDeSaisieRepository,
                                   ResponsableRepository responsableRepository,
                                   CompteurVisiteRepository compteurVisiteRepository, VisiteurRepository visiteurRepository) {
        return args -> {
//            adminRepository.save(new Admin(null, "El Amrani", "Youssef", "youssef.elamrani@entreprise.com", "yelamrani", "admin2024"));
//            agentDeSaisieRepository.save(new AgentDeSaisie(null, "Benali", "Sarah", "sarah.benali@entreprise.com", "sbenali", "agent2024"));
//            responsableRepository.save(new Responsable(null, "Kabbaj", "Mehdi", "mehdi.kabbaj@entreprise.com", "mkabbaj", "resp2024"));
//            visiteurRepository.save(new Visiteur(null, "Homme", "Tahiri", "Anas", "AB123456", "Département RH", "Entretien d'embauche", null));
//            compteurVisiteRepository.save(new CompteurVisite(null, 0, LocalDate.now()));
        };
    }
}
