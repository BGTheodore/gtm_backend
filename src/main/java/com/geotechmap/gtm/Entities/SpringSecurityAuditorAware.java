 package com.geotechmap.gtm.Entities;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;


public class SpringSecurityAuditorAware implements AuditorAware<String> {
    @Autowired
    private HttpServletRequest request;

    @Override
    public Optional<String> getCurrentAuditor() {
        //  System.out.println("@@@@@@@@@@@@@@@@@@@@2: "+CurrentUserUtil.getUsername() );
	//  return Optional.ofNullable(CurrentUserUtil.getUsername()).filter(s -> !s.isEmpty());
        return null;
    }

	}


