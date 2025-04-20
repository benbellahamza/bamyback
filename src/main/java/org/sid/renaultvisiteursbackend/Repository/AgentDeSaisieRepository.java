package org.sid.renaultvisiteursbackend.Repository;

import org.sid.renaultvisiteursbackend.Entity.AgentDeSaisie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgentDeSaisieRepository extends JpaRepository<AgentDeSaisie, Long> {
}
