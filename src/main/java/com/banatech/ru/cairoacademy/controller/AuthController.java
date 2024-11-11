package com.banatech.ru.cairoacademy.controller;

import com.banatech.ru.cairoacademy.dto.UserDTO;
import com.banatech.ru.cairoacademy.exception.AlreadyExistsException;
import com.banatech.ru.cairoacademy.request.AuthRequest;
import com.banatech.ru.cairoacademy.request.UserRequest;
import com.banatech.ru.cairoacademy.response.ApiResponse;
import com.banatech.ru.cairoacademy.response.AuthResponse;
import com.banatech.ru.cairoacademy.service.user.UserService;
import com.banatech.ru.cairoacademy.security.utils.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("${server.prefix}/users")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @PostMapping("/auth/sign-up")
    public ResponseEntity<?> createUser(@RequestBody UserRequest request){
        try {
            UserDTO userDTO = userService.createUser(request);
            return ResponseEntity.status(200).body(new ApiResponse("", userDTO));
        } catch (AlreadyExistsException e){
            return ResponseEntity.status(404).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PostMapping("/auth/sign-in")
    public ResponseEntity<?> authenticate(@RequestBody AuthRequest request){
        AuthResponse authResponse = userService.authenticate(request);
        return ResponseEntity.status(200).body(new ApiResponse("", authResponse));
    }

}