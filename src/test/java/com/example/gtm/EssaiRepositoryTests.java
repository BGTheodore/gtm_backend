package com.example.gtm;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.example.gtm.Entities.TypeEssai;
import com.example.gtm.Entities.Position;
import com.example.gtm.Entities.Fichier;
import com.example.gtm.Entities.Institution;
import com.example.gtm.Entities.Essai;
import com.example.gtm.Repositories.EssaiRepository;
import com.example.gtm.Services.EssaiService;

// import org.junit.Test ;
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

    @org.junit.Test
    public void whenSaveOneToManyRelationship_thenCorrect() {
        TypeEssai typeEssai = new TypeEssai();
        typeEssai.setNom("Teneur en eau");
		typeEssai.setSigle("TEE");
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

        Position position = new Position();
        position.setAltitude(1.2);;
        position.setLongitude(2.3);
        position.setAltitude(3.4);
        position.setDepartement("Ouest");
        position.setCommune("Pétion-Ville");
        position.setSectionCommunale("Une section communale");
        position.setAdresse("#41, Rue Chanvannes prolongée");

        Fichier fichier = new Fichier();
        fichier.setLien("www.gtm.fichiers/245456545.pdf");
        fichier.setFormat("PDF");
        fichier.setCapacite("1024 MB");

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
