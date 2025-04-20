package org.sid.renaultvisiteursbackend.Service;

import lombok.AllArgsConstructor;
import org.sid.renaultvisiteursbackend.Dto.ResponsableDTO;
import org.sid.renaultvisiteursbackend.Entity.Responsable;
import org.sid.renaultvisiteursbackend.Mapper.ResponsableMapper;
import org.sid.renaultvisiteursbackend.Repository.ResponsableRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ResponsableService {

    private final ResponsableRepository repository;
    private final ResponsableMapper mapper;

    public ResponsableDTO createResponsable(ResponsableDTO dto) {
        Responsable saved = repository.save(mapper.toEntity(dto));
        return mapper.toDTO(saved);
    }

    public List<ResponsableDTO> getAllResponsables() {
        return repository.findAll()
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }
}