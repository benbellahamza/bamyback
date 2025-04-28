package org.sid.renaultvisiteursbackend.Dto;

import lombok.Data;

@Data
public class CreateUserRequest {
    private String nom;
    private String prenom;
    private String email;
    private String password;
    private String role; // "ADMIN", "AGENT", "RESPONSABLE"
}