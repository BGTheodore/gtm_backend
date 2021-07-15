package com.geotechmap.gtm.Repositories;

import java.util.List;

import com.geotechmap.gtm.Dto.Utilisateur.UtilisateurDto;
import com.geotechmap.gtm.Entities.Utilisateur;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {

    @Query(value = "SELECT username AS u FROM keycloak.user_entity WHERE username NOT IN (SELECT username FROM public.utilisateurs) ORDER BY username ASC"
    , nativeQuery = true)
    List<Object> listAllUtilisateursKeycloak();


    @Query(value = "SELECT * FROM utilisateurs u WHERE u.username = :username LIMIT 1"
    , nativeQuery = true)
    Utilisateur rechercheParUsername(@Param("username") String username);

    @Query(value = "SELECT COUNT(*) FROM utilisateurs WHERE is_deleted = false", nativeQuery = true)
    Long countUtilisateurs();
    

}