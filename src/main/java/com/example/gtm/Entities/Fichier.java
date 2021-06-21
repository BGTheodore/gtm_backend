package com.example.gtm.Entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "fichiers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE fichiers SET is_deleted = true WHERE id = ?")
@Where(clause = "is_deleted is false")

@EqualsAndHashCode(callSuper=false)//to check
public class Fichier extends Auditable<String>{
   
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Champs obligatoire")
    @NotEmpty(message = "Champs obligatoire")
    @Size(max = 255, message = "255 caractères au maximum")
    @Column(length = 255)
    private String nom;

    @NotNull(message = "Champs obligatoire")
    @NotEmpty(message = "Champs obligatoire")
    @Size(max = 255, message = "255 caractères au maximum")
    @Column(length = 255)
    private String lien;

    @Size(max = 255, message = "255 caractères au maximum")
    @Column(length = 255)
    private String format;

    @Size(max = 255, message = "255 caractères au maximum")
    @Column(length = 255)
    private String capacite;

    @Size(max = 400, message = "400 caractères au maximum")
    @NotEmpty(message = "Champs obligatoire")
    @Column(length = 400)
    private String hash;

    // @OneToOne(mappedBy = "file")
    // private Test test;
}

