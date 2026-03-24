package com.sambo.journalApp.Repositry;

import com.sambo.journalApp.entity.JournalEntery;
import com.sambo.journalApp.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepositry extends MongoRepository<User, ObjectId> {
    User findByUsername(String username);
    void deleteByUsername(String username);

}
