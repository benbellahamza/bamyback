package org.sid.renaultvisiteursbackend.Repository;

import org.sid.renaultvisiteursbackend.Entity.Visiteur;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VisiteurRepository extends JpaRepository<Visiteur, Long> {
}
