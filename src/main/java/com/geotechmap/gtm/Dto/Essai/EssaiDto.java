package com.geotechmap.gtm.Dto.Essai;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.geotechmap.gtm.Dto.Fichier.FichierDto;
import com.geotechmap.gtm.Dto.Institution.InstitutionDto;
import com.geotechmap.gtm.Dto.Position.PositionDto;
import com.geotechmap.gtm.Dto.TypeEssai.TypeEssaiDto;
import com.geotechmap.gtm.Entities.Auditable;

import lombok.Data;
// @JsonIdentityInfo(
//   generator = ObjectIdGenerators.PropertyGenerator.class, 
//   property = "id") // to have reference of the type_essai

@Data
public class EssaiDto extends Auditable<String>{
    private Long id;
    @JsonBackReference
    // @JsonManagedReference
    private TypeEssaiDto typeEssai; 
    private InstitutionDto institution; 
    private PositionDto position; 
    private FichierDto fichier;
    private String motsCles;
    private String commentaire;
    private String pdf;

}
