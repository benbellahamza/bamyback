package org.sid.renaultvisiteursbackend.Controller;

import org.sid.renaultvisiteursbackend.Entity.ActiviteAgent;
import org.sid.renaultvisiteursbackend.Service.ActiviteAgentService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/activites")
@CrossOrigin("*")
public class ActiviteAgentController {
    private final ActiviteAgentService activiteAgentService;
    public ActiviteAgentController(ActiviteAgentService activiteAgentService) {
        this.activiteAgentService = activiteAgentService;
    }
    @GetMapping
    public List<ActiviteAgent> getAllActivities() {
        return activiteAgentService.getAllActivities();
    }
    @GetMapping("/agent/{agentId}")
    public List<ActiviteAgent> getActivitiesByAgent(@PathVariable Long agentId) {
        return activiteAgentService.getActivitiesByAgent(agentId);
    }
}
