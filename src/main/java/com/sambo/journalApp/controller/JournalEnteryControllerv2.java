package com.sambo.journalApp.controller;

import com.sambo.journalApp.entity.JournalEntery;
import com.sambo.journalApp.service.JournalEnteryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/journal")
public class JournalEnteryControllerv2 {

    @Autowired
    private JournalEnteryService journalEnteryService;



    @GetMapping
    public ResponseEntity<?> getAllJournalEntriesByUser() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        List<JournalEntery> entries = journalEnteryService.findByUsername(username);

        if (!entries.isEmpty()) {
            return new ResponseEntity<>(entries, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }



    @PostMapping
    public ResponseEntity<?> createEntry(@RequestBody JournalEntery entry) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        journalEnteryService.save(entry, username);

        return new ResponseEntity<>(entry, HttpStatus.CREATED);
    }



    @GetMapping("/id/{id}")
    public ResponseEntity<?> getEntryById(@PathVariable ObjectId id) {

        Optional<JournalEntery> entry = journalEnteryService.findById(id);

        return entry
                .map(journalEntery -> new ResponseEntity<>(journalEntery, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }



    @DeleteMapping("/id/{id}")
    public ResponseEntity<?> deleteEntry(@PathVariable ObjectId id) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        journalEnteryService.deleteById(id, username);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }



    @PutMapping("/id/{id}")
    public ResponseEntity<?> updateEntry(
            @PathVariable ObjectId id,
            @RequestBody JournalEntery newEntry) {

        Optional<JournalEntery> old = journalEnteryService.findById(id);

        if (old.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        JournalEntery entry = old.get();

        if (newEntry.getTitle() != null && !newEntry.getTitle().isBlank()) {
            entry.setTitle(newEntry.getTitle());
        }

        if (newEntry.getContent() != null && !newEntry.getContent().isBlank()) {
            entry.setContent(newEntry.getContent());
        }

        journalEnteryService.save(entry);

        return new ResponseEntity<>(entry, HttpStatus.OK);
    }
}