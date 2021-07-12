package com.geotechmap.gtm.Entities;
import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;

import com.fasterxml.jackson.annotation.JsonFormat;

import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static javax.persistence.TemporalType.TIMESTAMP;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Entity
@Immutable
@Table(name = "v_essais_details")
@Where(clause = "is_deleted_essai is false")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class EssaiDetails implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEssai;
    
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

    private String commentaireEssai;
    private String dateRealisationEssai;
    private String createdByEssai;
    @CreatedDate
    @DateTimeFormat(pattern="dd.MM.yyyy hh:mm")
	protected Date createdDateEssai;
    private String lastModifiedByEssai;
    @LastModifiedDate
    @DateTimeFormat(pattern="dd.MM.yyyy hh:mm")
    protected Date lastModifiedDateEssai;
    private Boolean isDeletedEssai;

    private Long idInstitution; 
    private String nomInstitution;
    private String sigleInstitution;
    private String adresseInstitution;
    private String emailInstitution;
    @Column(name="telephone1_institution")
    private String telephone1Institution;
    @Column(name="telephone2_institution")
    private String telephone2Institution;
    private String site_webInstitution;
    private String descriptionInstitution;

    private Long idFichier;
    private String nomFichier;
}
