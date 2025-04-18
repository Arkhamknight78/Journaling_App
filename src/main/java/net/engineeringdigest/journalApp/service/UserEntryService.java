package net.engineeringdigest.journalApp.service;

import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalApp.entity.UserEntry;
import net.engineeringdigest.journalApp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UserEntryService {
    @Autowired
    private UserRepository userRepository ;

    public void saveEntry(UserEntry userEntry){
       try {
           userRepository.save(userEntry);
       }catch(Exception e)
        {
            log.error("error: ", e);
        }
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
