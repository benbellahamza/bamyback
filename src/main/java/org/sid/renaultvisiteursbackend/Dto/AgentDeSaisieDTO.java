package org.sid.renaultvisiteursbackend.Dto;

import lombok.Data;

@Data
public class AgentDeSaisieDTO {
    private Long id;
    private String login;
    private String password;
}