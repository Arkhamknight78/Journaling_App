 package net.engineeringdigest.journalApp.controllers;


//special type of component

import net.engineeringdigest.journalApp.entity.UserEntry;
import net.engineeringdigest.journalApp.service.UserEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//special type of component
//handles http requests

@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserEntryService userEntryService;
    //dependency injection

    @GetMapping
    public ResponseEntity<List<UserEntry>> getAllUsers(){
        return new ResponseEntity<>(userEntryService.getAll(), HttpStatus.OK);
    }

    @PostMapping
    public void createUser(@RequestBody UserEntry user){

        userEntryService.saveNewUser(user);
    }

    @PutMapping("/{userName}")
    public ResponseEntity<?> updateUser(@RequestBody UserEntry username, @PathVariable String userName){
        UserEntry userInDB= userEntryService.findByUsername(userName);

        if(userInDB!= null){
            userInDB.setUserName(username.getUserName());
            userInDB.setPassword(username.getPassword());
            userEntryService.saveNewUser(userInDB);
            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }




}
