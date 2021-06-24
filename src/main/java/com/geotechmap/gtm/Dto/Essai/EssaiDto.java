package com.geotechmap.gtm.Dto.Essai;
import com.geotechmap.gtm.Dto.Fichier.FichierDto;
import com.geotechmap.gtm.Dto.Institution.InstitutionDto;
import com.geotechmap.gtm.Dto.Position.PositionDto;
import com.geotechmap.gtm.Dto.TypeEssai.TypeEssaiDto;
import com.geotechmap.gtm.Entities.Auditable;

import lombok.Data;

@Data
public class EssaiDto extends Auditable<String>{
    private Long id;
    private TypeEssaiDto typeEssai; 
    private InstitutionDto institution; 
    private PositionDto position; 
    private FichierDto fichier;
    private String motsCles;
    private String commentaire;
    private String pdf;

}
