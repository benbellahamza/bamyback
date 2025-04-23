package org.sid.renaultvisiteursbackend.Repository;

import org.sid.renaultvisiteursbackend.Entity.Visiteur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VisiteurRepository extends JpaRepository<Visiteur, Long> {
    @Query("SELECT v FROM Visiteur v WHERE DATE(v.dateEntree) = CURRENT_DATE")
    List<Visiteur> findVisiteursDuJour();

}
