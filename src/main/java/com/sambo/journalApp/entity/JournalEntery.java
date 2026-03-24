package com.sambo.journalApp.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.cglib.core.Local;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Document(collection="journal_entries")
@NoArgsConstructor
public class JournalEntery {
    @Id
    private ObjectId id;

    @NonNull
    private String title;

    private String content;
    private LocalDateTime date;


}
