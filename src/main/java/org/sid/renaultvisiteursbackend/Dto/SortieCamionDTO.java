package org.sid.renaultvisiteursbackend.Dto;

import lombok.Data;

@Data
public class SortieCamionDTO {
    private String destination;
    private String nomChauffeurSortie;
    private String prenomChauffeurSortie;
    private String cinChauffeurSortie;
    private String entreprise;
}
