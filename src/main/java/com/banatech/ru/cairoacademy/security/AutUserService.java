package com.banatech.ru.cairoacademy.security;

import com.banatech.ru.cairoacademy.model.User;
import com.banatech.ru.cairoacademy.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AutUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(AutUserDetailsService.class);

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        logger.debug("Entering in loadUserByUsername Method...");
        User user = userRepository.findByUsername(username).orElse(null);
        if(user == null){
            logger.error("Username not found: {}", username);
            throw new UsernameNotFoundException("Could not found user..!!");
        }
        logger.info("User Authenticated Successfully..!!!");
        return new AuthUserDetails(user);
    }
}
