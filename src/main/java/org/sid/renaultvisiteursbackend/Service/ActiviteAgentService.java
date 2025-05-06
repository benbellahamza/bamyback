package org.sid.renaultvisiteursbackend.Service;

import org.sid.renaultvisiteursbackend.Entity.ActiviteAgent;
import org.sid.renaultvisiteursbackend.Repository.ActiviteAgentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActiviteAgentService {

    private final ActiviteAgentRepository activiteAgentRepository;

    public ActiviteAgentService(ActiviteAgentRepository activiteAgentRepository) {
        this.activiteAgentRepository = activiteAgentRepository;
    }

    public List<ActiviteAgent> getAllActivities() {
        return activiteAgentRepository.findAll();
    }

    public List<ActiviteAgent> getActivitiesByAgent(Long agentId) {
        return activiteAgentRepository.findByAgentId(agentId);
    }

    public ActiviteAgent saveActivity(ActiviteAgent activite) {
        return activiteAgentRepository.save(activite);
    }
}
