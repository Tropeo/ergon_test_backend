package com.mc.demo.ergon.controllers;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mc.demo.ergon.dtos.UserDto;
import com.mc.demo.ergon.services.UserService;

@RestController
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

	@GetMapping("/user/{id}")
	public UserDto getSingle(@PathVariable("id") Long id) {
        return userService.GetUserById(id);
	}

    @GetMapping("/user")
	public ArrayList<UserDto> getAll() {
        return userService.GetUsers();
	}

    @PostMapping("/user")
	public boolean post(@RequestBody UserDto user) {
        return userService.AddUser(user);
	}

    @PatchMapping("/user/{id}")
	public boolean patch(@PathVariable("id") Long id, @RequestBody UserDto user) {
        return userService.UpdateUser(id, user);
	}

    @DeleteMapping("/user/{id}")
	public boolean delete(@PathVariable("id") Long id) {
        return userService.DeleteUser(id);
	}
}