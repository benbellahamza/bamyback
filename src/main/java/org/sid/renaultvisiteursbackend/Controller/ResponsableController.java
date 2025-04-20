package org.sid.renaultvisiteursbackend.Controller;

import org.sid.renaultvisiteursbackend.Dto.ResponsableDTO;
import org.sid.renaultvisiteursbackend.Export.ExcelExporter;
import org.sid.renaultvisiteursbackend.Entity.Visiteur;
import org.sid.renaultvisiteursbackend.Repository.VisiteurRepository;
import org.sid.renaultvisiteursbackend.Service.ResponsableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/responsables")
@CrossOrigin("*")
public class ResponsableController {

    private final VisiteurRepository visiteurRepository;

    private final ResponsableService service;

    public ResponsableController(VisiteurRepository visiteurRepository, ResponsableService service) {
        this.visiteurRepository = visiteurRepository;
        this.service = service;
    }


    @PostMapping
    public ResponsableDTO createResponsable(@RequestBody ResponsableDTO dto) {
        return service.createResponsable(dto);
    }

    @GetMapping
    public List<ResponsableDTO> getAllResponsables() {
        return service.getAllResponsables();
    }

    // ✅ 1. Liste paginée avec tri
    @GetMapping("/visiteurs")
    public Page<Visiteur> getVisiteursAvecPagination(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "dateEntree") String sortBy,
            @RequestParam(defaultValue = "desc") String direction
    ) {
        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();
        return visiteurRepository.findAll(PageRequest.of(page, size, sort));
    }

    // ✅ 2. Export Excel (TOUS les visiteurs)
    @GetMapping("/export/excel")
    public ResponseEntity<byte[]> exportExcel() throws IOException {
        List<Visiteur> visiteurs = visiteurRepository.findAll();
        ExcelExporter exporter = new ExcelExporter(visiteurs);
        byte[] excelData = exporter.export();

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=visiteurs.xlsx");
        headers.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));

        return ResponseEntity.ok()
                .headers(headers)
                .body(excelData);
    }
}
