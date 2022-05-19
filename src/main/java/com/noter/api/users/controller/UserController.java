package com.noter.api.users.controller;

import com.noter.api.users.model.User;
import com.noter.api.users.service.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    
    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }
    
    @GetMapping("/{id}")
    public User getUser(@PathVariable final int id) {
        return userService.getUser(id);
    }
    
    @PostMapping
    public ResponseEntity createUser(@RequestBody final User user) {
        return userService.createUser(user);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity deleteUser(@PathVariable("id") final int id) {
        return userService.deleteUser(id);
    }
}
