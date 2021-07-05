package com.geotechmap.gtm.Entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "utilisateurs")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE utilisateurs SET is_deleted = true WHERE id = ?")
@Where(clause = "is_deleted is false")

@EqualsAndHashCode(callSuper=false)//to check
public class Utilisateur extends Auditable<String>  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_keycloak", nullable = false, length = 255)
    private String idKeycloak;

    @NotNull(message = "Nom obligatoire")
    @NotEmpty(message = "Champs obligatoire")
    @Size(min = 2, max = 255, message = "2 caractères au minimum; 255 maximum")
    @Column(nullable = true, length = 45)
    private String nom;

    @NotNull(message = "Préom obligatoire")
    @NotEmpty(message = "Champs obligatoire")
    @Size(min = 2, max = 255, message = "2 caractères au minimum; 255 maximum")
    @Column(nullable = true, length = 45)
    private String prenom;

    @NotNull(message = "Nom obligatoire")
    @NotEmpty(message = "Champs obligatoire")
    @Size(min = 2, max = 45, message = "2 caractères au minimum; 255 maximum")
    @Column(nullable = true, length = 45)
    private String adresse;

    @NotNull(message = "Nom obligatoire")
    @NotEmpty(message = "Champs obligatoire")
    @Size(min = 3, max = 45, message = "3 caractères au minimum; 45 maximum")
    @Column(nullable = true, length = 45)
    private String telephone;

    @NotNull(message = "Nom obligatoire")
    @NotEmpty(message = "Champs obligatoire")
    @Size(min = 6, max = 45, message = "6 caractères au minimum; 45 maximum")
    @Column(nullable = true, length = 45)
    private String email;

    @NotNull(message = "Préom obligatoire")
    @NotEmpty(message = "Champs obligatoire")
    @Size(min = 2, max = 255, message = "2 caractères au minimum; 255 maximum")
    @Column(nullable = true, length = 45)
    private String username;

    @ManyToOne()
    @JoinColumn(name = "id_institution")
    private Institution institution; 
}
