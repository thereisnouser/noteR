package com.noter.api.users.service;

import com.noter.api.users.model.User;
import com.noter.api.users.repository.UserRepository;
import com.noter.util.Utils;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    
    @Override
    public List<User> getAllUsers() {
        return userRepository.getAllUsers();
    }
    
    @Override
    public User getUser(final int id) {
        return userRepository.getUser(id);
    }
    
    @Override
    public ResponseEntity createUser(final User user) {
        if (Utils.isNullOrEmpty(user.getName())) {
            return ResponseEntity.badRequest().body("Ser correct value for the 'name'");
        }
        if (Utils.isNullOrEmpty(user.getPassword())) {
            return ResponseEntity.badRequest().body("Ser correct value for the 'password'");
        }
        
        userRepository.createUser(user);
        
        return ResponseEntity.ok("User is created successfully!");
    }
    
    @Override
    public ResponseEntity deleteUser(final int id) {
        if (this.getUser(id) == null) {
            return ResponseEntity.badRequest().body("Set correct value for the 'id'");
        }
        
        userRepository.deleteUser(id);
        
        return ResponseEntity.ok("User is removed successfully!");
    }
}
