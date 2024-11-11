package com.banatech.ru.cairoacademy.request;

import lombok.Data;

@Data
public class AuthRequest {
    private String username;
    private String password;
}
