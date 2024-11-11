package com.banatech.ru.cairoacademy.response;

import lombok.*;

import java.util.Set;

@Getter @Setter
@Builder
public class AuthResponse{
    private String username;
    private String email;
    private Set<String> roles;
    private String accessToken;

    public AuthResponse(){}

    public AuthResponse(String username, String email, Set<String> roles, String accessToken){
        this.username = username;
        this.email = email;
        this.roles = roles;
        this.accessToken = accessToken;
    }

    public AuthResponse(String username, String email, Set<String> roles){
        this.username = username;
        this.email = email;
        this.roles = roles;
        this.accessToken = accessToken;
    }
}
