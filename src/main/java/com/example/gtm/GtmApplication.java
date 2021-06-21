package com.example.gtm;

import com.bedatadriven.jackson.datatype.jts.JtsModule;

// import java.util.List;

import com.example.gtm.Entities.SpringSecurityAuditorAware;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication
//@RestController

@EnableJpaAuditing(auditorAwareRef="auditorAware")
public class GtmApplication {

	//__Permettre à Jackson de serialize geoJSON
	// @Configuration
	// public class JacksonConfig {
	
	//  @Bean
	//  public JtsModule jtsModule() {
	//   return new JtsModule();
	//  }
	// }
    // @Bean
    // public ObjectMapper objectMapper() {
    //     ObjectMapper mapper = new ObjectMapper();
    //     //mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    //     //mapper.configure(MapperFeature.DEFAULT_VIEW_INCLUSION, true);
    //     mapper.registerModule(new JtsModule());
    //     System.out.println(">>>>>>>>>>>>>>>>>>>>>>JST MoDULE CONFIGURED>>>>>>>>>>>>>>>>> ");
    //     return mapper;
    // }
	//__ End permettre à Jackson de serialize geoJSO

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Bean
	public AuditorAware<String> auditorAware() {
		return new SpringSecurityAuditorAware();
	}

	public static void main(String[] args) {
		SpringApplication.run(GtmApplication.class, args);
	}

	// @GetMapping
	// public List<String> hello(){
	// 	return List.of("Hello", "World");
	// }

}
