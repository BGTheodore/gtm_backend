package com.geotechmap.gtm.Dto.Utilisateur;

import com.geotechmap.gtm.Dto.Institution.InstitutionDto;
import com.geotechmap.gtm.Entities.Auditable;

import lombok.Data;
@Data
public class UtilisateurDto extends Auditable<String>  {
    private Long id;
    private String idKeycloak;
    private String nom;
    private String prenom;
    private String adresse;
    private String telephone;
    private String email;
    private String username;
    private InstitutionDto institution; 
}
