package org.sid.renaultvisiteursbackend.Mapper;

import org.sid.renaultvisiteursbackend.Dto.ResponsableDTO;
import org.sid.renaultvisiteursbackend.Entity.Responsable;
import org.springframework.stereotype.Component;

@Component
public class ResponsableMapper {

    public ResponsableDTO toDTO(Responsable responsable) {
        ResponsableDTO dto = new ResponsableDTO();
        dto.setId(responsable.getId());
        dto.setLogin(responsable.getEmail());
        dto.setPassword(responsable.getPassword());
        return dto;
    }

    public Responsable toEntity(ResponsableDTO dto) {
        Responsable responsable = new Responsable();
        responsable.setId(dto.getId());
        responsable.setEmail(dto.getLogin());
        responsable.setPassword(dto.getPassword());
        return responsable;
    }
}