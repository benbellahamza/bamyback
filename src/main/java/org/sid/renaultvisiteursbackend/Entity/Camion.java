package org.sid.renaultvisiteursbackend.Entity;

import jakarta.persistence.*;
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
public class Camion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String numeroChassis;

    private String marque; // ex: Renault, Forlend, Kaycenne

    private String modele; // dépend de la marque

    @OneToOne(cascade = CascadeType.ALL)
    private Chauffeur chauffeurEntree; // pour l’entrée

    private LocalDateTime dateEntree;

    @OneToOne(cascade = CascadeType.ALL)
    private Livraison livraison; // null tant que le camion n’a pas encore quitté
}
