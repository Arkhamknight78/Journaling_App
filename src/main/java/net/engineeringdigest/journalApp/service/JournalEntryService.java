package net.engineeringdigest.journalApp.service;

import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.UserEntry;
import net.engineeringdigest.journalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class JournalEntryService  {
    @Autowired
    private JournalEntryRepository journalEntryRepository;
    @Autowired
    private UserEntryService userEntryService;

    @Transactional
    public void saveEntry(JournalEntry journalEntry, String userName){
       try {
           UserEntry user= userEntryService.findByUsername(userName);
           if(user == null) {
               new ResponseEntity<>("UserNot Found", HttpStatus.NOT_FOUND);
               return;
           }

           journalEntry.setDate(LocalDateTime.now(ZoneId.of("Asia/Kolkata")));
           journalEntry.setCreatedBy(userName);
           JournalEntry savedEntry= journalEntryRepository.save(journalEntry);
           user.getJournalEntries().add(savedEntry);
           userEntryService.saveNewUser(user);
//           user.setUserName(null);
           new ResponseEntity<>(HttpStatus.OK);
//           return;

       }catch(Exception e)
        {


            System.out.println(e);
            throw new RuntimeException("An error occured while performing: ", e);
        }
//        new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    }
    public void updateEntry(JournalEntry journalEntry){
           journalEntryRepository.save(journalEntry);
    }

    public List<JournalEntry> getAll(){
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> findById(ObjectId Id){
        return journalEntryRepository.findById(Id);
    }

    public void deleteById(ObjectId Id, String userName){
        UserEntry user= userEntryService.findByUsername(userName);
        user.getJournalEntries().removeIf(x -> x.getId().equals(Id));
        userEntryService.saveNewUser(user);
        journalEntryRepository.deleteById(Id);

    }
}

//controller calls ---> service, service calls ---> Repository
