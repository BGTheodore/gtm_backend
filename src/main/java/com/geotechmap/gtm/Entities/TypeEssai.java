package com.geotechmap.gtm.Entities;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.bouncycastle.util.Objects;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "type_essais")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE type_essais SET is_deleted = true WHERE id = ?")
@Where(clause = "is_deleted is false")

@EqualsAndHashCode(callSuper = false) // to check
public class TypeEssai extends Auditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // @NaturalId
    @NotNull(message = "Nom obligatoire")
    @NotEmpty(message = "Champs obligatoire")
    @Size(min = 2, max = 45, message = "2 caractères au minimum; 45 maximum")
    @Column(nullable = false, unique = true, length = 45)
    private String nom;

    @Size(max = 15, message = "15 caractères au maximum")
    @Column(length = 15)
    private String sigle;

    @Size(max = 255, message = "255 caractères au maximum")
    @Column(length = 255)
    private String description;

    @Size(max = 10, message = "10 caractères au maximum")
    @Column(name="code_couleur", length = 10)
    private String codeCouleur="0077FF" ;
    // varchar(255) default '15-JUL-1980'"


    // implement hashCode() and equals()
    //@JsonManagedReference
    @OneToMany(mappedBy = "typeEssai")
    private List<Essai> essais;

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

    //     TypeEssai typeEssai = (TypeEssai) obj;

    //     return java.util.Objects.equals(this.nom, typeEssai.getNom());
    // }

    // @Override
    // public int hashCode() {
    //     return java.util.Objects.hashCode(nom);
    // }



}
