package org.sid.renaultvisiteursbackend.Mapper;

import org.sid.renaultvisiteursbackend.Dto.AdminDTO;
import org.sid.renaultvisiteursbackend.Entity.Admin;
import org.springframework.stereotype.Component;

@Component
public class AdminMapper {

    public AdminDTO toDTO(Admin admin) {
        AdminDTO dto = new AdminDTO();
        dto.setId(admin.getId());
        dto.setLogin(admin.getEmail());
        dto.setPassword(admin.getPassword());
        return dto;
    }

    public Admin toEntity(AdminDTO dto) {
        Admin admin = new Admin();
        admin.setId(dto.getId());
        admin.setEmail(dto.getLogin());
        admin.setPassword(dto.getPassword());
        return admin;
    }
}