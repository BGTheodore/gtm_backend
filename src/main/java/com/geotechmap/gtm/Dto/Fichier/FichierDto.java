package com.geotechmap.gtm.Dto.Fichier;
import com.geotechmap.gtm.Entities.Auditable;

import lombok.Data;

@Data
public class FichierDto extends Auditable<String>{
    private Long id;
    private String nom;
    private String lien;
    private String format;
    private String capacite;
    private String hashPdf;
    private String hashNomFichier;
}
