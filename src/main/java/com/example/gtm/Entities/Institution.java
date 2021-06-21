package com.example.gtm.Entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "institutions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE institutions SET is_deleted = true WHERE id = ?")
@Where(clause = "is_deleted is false")

@EqualsAndHashCode(callSuper=false)//to check
public class Institution extends Auditable<String>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Nom obligatoire")
    @NotEmpty(message = "Champs obligatoire")
    @Size(min = 2, max = 255, message = "2 caractères au minimum; 255 maximum")
    @Column(nullable = false, length = 45)
    private String nom;

    @Size( max = 10, message = "10 caractères au maximum")
    @Column(length = 45)
    private String sigle;

    @NotNull(message = "Nom obligatoire")
    @NotEmpty(message = "Champs obligatoire")
    @Size(min = 2, max = 45, message = "2 caractères au minimum; 255 maximum")
    @Column(nullable = false, length = 45)
    private String adresse;

    @NotNull(message = "Nom obligatoire")
    @NotEmpty(message = "Champs obligatoire")
    @Size(min = 3, max = 45, message = "3 caractères au minimum; 45 maximum")
    @Column(nullable = false, length = 45)
    private String telephone1;

    @Size(max = 45, message = "45 caractères maximum")
    @Column(nullable = true, length = 45)
    private String telephone2;

    @NotNull(message = "Nom obligatoire")
    @NotEmpty(message = "Champs obligatoire")
    @Size(min = 6, max = 45, message = "6 caractères au minimum; 45 maximum")
    @Column(nullable = false, length = 45)
    private String email;

    @Size(max = 45, message = "2 caractères au minimum; 45 maximum")
    @Column(name = "site_web", nullable = true, length = 45)
    private String siteWeb;

    @NotNull(message = "Nom obligatoire")
    @NotEmpty(message = "Champs obligatoire")
    @Size(min = 2, max = 45, message = "2 caractères au minimum; 45 maximum")
    @Column(name = "numero_fiscal", nullable = false, length = 45)
    private String numeroFiscal;

    @Size(max = 255, message = "255 caractères au maximum")
    @Column(name = "description", length = 255)
    private String description;

    // @OneToMany(mappedBy = "institution")
    // private List<Essai> essais;
}
