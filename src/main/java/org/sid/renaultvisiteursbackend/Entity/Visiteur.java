package org.sid.renaultvisiteursbackend.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Visiteur  extends Person {
    @Column(name = "genre")
    private String genre;
    private String cin;
    private String destination;
    private String telephone;
    private String matricule;
    private String typeVisiteur;
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime dateEntree;
    private LocalDateTime dateSortie;
}
