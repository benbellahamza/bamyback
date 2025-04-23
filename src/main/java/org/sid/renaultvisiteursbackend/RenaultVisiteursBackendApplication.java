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
        };
    }
}
