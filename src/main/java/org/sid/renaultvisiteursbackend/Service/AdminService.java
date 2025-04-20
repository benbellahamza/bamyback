package org.sid.renaultvisiteursbackend.Service;

import org.sid.renaultvisiteursbackend.Dto.AdminDTO;
import org.sid.renaultvisiteursbackend.Entity.Admin;
import org.sid.renaultvisiteursbackend.Mapper.AdminMapper;
import org.sid.renaultvisiteursbackend.Repository.AdminRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminService {

    private final AdminRepository adminRepository;
    private final AdminMapper adminMapper;

    // ✅ Constructeur explicite pour injection
    public AdminService(AdminRepository adminRepository, AdminMapper adminMapper) {
        this.adminRepository = adminRepository;
        this.adminMapper = adminMapper;
    }

    public AdminDTO createAdmin(AdminDTO dto) {
        Admin saved = adminRepository.save(adminMapper.toEntity(dto));
        return adminMapper.toDTO(saved);
    }

    public List<AdminDTO> getAllAdmins() {
        return adminRepository.findAll()
                .stream()
                .map(adminMapper::toDTO)
                .collect(Collectors.toList());
    }
}
