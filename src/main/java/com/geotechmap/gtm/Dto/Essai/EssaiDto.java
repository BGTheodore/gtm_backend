package com.geotechmap.gtm.Dto.Essai;
import java.io.Serializable;

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


@Data
public class EssaiDto extends Auditable<String>  implements Serializable{
    private Long id;
    private static final long serialVersionUID = 1L;
    //@JsonBackReference
    private TypeEssaiDto typeEssai; 
    private InstitutionDto institution; 
    private PositionDto position; 
    private FichierDto fichier;
    private String motsCles;
    private String commentaire;
    private String dateRealisation;
    private String pdf;
    private String nomFichierASuprimmer;


    public TypeEssaiDto getTypeEssai(){
        typeEssai.setEssais(null);
        return typeEssai;
    }
}
