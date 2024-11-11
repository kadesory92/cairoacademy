package com.banatech.ru.cairoacademy.service.user;

import com.banatech.ru.cairoacademy.dto.UserDTO;
import com.banatech.ru.cairoacademy.model.User;
import com.banatech.ru.cairoacademy.request.AuthRequest;
import com.banatech.ru.cairoacademy.request.UserRequest;
import com.banatech.ru.cairoacademy.response.AuthResponse;

import java.util.UUID;

public interface UserService {
    UserDTO getUserById(UUID userId);
    UserDTO getUserByUsername(String username);
    UserDTO getUserByEmail(String email);
    boolean existUserByUsername(String username);
    boolean existUserByEmail(String email);
    UserDTO createUser(UserRequest request);
    AuthResponse authenticate(AuthRequest request);
//    AuthResponse getUserLogged();
    UserDTO updateUser(UserRequest request, UUID userId);
    void deleteUser(UUID userId);
    void activateUser(String token);
    UserDTO getUserEnabled(String username);
    UserDTO userToUserDTO(User user);
    User userDtoToUser(UserDTO userDTO);
}
