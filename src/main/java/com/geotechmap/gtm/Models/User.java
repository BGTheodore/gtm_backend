package com.geotechmap.gtm.Models;
import lombok.Builder;
import lombok.Data;
import java.util.Set;

@Data
@Builder
public class User {
    private String id;

    private String username;

    private String nickName;

    private String firstName;

    private String lastName;

    private Set<Role> roles; 
}
