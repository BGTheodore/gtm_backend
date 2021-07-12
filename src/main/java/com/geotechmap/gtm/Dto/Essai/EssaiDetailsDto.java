package com.geotechmap.gtm.Dto.Essai;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Temporal;

import com.fasterxml.jackson.annotation.JsonFormat;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import static javax.persistence.TemporalType.TIMESTAMP;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class EssaiDetailsDto {
    
    private Long idTypeEssai;
    private String nomTypeEssai;
    private String sigleTypeEssai;
    private String codeCouleurTypeEssai;
    private String descriptionTypeEssai;

    private Long idPosition; 
    private double latitudePosition;
    private double longitudePosition;
    private double altitudePosition;
	private String departementPosition;

    private Long idEssai; 
    private String commentaireEssai;
    private String dateRealisationEssai;
    private String createdByEssai;
    @CreatedDate
    @Temporal(TIMESTAMP)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	protected Date createdDateEssai;
    private String lastModifiedByEssai;
    @LastModifiedDate
    @Temporal(TIMESTAMP)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    protected Date lastModifiedDateEssai;

    private Long idInstitution; 
    private String nomInstitution;
    private String sigleInstitution;
    private String adresseInstitution;
    private String emailInstitution;
    private String telephone1Institution;
    private String telephone2Institution;
    private String site_webInstitution;
    private String descriptionInstitution;

    private Long idFichier;
    private String nomFichier;
}
