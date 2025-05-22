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
    private String marque;
    private String modele;
    @OneToOne(cascade = CascadeType.ALL)
    private Chauffeur chauffeurEntree;
    private LocalDateTime dateEntree;
    private LocalDateTime dateSortie;
    @OneToOne(cascade = CascadeType.ALL)
    private Livraison livraison;
}
