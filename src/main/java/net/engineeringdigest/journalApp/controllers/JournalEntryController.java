//package net.engineeringdigest.journalApp.controllers;
//
//
////special type of component
//
//import net.engineeringdigest.journalApp.entity.JournalEntry;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@RestController
//@RequestMapping("/journal")
//public class JournalEntryController {
//
//    public Map<Long, JournalEntry> journalEntries= new HashMap<>();
//
//    @GetMapping("/getAll")
//    private List<JournalEntry> getJournalEntries(){
//        return new ArrayList<JournalEntry>(journalEntries.values());
//    }
//
//    @PostMapping
//    private boolean createEntry(@RequestBody JournalEntry myEntry){
//        try {
//
//            journalEntries.put(myEntry.getId(), myEntry);
//            return true;
//        }
//        catch(Exception e){
//            System.out.println(e.getMessage());
//            return false;
//        }
//    }
//
//    @GetMapping("/id/{id}")
//    public JournalEntry getJournalById(@PathVariable long id){
//        return journalEntries.get(id);
//    }
//
//    @DeleteMapping("/id/{id}")
//    public JournalEntry deleteEntryById(@PathVariable long id){
//        System.out.println("Deleted"+ id);
//        return journalEntries.remove(id);
//    }
//
//    @PutMapping("/id/{id}")
//    public List<JournalEntry> updateById(@PathVariable long id, @RequestBody JournalEntry myUpdate){
//        journalEntries.put(id, myUpdate);
//        return new ArrayList<JournalEntry>( journalEntries.values());
//    }
//
//
//
//
//}
