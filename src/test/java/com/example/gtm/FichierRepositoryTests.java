// package com.example.gtm;

// import static org.junit.Assert.assertEquals;
// import static org.mockito.Mockito.when;  

// import com.example.gtm.Entities.Fichier;
// import com.example.gtm.Repositories.FichierRepository;

// import org.junit.jupiter.api.Test;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.boot.test.mock.mockito.MockBean;
// import org.springframework.test.context.junit4.SpringRunner;
// import java.util.stream.Collectors;
// import java.util.stream.Stream;

// import org.junit.runner.RunWith;

// @RunWith(SpringRunner.class)
// @SpringBootTest
// public class FichierRepositoryTests {

// 	@MockBean
//     private FichierRepository repository;

//     @Test
// 	public void getFileTest() {
//         Fichier fichier = new Fichier();
//         fichier.setNom("okokok");
//         fichier.setLien("www.gtm.fichiers/245456545.pdf");
//         fichier.setFormat("PDF");
//         fichier.setCapacite("1024 MB");
        
// 		when(repository.findAll()).thenReturn(Stream
// 		.of(fichier).collect(Collectors.toList()));
// 		assertEquals(1, repository.findAll().size());


// 	}

// }
