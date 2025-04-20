package org.sid.renaultvisiteursbackend.Service;

import lombok.AllArgsConstructor;
import org.sid.renaultvisiteursbackend.Dto.AgentDeSaisieDTO;
import org.sid.renaultvisiteursbackend.Entity.AgentDeSaisie;
import org.sid.renaultvisiteursbackend.Mapper.AgentDeSaisieMapper;
import org.sid.renaultvisiteursbackend.Repository.AgentDeSaisieRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AgentDeSaisieService {

    private final AgentDeSaisieRepository repository;
    private final AgentDeSaisieMapper mapper;

    public AgentDeSaisieDTO createAgent(AgentDeSaisieDTO dto) {
        AgentDeSaisie saved = repository.save(mapper.toEntity(dto));
        return mapper.toDTO(saved);
    }

    public List<AgentDeSaisieDTO> getAllAgents() {
        return repository.findAll()
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }
}
