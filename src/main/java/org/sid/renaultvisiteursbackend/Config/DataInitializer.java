package org.sid.renaultvisiteursbackend.Config;

import org.sid.renaultvisiteursbackend.Entity.Admin;
import org.sid.renaultvisiteursbackend.Repository.AdminRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(AdminRepository adminRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            String adminEmail = "admin@gmail.com";

            if (adminRepository.findByEmail(adminEmail) == null) {
                Admin admin = new Admin();
                admin.setNom("Admin");
                admin.setPrenom("Principal");
                admin.setEmail(adminEmail);
                admin.setRole("ADMIN");
                admin.setPassword(passwordEncoder.encode("123456"));
                admin.setActif(true);

                adminRepository.save(admin);
                System.out.println("✅ Admin ajouté automatiquement au démarrage.");
            } else {
                System.out.println("ℹ️ Admin déjà existant, aucune action.");
            }
        };
    }
}
