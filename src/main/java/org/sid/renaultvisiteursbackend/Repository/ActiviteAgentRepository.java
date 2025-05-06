package org.sid.renaultvisiteursbackend.Repository;

import org.sid.renaultvisiteursbackend.Entity.ActiviteAgent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ActiviteAgentRepository extends JpaRepository<ActiviteAgent, Long> {
    List<ActiviteAgent> findByAgentId(Long agentId);
}
