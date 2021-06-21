package com.example.gtm.Entities;

import java.io.Serializable;

// import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
// import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.bedatadriven.jackson.datatype.jts.serialization.GeometryDeserializer;
import com.bedatadriven.jackson.datatype.jts.serialization.GeometrySerializer;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.locationtech.jts.geom.Point;

@Entity
@Table(name = "positions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE positions SET is_deleted = true WHERE id = ?")
@Where(clause = "is_deleted is false")
@EqualsAndHashCode(callSuper=false)//to check
public class Position extends Auditable<String> implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "latitude", nullable = false)
    @Digits(integer = 6, fraction = 6, message = "Problème de type")
    private double latitude;

    @Column(name = "longitude", nullable = false)
    @Digits(integer = 6, fraction = 6, message = "Problème de type")
    private double longitude;

    @Column(name = "altitude", nullable = false)
    @Digits(integer = 6, fraction = 6, message = "Problème de type")
    private double altitude;

    @NotNull(message = "Champs obligatoire")
    @NotEmpty(message = "Champs obligatoire")
    @Size(max = 255, message = "255 caractères au maximum")
    @Column(length = 255)
    private String departement;

    @NotNull(message = "Champs obligatoire")
    @NotEmpty(message = "Champs obligatoire")
    @Size(max = 255, message = "255 caractères au maximum")
    @Column(length = 255)
    private String commune;

    @NotNull(message = "Champs obligatoire")
    @NotEmpty(message = "Champs obligatoire")
    @Size(max = 255, message = "255 caractères au maximum")
    @Column(name="section_communale", length = 255)
    private String sectionCommunale;

    @Size(max = 255, message = "255 caractères au maximum")
    @Column(length = 255)
    private String adresse;


    @Column(columnDefinition = "geometry(Point)", name = "geom")
    // @JsonSerialize(using = GeometrySerializer.class)
    // @JsonDeserialize(contentUsing = GeometryDeserializer.class)
    @JsonIgnore
    private Point geom;

    // @OneToMany(mappedBy = "coordonate")
    // private List<Test> tests;
    
    @Column(nullable = true)
    private Boolean isDeleted = false;
}
