package com.geotechmap.gtm;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.geotechmap.gtm.Entities.Essai;
import com.geotechmap.gtm.Entities.Fichier;
import com.geotechmap.gtm.Entities.Institution;
import com.geotechmap.gtm.Entities.Position;
import com.geotechmap.gtm.Entities.TypeEssai;
import com.geotechmap.gtm.Entities.Utilisateur;
import com.geotechmap.gtm.Repositories.EssaiRepository;
import com.geotechmap.gtm.Services.EssaiService;

import org.junit.Test ;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;



@RunWith(SpringRunner.class)
@SpringBootTest
public class EssaiRepositoryTests {

    @Autowired
    private EssaiService service;

	@MockBean
    private EssaiRepository essaiRepository;

    @Test
    public void whenSaveOneToManyRelationship_thenCorrect() {
        TypeEssai typeEssai = new TypeEssai();
        typeEssai.setNom("Teneur en eau");
		typeEssai.setSigle("TEE");
        typeEssai.setCodeCouleur("0077FF");;
		typeEssai.setDescription("Test servant faire calculer la teneur en eau d'un terrain");

        Institution institution = new Institution();
        institution.setNom("Unité de Recherche en Géosciences");
        institution.setSigle("LNBTP");
        institution.setAdresse("Delmas 33");
        institution.setTelephone1("56782332");
        institution.setTelephone2("45678922");
        institution.setEmail("www.lnbtp.com");
        institution.setNumeroFiscal("RR-32323");
        institution.setDescription("Lorem ipsum dolor.");

        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setIdKeycloak("nweibi");

        Position position = new Position();
        position.setAltitude(1.2);;
        position.setLongitude(2.3);
        position.setAltitude(3.4);
        position.setDepartement("Ouest");
        // position.setCommune("Pétion-Ville");
        // position.setSectionCommunale("Une section communale");
        // position.setAdresse("#41, Rue Chanvannes prolongée");

        Fichier fichier = new Fichier();
        // fichier.setLien("www.gtm.fichiers/245456545.pdf");
        fichier.setFormat("PDF");
        fichier.setCapacite("1024 MB");
        fichier.setHashNomFichier("uigeflgb");
        fichier.setHashPdf("jbfdglluduil");

        Essai essai1 = new Essai();
        // Test test2 = new Test();
        // test2.setTestType(testType);
        essai1.setTypeEssai(typeEssai);
        essai1.setInstitution(institution);
        essai1.setPosition(position);
        essai1.setFichier(fichier);
        essai1.setMotsCles("bonjour bonsoir test");

        when(essaiRepository.findAll()).thenReturn(Stream
        .of(essai1).collect(Collectors.toList()));
        assertEquals(1, service.listAllEssais().size());
    }
}
