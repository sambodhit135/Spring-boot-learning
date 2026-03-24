package com.sambo.journalApp.controller;

import com.sambo.journalApp.entity.User;
import com.sambo.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private UserService userService;


    @GetMapping("/health_check")
    public String healthCheck() {
        return "OK";
    }


    @PostMapping("/create_user")
    public ResponseEntity<?> createUser(@RequestBody User user) {

        if (userService.findByUsername(user.getUsername()) != null) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Username already exists");
        }

        userService.saveNewUser(user);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("User created successfully");
    }
}