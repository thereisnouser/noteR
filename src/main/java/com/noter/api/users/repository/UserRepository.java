package com.noter.api.users.repository;

import com.noter.api.users.model.User;
import java.util.List;

public interface UserRepository {
    
    public List<User> getAllUsers();
    
    public User getUser(final int id);
    
    public void createUser(final User user);
    
    public void deleteUser(final int id);
}
