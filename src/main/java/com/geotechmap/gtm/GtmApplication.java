package com.geotechmap.gtm;

import java.util.Arrays;
import java.util.Collections;

import com.bedatadriven.jackson.datatype.jts.JtsModule;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.geotechmap.gtm.Entities.SpringSecurityAuditorAware;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.core.Ordered;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication
//@RestController
@Configuration
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

	//  Spring’s @CrossOrigin doesn’t play well with Spring Security. 
	@Bean
    public FilterRegistrationBean<CorsFilter> simpleCorsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.setAllowedOrigins(Arrays.asList("http://localhost:3000","http://localhost:3001"));
        config.setAllowedMethods(Collections.singletonList("*"));
        config.setAllowedHeaders(Collections.singletonList("*"));
        source.registerCorsConfiguration("/**", config);
        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(new CorsFilter(source));
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return bean;
    }

}
