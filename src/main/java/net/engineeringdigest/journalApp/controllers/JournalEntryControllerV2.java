package net.engineeringdigest.journalApp.controllers;


//special type of component

import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.repository.JournalEntryRepository;
import net.engineeringdigest.journalApp.entity.UserEntry;
import net.engineeringdigest.journalApp.service.JournalEntryService;
import net.engineeringdigest.journalApp.service.UserEntryService;
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
    @Autowired
    private UserEntryService userEntryService;

//    public Map<Long, JournalEntry> journalEntries= new HashMap<>();

    @GetMapping("{username}")
    private ResponseEntity<?> getAllJournalEntriesOfUser(@PathVariable String username){
        UserEntry user= userEntryService.findByUsername(username);
        if(user==null){
            return new ResponseEntity<>("User Not Found", HttpStatus.UNAUTHORIZED);
        }
        List<JournalEntry> entries = user.getJournalEntries();
        if(!entries.isEmpty()){
            return new ResponseEntity<>(entries, HttpStatus.OK);
        }
        return new ResponseEntity<>("entries not found",HttpStatus.NOT_FOUND);

    }

    @PostMapping("/{userName}")
    private ResponseEntity<?> createEntry(@RequestBody JournalEntry myEntry, @PathVariable String userName){
        try {

//            myEntry.setDate(LocalDateTime.now());
            //added in service

            journalEntryService.saveEntry(myEntry, userName);
            //everything relating to username is in service



            return new ResponseEntity<>(myEntry, HttpStatus.CREATED);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity<>(e ,HttpStatus.NOT_MODIFIED);
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


    @DeleteMapping("{username}/id/{id}")
    public ResponseEntity<?> deleteEntryById(@PathVariable ObjectId id, @PathVariable String username){
        System.out.println("Deleted" + id);
        journalEntryService.deleteById(id, username);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/id/{userName}/{id}")
    public ResponseEntity<JournalEntry> updateById(@PathVariable ObjectId id,
                                                   @RequestBody JournalEntry myUpdate,
                                                   @PathVariable String userName
    ){
        JournalEntry oldEntry= journalEntryService.findById(id).orElse(null);
        if(oldEntry!=null) {
            oldEntry.setContent(myUpdate.getContent() != null && !myUpdate.getContent().equals("") ? myUpdate.getContent() : oldEntry.getContent());
            oldEntry.setTitle(myUpdate.getTitle() != null && !myUpdate.getTitle().equals("") ? myUpdate.getTitle() : oldEntry.getTitle());

            journalEntryService.updateEntry(oldEntry);
            return new ResponseEntity<>(oldEntry, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND) ;
    }




}
