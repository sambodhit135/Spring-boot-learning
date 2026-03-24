package com.sambo.journalApp.Repositry;

import com.sambo.journalApp.entity.JournalEntery;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface JournalEnteryRepositry extends MongoRepository<JournalEntery, ObjectId> {
}
