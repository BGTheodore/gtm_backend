// package com.example.gtm;

// import static org.junit.Assert.assertEquals;
// import static org.mockito.Mockito.when;  

// import com.example.gtm.Entities.TypeEssai;
// import com.example.gtm.Repositories.TypeEssaiRepository;
// import com.example.gtm.Services.TypeEssaiService;

// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.boot.test.mock.mockito.MockBean;
// import org.springframework.test.context.junit4.SpringRunner;
// import java.util.stream.Collectors;
// import java.util.stream.Stream;

// import org.junit.runner.RunWith;

// @RunWith(SpringRunner.class)
// @SpringBootTest
// public class TypeEssaiRepositoryTests {
//     @Autowired
// 	private TypeEssaiService service;

// 	@MockBean
//     private TypeEssaiRepository repository;

//     @Test
// 	public void getTypeEssaiTest() {
//         TypeEssai typeEssai = new TypeEssai();
//         typeEssai.setNom("Teneur en eau");
// 		typeEssai.setSigle("TEE");
// 		typeEssai.setDescription("Test servant faire calculer la teneur en eau d'un terrain");
        
// 		when(repository.findAll()).thenReturn(Stream
// 				.of(typeEssai).collect(Collectors.toList()));
// 		assertEquals(1, service.listAllTypeEssais().size());
// 	}

// }
