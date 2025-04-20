package org.sid.renaultvisiteursbackend.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PersonRequestRegister {
    private String nom;
    private String prenom;
    private String email;
    private String role;
    private String password;
}
