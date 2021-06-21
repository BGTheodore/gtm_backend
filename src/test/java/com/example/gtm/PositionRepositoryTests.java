// package com.example.gtm;

// import static org.junit.Assert.assertEquals;
// import static org.mockito.Mockito.when;  

// import com.example.gtm.Entities.Position;
// import com.example.gtm.Repositories.PositionRepository;

// import org.junit.jupiter.api.Test;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.boot.test.mock.mockito.MockBean;
// import org.springframework.test.context.junit4.SpringRunner;
// import java.util.stream.Collectors;
// import java.util.stream.Stream;

// import org.junit.runner.RunWith;

// @RunWith(SpringRunner.class)
// @SpringBootTest
// public class PositionRepositoryTests {

// 	@MockBean
//     private PositionRepository repository;

//     @Test
// 	public void getPositionTest() {
//         Position position = new Position();
//         position.setAltitude(1.2);;
//         position.setLongitude(2.3);
//         position.setAltitude(3.4);
//         position.setDepartement("Ouest");
//         position.setCommune("Pétion-Ville");
//         position.setSectionCommunale("Une section communale");
//         position.setAdresse("#41, Rue Chanvannes prolongée");

// 		when(repository.findAll()).thenReturn(Stream
// 		.of(position).collect(Collectors.toList()));
// 		assertEquals(1, repository.findAll().size());


// 	}

// }
