package com.geotechmap.gtm.Util;


import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.adapters.spi.KeycloakAccount;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.AccessToken;
import org.keycloak.representations.IDToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.security.Principal;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.geotechmap.gtm.Models.Role;
import com.geotechmap.gtm.Models.User;
public class CurrentUserUtil {
    
    public static User getModel() {
        return User.builder()
                .id(getId())
                .nickName(getNickName())
                .firstName(getFirstName())
                .lastName(getLastName())
                .roles(getRoles())
                .build();
    }

    public static String getId() {
        return getUserInfo().map(IDToken::getId).orElse(null);
     
    }

    public static String getNickName() {
        return getKeycloakAccount()
                .map(account -> account.getPrincipal().getName())
                .orElse("anonymous");
    }

    public static Set<Role> getRoles() {
        return getKeycloakAccount()
                .map(keycloakAccount -> keycloakAccount.getRoles()
                        .stream().map(Role::valueOf)
                        .collect(Collectors.toSet()))
                .orElse(null);
    }

    public static String getEmail() {
        return getUserInfo().map(IDToken::getEmail).orElse(null);
    }

    public static String getFirstName() {
        return getUserInfo().map(IDToken::getGivenName).orElse(null);
    }

    public static String getLastName() {
        return getUserInfo().map(IDToken::getFamilyName).orElse(null);
    }

    public static String getMiddleName() {
        return getUserInfo().map(IDToken::getMiddleName).orElse(null);
    }

    public static String getBirthDay() {
        return getUserInfo().map(IDToken::getBirthdate).orElse(null);
    }

    public static Optional<AccessToken> getUserInfo() {
        KeycloakPrincipal<?> principal = (KeycloakPrincipal<?>) getKeycloakAccount()
                .map(KeycloakAccount::getPrincipal)
                .orElse(null);
       return Optional.ofNullable(principal)
                .map(keycloakPrincipal -> keycloakPrincipal.getKeycloakSecurityContext().getToken());
    }

    public static String getUsername(){

        return getUserInfo().map(IDToken::getPreferredUsername).orElse(null);
    }

    public static Optional<KeycloakAccount> getKeycloakAccount() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getName().equals("anonymousUser")) {
            return Optional.empty();
        }
        return Optional.ofNullable(((KeycloakAuthenticationToken) authentication).getAccount());
    }

}
