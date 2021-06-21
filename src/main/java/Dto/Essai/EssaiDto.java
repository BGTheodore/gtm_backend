package Dto.Essai;
import com.example.gtm.Entities.Auditable;
import com.example.gtm.Entities.Fichier;
import com.example.gtm.Entities.Institution;
import com.example.gtm.Entities.Position;
import com.example.gtm.Entities.TypeEssai;
import lombok.Data;

@Data
public class EssaiDto extends Auditable<String>{
    private Long id;
    private TypeEssai typeEssai; 
    private Institution institution; 
    private Position position; 
    private Fichier fichier;
    private String motsCles;
    private String commentaire;
    private String pdf;

}
