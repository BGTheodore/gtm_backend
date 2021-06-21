package com.example.gtm;
import com.bedatadriven.jackson.datatype.jts.JtsModule;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

@Configuration
public class JacksonConfig {
    @Bean
    public JtsModule jtsModule() {
        return new JtsModule();
    }
    //__Permettre à Jackson de serialize geoJSON
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        //mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        //mapper.configure(MapperFeature.DEFAULT_VIEW_INCLUSION, true);
        mapper.registerModule(new JtsModule());
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>JST MoDULE CONFIGURED>>>>>>>>>>>>>>>>> ");
        return mapper;
    }
	//__ End permettre à Jackson de serialize geoJSO

@Bean
@ConditionalOnMissingBean(value = MappingJackson2HttpMessageConverter.class, ignoredType = {
        "org.springframework.hateoas.mvc.TypeConstrainedMappingJackson2HttpMessageConverter",
        "org.springframework.data.rest.webmvc.alps.AlpsJsonHttpMessageConverter" })
public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter(
        ObjectMapper objectMapper) {
    return new MappingJackson2HttpMessageConverter(objectMapper);
}


}