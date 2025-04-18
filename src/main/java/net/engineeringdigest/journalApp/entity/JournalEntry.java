package net.engineeringdigest.journalApp.entity;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Document(collection = "journal_entries")
//@Getter
//@Setter

@Data
@NoArgsConstructor
public class JournalEntry {
    @Id //
    private ObjectId id;
    @NonNull
    private String title;

    private LocalDateTime date;

    private String content;

    private String createdBy;




}
