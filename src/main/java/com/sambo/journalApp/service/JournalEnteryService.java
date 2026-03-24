package com.sambo.journalApp.service;

import com.sambo.journalApp.Repositry.JournalEnteryRepositry;
import com.sambo.journalApp.entity.JournalEntery;
import com.sambo.journalApp.entity.User;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class JournalEnteryService {

    @Autowired
    private JournalEnteryRepositry journalEnteryRepositry;

    @Autowired
    private UserService userService;


    /**
     * Create a new journal entry and attach it to the user
     */
    @Transactional
    public void save(JournalEntery journalEntery, String username) {

        User user = userService.findByUsername(username);
        if (user == null) {
            throw new RuntimeException("User not found");
        }

        journalEntery.setDate(LocalDateTime.now());

        JournalEntery savedEntry = journalEnteryRepositry.save(journalEntery);

        if (user.getJournalEntries() == null) {
            user.setJournalEntries(new ArrayList<>());
        }

        user.getJournalEntries().add(savedEntry);

        userService.saveNewUser(user);
    }


    /**
     * Update an existing journal entry
     */
    public void save(JournalEntery journalEntery) {
        journalEnteryRepositry.save(journalEntery);
    }


    public List<JournalEntery> getAll() {
        return journalEnteryRepositry.findAll();
    }


    public Optional<JournalEntery> findById(ObjectId id) {
        return journalEnteryRepositry.findById(id);
    }


    /**
     * Delete a journal entry and remove it from the user
     */
    public void deleteById(ObjectId id, String username) {

        User user = userService.findByUsername(username);
        if (user == null) {
            throw new RuntimeException("User not found");
        }

        if (user.getJournalEntries() != null) {
            user.getJournalEntries().removeIf(entry -> entry.getId().equals(id));
        }

        userService.saveNewUser(user);

        journalEnteryRepositry.deleteById(id);
    }


    /**
     * Get all journal entries of a specific user
     */
    public List<JournalEntery> findByUsername(String username) {

        User user = userService.findByUsername(username);

        if (user == null || user.getJournalEntries() == null) {
            return new ArrayList<>();
        }

        return user.getJournalEntries();
    }
}