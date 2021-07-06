package com.geotechmap.gtm.Dto.Position;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.geotechmap.gtm.Entities.Auditable;

import org.locationtech.jts.geom.Point;
import lombok.Data;

@Data
public class PositionDto{
    private Long id;
    private double latitude;
    private double longitude;
    private double altitude;
    private String departement;
    // private String commune;
    // private String sectionCommunale;
    // private String adresse;
    @JsonIgnore
    private Point geom; 
}
