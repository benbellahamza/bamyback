package org.sid.renaultvisiteursbackend.Mapper;

import org.sid.renaultvisiteursbackend.Dto.AgentDeSaisieDTO;
import org.sid.renaultvisiteursbackend.Entity.AgentDeSaisie;
import org.springframework.stereotype.Component;

@Component
public class AgentDeSaisieMapper {

    public AgentDeSaisieDTO toDTO(AgentDeSaisie agent) {
        AgentDeSaisieDTO dto = new AgentDeSaisieDTO();
        dto.setId(agent.getId());
        dto.setLogin(agent.getEmail());
        dto.setPassword(agent.getPassword());
        return dto;
    }

    public AgentDeSaisie toEntity(AgentDeSaisieDTO dto) {
        AgentDeSaisie agent = new AgentDeSaisie();
        agent.setId(dto.getId());
        agent.setEmail(dto.getLogin());
        agent.setPassword(dto.getPassword());
        return agent;
    }
}