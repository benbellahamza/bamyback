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
    private Person agent;
    private String action;
    private String description;
    private LocalDateTime dateAction;
}
