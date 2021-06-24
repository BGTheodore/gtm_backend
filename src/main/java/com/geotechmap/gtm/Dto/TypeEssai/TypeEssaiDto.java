package com.geotechmap.gtm.Dto.TypeEssai;
import java.util.List;

import com.geotechmap.gtm.Dto.Essai.EssaiDto;
import com.geotechmap.gtm.Entities.Auditable;

import lombok.Data;

@Data
public class TypeEssaiDto extends Auditable<String>{
    private Long id;
    private String nom;
    private String sigle;
    private String description;
    private List<EssaiDto> essais;
}
