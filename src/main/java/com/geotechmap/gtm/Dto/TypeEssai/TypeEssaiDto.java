package com.geotechmap.gtm.Dto.TypeEssai;
import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.geotechmap.gtm.Dto.Essai.EssaiDto;
import com.geotechmap.gtm.Entities.Auditable;

import lombok.Data;
@Data
public class TypeEssaiDto extends Auditable<String> implements Serializable {
    private Long id;
    private static final long serialVersionUID = 1L;
    private String nom;
    private String sigle;
    private String description;
    private String codeCouleur;
    //@JsonManagedReference
    private List<EssaiDto> essais;


}
