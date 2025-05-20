package org.sid.renaultvisiteursbackend.Repository;

import org.sid.renaultvisiteursbackend.Entity.Camion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CamionRepository extends JpaRepository<Camion, Long> {
    Optional<Camion> findByNumeroChassis(String numeroChassis);
}
