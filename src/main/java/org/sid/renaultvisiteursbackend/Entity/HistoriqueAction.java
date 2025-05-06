package org.sid.renaultvisiteursbackend.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "historique_action") // S'assurer que le nom de la table est correct
public class HistoriqueAction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "agent") // Correspond à la colonne dans MySQL
    private String agent;

    @Column(name = "action") // Correspond à la colonne dans MySQL
    private String action;

    @Column(name = "date_action")
    private LocalDateTime dateAction;
}
