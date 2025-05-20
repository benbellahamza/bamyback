package org.sid.renaultvisiteursbackend.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Livraison {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String destination; // park, livraison finale, etc.

    private String nomChauffeurSortie;
    private String prenomChauffeurSortie;
    private String cinChauffeurSortie;

    private String entreprise;

    private LocalDateTime dateSortie;
}