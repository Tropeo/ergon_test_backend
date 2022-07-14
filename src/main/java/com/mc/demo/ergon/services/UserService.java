package com.mc.demo.ergon.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mc.demo.ergon.dtos.UserDto;
import com.mc.demo.ergon.models.User;
import com.mc.demo.ergon.repositories.UserRepository;

@Service
public class UserService {
    private UserRepository userRepo;
    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public UserDto GetUserById(Long id) {
        Optional<User> optUser = userRepo.findById(id);

        if (optUser.isEmpty())
            return null;

        User user = optUser.get();

        return new UserDto(user.getId(), user.getUsername(), user.getPassword(), user.getFirstName(),
                user.getLastName(), user.getCreationDate());
    }

    public ArrayList<UserDto> GetUsers() {
        ArrayList<UserDto> ret = new ArrayList<UserDto>();

        Iterable<User> users = userRepo.findAll();

        users.forEach(user -> {
            ret.add(
                new UserDto(
                    user.getId(), 
                    user.getUsername(), 
                    user.getPassword(), 
                    user.getFirstName(),
                    user.getLastName(), 
                    user.getCreationDate()
                )
            );
        });

        return ret;
    }

    public boolean AddUser(UserDto newUser) {
        if (userRepo.countByUsername(newUser.getUsername()) > 0)
            return false;

        if (newUser.getUsername() == null || newUser.getUsername().isEmpty() ||
                newUser.getPassword() == null || newUser.getPassword().isEmpty()) {
            return false;
        }

        User createdUser = userRepo.save(
                new User(
                        newUser.getUsername(),
                        passwordEncoder.encode(newUser.getPassword()),
                        newUser.getFirstName(),
                        newUser.getLastName(),
                        new java.sql.Date(new Date().getTime())));

        return createdUser.getId() != null;
    }

    public boolean DeleteUser(Long id) {
        Optional<User> optUser = userRepo.findById(id);

        if (optUser.isEmpty())
            return false;

        User user = optUser.get();

        if (user.getRelatedActivities().size() > 0)
            return false;

        if (user.getComments().size() > 0)
            return false;

        try {
            userRepo.delete(user);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public boolean UpdateUser(Long id, UserDto selectedUser) {
        Optional<User> optUser = userRepo.findById(id);

        if (optUser.isEmpty())
            return false;

        User user = optUser.get();

        Optional.ofNullable(selectedUser.getUsername()).ifPresent(username -> user.setUsername(username));
        Optional.ofNullable(selectedUser.getFirstName()).ifPresent(firstName -> user.setFirstName(firstName));
        Optional.ofNullable(selectedUser.getLastName()).ifPresent(lastName -> user.setLastName(lastName));
        Optional.ofNullable(selectedUser.getPassword()).ifPresent(
                password -> user.setPassword(passwordEncoder.encode(password)));

        User updatedUser = userRepo.save(user);

        return updatedUser.getId() != null;
    }
}
