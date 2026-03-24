package com.sambo.journalApp.controller;

import com.sambo.journalApp.entity.User;
import com.sambo.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;



    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAll();
    }



    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody User user) {

        userService.saveNewUser(user);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }



    @DeleteMapping
    public ResponseEntity<?> deleteUser() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User user = userService.findByUsername(username);

        if (user != null) {
            userService.deleteById(user.getId());
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}