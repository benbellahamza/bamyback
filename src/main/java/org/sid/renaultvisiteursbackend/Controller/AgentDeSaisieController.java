package org.sid.renaultvisiteursbackend.Controller;

import lombok.RequiredArgsConstructor;
import org.sid.renaultvisiteursbackend.Dto.AgentDeSaisieDTO;
import org.sid.renaultvisiteursbackend.Service.AgentDeSaisieService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/agents")
@CrossOrigin("*")
@RequiredArgsConstructor
public class AgentDeSaisieController {

    private final AgentDeSaisieService service;

    @PostMapping
    public AgentDeSaisieDTO createAgent(@RequestBody AgentDeSaisieDTO dto) {
        return service.createAgent(dto);
    }

    @GetMapping
    public List<AgentDeSaisieDTO> getAllAgents() {
        return service.getAllAgents();
    }
}