package org.sid.renaultvisiteursbackend.Entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
public class ActiviteAgent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Person agent; // L'agent qui a fait l'action

    private String action; // Ajout, Modification, Suppression

    private String description; // Détail de l'action

    private LocalDateTime dateAction; // Date de l'action
}
