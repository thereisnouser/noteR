package com.noter.api.users.service;

import com.noter.api.users.model.User;
import java.util.List;
import org.springframework.http.ResponseEntity;

public interface UserService {
    
    public List<User> getAllUsers();
    
    public User getUser(final int id);
    
    public ResponseEntity createUser(final User user);
    
    public ResponseEntity deleteUser(final int id);
}
