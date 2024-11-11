package com.banatech.ru.cairoacademy.dto;

import com.banatech.ru.cairoacademy.model.Role;
import lombok.Data;

import java.util.Set;

@Data
public class UserDTO {
    private String username;
    private String email;
    private String password;
    private boolean enabled;
    private Set<Role> roles;
}
