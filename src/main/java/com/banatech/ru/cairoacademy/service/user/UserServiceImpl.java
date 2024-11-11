package com.banatech.ru.cairoacademy.service.user;

import com.banatech.ru.cairoacademy.dto.UserDTO;
import com.banatech.ru.cairoacademy.exception.AlreadyExistsException;
import com.banatech.ru.cairoacademy.exception.InvalidCredentialsException;
import com.banatech.ru.cairoacademy.exception.NotFoundException;
import com.banatech.ru.cairoacademy.model.Role;
import com.banatech.ru.cairoacademy.model.User;
import com.banatech.ru.cairoacademy.model.enums.RoleName;
import com.banatech.ru.cairoacademy.repository.RoleRepository;
import com.banatech.ru.cairoacademy.repository.UserRepository;
import com.banatech.ru.cairoacademy.request.AuthRequest;
import com.banatech.ru.cairoacademy.request.UserRequest;
import com.banatech.ru.cairoacademy.response.AuthResponse;
import com.banatech.ru.cairoacademy.security.AutUserService;
import com.banatech.ru.cairoacademy.security.AuthUser;
import com.banatech.ru.cairoacademy.security.utils.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final AutUserService autUserService;

    @Override
    public UserDTO getUserById(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(()->new NotFoundException("Pas d'utilisateur" +userId));
        return userMapper.convertToDto(user);
    }

    @Override
    public UserDTO getUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(()->new NotFoundException("Pas d'Utilisateur avec cet username " +username));
        return userMapper.convertToDto(user);
    }

    @Override
    public UserDTO getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(()->new NotFoundException("Pas d'Utilisateur avec cet email " +email));
        return userMapper.convertToDto(user);
    }

    @Override
    public boolean existUserByUsername(String email) {
        return userRepository.existsByUsernameIgnoreCase(email);
    }

    @Override
    public boolean existUserByEmail(String email) {
        return userRepository.existsByEmailIgnoreCase(email);
    }


    @Override
    public UserDTO createUser(UserRequest request) {
        if (existUserByUsername(request.getUsername())){
            throw  new AlreadyExistsException("User avec ce username " +request.getUsername()+ " existe déjà");
        }

        if (existUserByEmail(request.getEmail())){
            throw  new AlreadyExistsException("User avec ce email " +request.getEmail()+ " existe déjà");
        }
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setEnabled(true);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        Role defaultRole = roleRepository.findByName(RoleName.USER).orElse(null);
        if (defaultRole == null){
            defaultRole = new Role(RoleName.USER);
            roleRepository.save(defaultRole);
        }
        user.getRoles().add(defaultRole);

        User userSaved = userRepository.save(user);
        return userMapper.convertToDto(userSaved);
    }

    @Override
    public AuthResponse authenticate(AuthRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
            AuthUser authUser = (AuthUser) authentication.getPrincipal();
            String accessToken = jwtService.generateToken(authUser);

            AuthResponse authResponse = new AuthResponse();
            authResponse.setUsername(authUser.getUsername());
            authResponse.setEmail(authUser.getEmail());
            authResponse.setRoles(authUser.getAuthorities().stream().map(Object::toString).collect(Collectors.toSet()));
            authResponse.setAccessToken(accessToken);

            return authResponse;
        } catch (AuthenticationException e) {
            log.info(e.getMessage());
        }
        return null;
    }

//    @Override
//    public AuthResponse getUserLogged() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        AuthUser authUser = (AuthUser) authentication;
//        String username = authUser.getUsername();
//        String email = authUser.getEmail();
//        List<String> roles = authUser.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
//        return new AuthResponse(username, email, roles);
//    }

    @Override
    public UserDTO updateUser(UserRequest request, UUID userId) {
        return userRepository.findById(userId).map(existUser->{
            existUser.setUsername(request.getUsername());
            existUser.setEmail(request.getEmail());
            existUser.setPassword(request.getPassword());
            existUser.setEnabled(request.isEnabled());
            return userMapper.convertToDto(userRepository.save(existUser));
        }).orElseThrow(() -> new NotFoundException("User not found!"));
    }

    @Override
    public void deleteUser(UUID userId) {
        userRepository.findById(userId).ifPresentOrElse(userRepository::delete, ()->{
            throw new NotFoundException("User Not Found");
        });
    }

    @Override
    public void activateUser(String token) {

    }

    @Override
    public UserDTO getUserEnabled(String username) {
        return null;
    }

    @Override
    public UserDTO userToUserDTO(User user) {
        return userMapper.convertToDto(user);
    }

    @Override
    public User userDtoToUser(UserDTO userDTO) {
        return userMapper.convertToEntity(userDTO);
    }
}
