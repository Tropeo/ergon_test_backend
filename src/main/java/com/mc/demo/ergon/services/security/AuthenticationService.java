package com.mc.demo.ergon.services.security;

import java.util.HashMap;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.mc.demo.ergon.models.User;
import com.mc.demo.ergon.repositories.UserRepository;

@Service
public class AuthenticationService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public AuthenticationService(PasswordEncoder passwordEncoder, UserRepository userRepository){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public String login(String username, String password) {
        User user = this.userRepository.findByUsername(username).orElseThrow();

        if (!this.passwordEncoder.matches(password, user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Bad credentials");
        }

        HashMap<String, Object> claimMap = new HashMap<String, Object>(0);
        claimMap.put("user", user.getUsername());

        return JwtProvider.createJwt(username, claimMap);
    }
}