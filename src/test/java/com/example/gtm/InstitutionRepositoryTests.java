package com.example.gtm;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.example.gtm.Entities.Institution;
import com.example.gtm.Repositories.InstitutionRepository;
import com.example.gtm.Services.InstitutionService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class) 
@SpringBootTest
public class InstitutionRepositoryTests {
    @Autowired
    private InstitutionService service;

    @MockBean
    private InstitutionRepository repository;

    @Test
    public void getInstitutionTest(){
        Institution institution = new Institution();
        institution.setNom("Unité de Recherche en Géosciences");
        institution.setSigle("LNBTP");
        institution.setAdresse("Delmas 33");
        institution.setTelephone1("56782332");
        institution.setTelephone2("45678922");
        institution.setEmail("www.lnbtp.com");
        institution.setNumeroFiscal("RR-32323");
        institution.setDescription("Lorem ipsum dolor.");

        when(repository.findAll()).thenReturn(Stream
        .of(institution).collect(Collectors.toList()));
        assertEquals(1, service.listAllInstitutions().size());
    }

}
