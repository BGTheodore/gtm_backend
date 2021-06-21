package com.example.gtm.Entities;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;

public class SpringSecurityAuditorAware implements AuditorAware<String> {
    @Autowired
    private HttpServletRequest request;
    
	@Override
	public Optional<String> getCurrentAuditor() {
		
		// Just return a string representing the username
		 return Optional.ofNullable("kevin").filter(s -> !s.isEmpty());
        
        // KeycloakPrincipal<KeycloakSecurityContext> kp = (KeycloakPrincipal<KeycloakSecurityContext>) request.getUserPrincipal();
        // String userName = kp.getKeycloakSecurityContext().getToken().getPreferredUsername();
        // return Optional.ofNullable(userName);


        

	}
	
}
