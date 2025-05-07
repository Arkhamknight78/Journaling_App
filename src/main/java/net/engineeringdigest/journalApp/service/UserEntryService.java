package net.engineeringdigest.journalApp.service;

import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalApp.entity.UserEntry;
import net.engineeringdigest.journalApp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
// Slf4j is a logging library that provides a simple facade for various logging frameworks
// like log4j, slf4j, etc. It allows you to use different logging frameworks without changing your code.

@Service
// This annotation indicates that this class is a service component in the Spring framework.
// It is used to mark the class as a service layer component, which typically contains business logic.
// The service layer is responsible for handling the application's business logic and interacting with the data access layer.
// It is a specialization of the @Component annotation, which means that Spring will automatically detect and register this class as a bean in the application context.

public class UserEntryService {

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @Autowired
    private UserRepository userRepository ;

//    public void saveEntry(UserEntry userEntry){
//       try {
//           userRepository.save(userEntry);
//       }catch(Exception e)
//        {
//            log.error("error: ", e);
//        }
//    }
    public void saveNewUser(UserEntry userEntry){
        userEntry.setPassword(passwordEncoder.encode(userEntry.getPassword()));
        userEntry.setRoles(Arrays.asList("USER"));
        userRepository.save(userEntry);
    }

    public List<UserEntry> getAll(){
        return userRepository.findAll();
    }

    public Optional<UserEntry> findById(ObjectId Id){
        return userRepository.findById(Id);
    }

    public void deleteById(ObjectId Id){
        userRepository.deleteById(Id);

    }

    public UserEntry findByUsername(String username){
        return userRepository.findByUserName(username);
    }
}

//controller calls ---> service, service calls ---> Repository
