package org.sid.renaultvisiteursbackend.Repository;

import org.sid.renaultvisiteursbackend.Entity.HistoriqueAction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoriqueRepository extends JpaRepository<HistoriqueAction, Long> {
}

