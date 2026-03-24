package com.sambo.journalApp.service;

import com.sambo.journalApp.Repositry.UserRepositry;
import com.sambo.journalApp.entity.User;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepositry userRepositry;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public void saveNewUser(User user) {

        // Encode password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Assign role
        user.setRole(Arrays.asList("USER"));

        // Save user
        userRepositry.save(user);
    }


    public List<User> getAll() {
        return userRepositry.findAll();
    }


    public Optional<User> findById(ObjectId id) {
        return userRepositry.findById(id);
    }


    public void deleteById(ObjectId id) {
        userRepositry.deleteById(id);
    }


    public User findByUsername(String username) {
        return userRepositry.findByUsername(username);
    }
}