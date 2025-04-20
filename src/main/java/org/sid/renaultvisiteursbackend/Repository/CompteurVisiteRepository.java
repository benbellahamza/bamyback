package org.sid.renaultvisiteursbackend.Repository;

import org.sid.renaultvisiteursbackend.Entity.CompteurVisite;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.Optional;

public interface CompteurVisiteRepository extends JpaRepository<CompteurVisite, Long> {
    Optional<CompteurVisite> findByDate(LocalDate date);
}
