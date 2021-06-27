package com.geotechmap.gtm.Dto.TypeEssai;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.geotechmap.gtm.Dto.Essai.EssaiDto;
import com.geotechmap.gtm.Entities.Auditable;

import lombok.Data;

// @JsonIdentityInfo(
//   generator = ObjectIdGenerators.PropertyGenerator.class, 
//   property = "id") // to have reference of the type_essai

  
@Data
public class TypeEssaiDto extends Auditable<String>{
    private Long id;
    private String nom;
    private String sigle;
    private String description;
    @JsonManagedReference
    // @JsonBackReference
    private List<EssaiDto> essais;
}
