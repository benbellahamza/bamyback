package org.sid.renaultvisiteursbackend.Controller;

import lombok.RequiredArgsConstructor;
import org.sid.renaultvisiteursbackend.Dto.AdminDTO;
import org.sid.renaultvisiteursbackend.Service.AdminService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admins")
@CrossOrigin("*")

public class AdminController {
    private final AdminService adminService;
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping
    public AdminDTO createAdmin(@RequestBody AdminDTO dto) {
        return adminService.createAdmin(dto);
    }

    @GetMapping
    public List<AdminDTO> getAllAdmins() {
        return adminService.getAllAdmins();
    }
}