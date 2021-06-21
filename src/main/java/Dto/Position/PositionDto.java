package Dto.Position;
import com.example.gtm.Entities.Auditable;

import org.locationtech.jts.geom.Point;
import lombok.Data;

@Data
public class PositionDto extends Auditable<String>{
    private Long id;
    private double latitude;
    private double longitude;
    private double altitude;
    private String departement;
    private String commune;
    private String sectionCommunale;
    private String adresse;
    private Point geom; 
}
