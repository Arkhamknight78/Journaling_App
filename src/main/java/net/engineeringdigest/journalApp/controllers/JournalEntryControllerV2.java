package net.engineeringdigest.journalApp.controllers;


//special type of component

import net.engineeringdigest.journalApp.entity.JournalEntry;
//import net.engineeringdigest.journalApp.repository.JournalEntryRepository;
import net.engineeringdigest.journalApp.service.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/journal")
public class JournalEntryControllerV2 {

    @Autowired
    private JournalEntryService journalEntryService;
    //dependency injection

//    public Map<Long, JournalEntry> journalEntries= new HashMap<>();

    @GetMapping("/getAll")
    private ResponseEntity<?> getJournalEntries(){

        List<JournalEntry> entries = journalEntryService.getAll();
        if(!entries.isEmpty()){
            return new ResponseEntity<>(entries, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @PostMapping
    private ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry){
        try {
            myEntry.setDate(LocalDateTime.now());
            journalEntryService.saveEntry(myEntry);
            return new ResponseEntity<>(myEntry, HttpStatus.CREATED);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<JournalEntry> getJournalById(@PathVariable ObjectId id){
        Optional<JournalEntry> Entry= journalEntryService.findById(id);
        if(Entry.isPresent()){
            return new ResponseEntity<>(Entry.get(), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }


    @DeleteMapping("/id/{id}")
    public ResponseEntity<?> deleteEntryById(@PathVariable ObjectId id){
        System.out.println("Deleted" + id);
        journalEntryService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/id/{id}")
    public ResponseEntity<JournalEntry> updateById(@PathVariable ObjectId id, @RequestBody JournalEntry myUpdate){
        JournalEntry oldEntry= journalEntryService.findById(id).orElse(null);
        if(oldEntry!=null) {
            oldEntry.setContent(myUpdate.getContent() != null && !myUpdate.getContent().equals("") ? myUpdate.getContent() : oldEntry.getContent());
            oldEntry.setTitle(myUpdate.getTitle() != null && !myUpdate.getTitle().equals("") ? myUpdate.getTitle() : oldEntry.getTitle());

            journalEntryService.saveEntry(oldEntry);
            return new ResponseEntity<>(oldEntry, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND) ;
    }




}
