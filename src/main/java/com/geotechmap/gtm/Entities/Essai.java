package com.geotechmap.gtm.Entities;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Table(name = "essais", indexes = @Index(name = "essais_mots_cles_index", columnList = "mots_cles"))
@SQLDelete(sql = "UPDATE essais SET is_deleted = TRUE WHERE id = ?")
@Where(clause = "is_deleted is false")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Essai extends Auditable<String>  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // @NaturalId(mutable = false)
    // @Column(name="natural_id", length = 255, unique = true)
    // private String naturalId;

    //@JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="id_type_essai", nullable = false)
    private TypeEssai typeEssai;


    @ManyToOne()
    @JoinColumn(name = "id_institution")
    private Institution institution; 

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_position")
    private Position position; 

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_fichier")
    private Fichier fichier;

    @Size(max = 255, message = "255 caractères au maximum")
    @Column(name="mots_cles", length = 255)
    private String motsCles;

    @Size(max = 255, message = "255 caractères au maximum")
    @Column(name="commentaire", length = 255)
    private String commentaire;

    @Transient //to not persist in DB
    @Lob // large object
    private String pdf;

    // @Override
    // public boolean equals(Object obj) {
    //     if (this == obj) {
    //         return true;
    //     }

    //     if (obj == null) {
    //         return false;
    //     }

    //     if (obj.getClass() != getClass()) {
    //         return false;
    //     }

    //     Essai essai = (Essai) obj;

    //     return java.util.Objects.equals(this.naturalId, essai.getNaturalId());
    // }

    // @Override
    // public int hashCode() {
    //     return java.util.Objects.hashCode(naturalId);
    // }


    // public void setNaturalId(String naturalId){
    //     java.util.Date date = new java.util.Date();
    //     this.naturalId = new Timestamp(date.getTime()).toString();
    // }
}
