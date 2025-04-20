package org.sid.renaultvisiteursbackend.Dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PersonRequestLogin {
    private String email;
    private String Role;
    private String password;

}
