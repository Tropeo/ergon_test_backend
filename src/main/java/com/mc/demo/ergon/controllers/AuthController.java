
package com.mc.demo.ergon.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mc.demo.ergon.dtos.AuthDto;
import com.mc.demo.ergon.dtos.UserDto;
import com.mc.demo.ergon.services.UserService;
import com.mc.demo.ergon.services.security.AuthenticationService;

@RestController
public class AuthController {

    private UserService userService;
    private AuthenticationService authenticationService;

    public AuthController(UserService userService, AuthenticationService authenticationService) {
        this.userService = userService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/token")
    public String token(@RequestBody() AuthDto data) {
        return this.authenticationService.login(data.getUsername(), data.getPassword());
    }

    @PostMapping("/register")
    public boolean register(@RequestBody UserDto user) {
        return userService.AddUser(user);
    }
}