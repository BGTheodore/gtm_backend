package Dto.Fichier;
import com.example.gtm.Entities.Auditable;

import lombok.Data;

@Data
public class FichierDto extends Auditable<String>{
    private Long id;
    private String nom;
    private String lien;
    private String format;
    private String capacite;
    private String hash;
}
