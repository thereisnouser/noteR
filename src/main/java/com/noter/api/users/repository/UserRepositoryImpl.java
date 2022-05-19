package com.noter.api.users.repository;

import com.noter.api.users.model.User;
import java.util.List;
import javax.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryImpl implements UserRepository {
    
    @Autowired
    private EntityManager entityManager;
    
    @Override
    public List<User> getAllUsers() {
        return entityManager.createQuery("FROM User").getResultList();
    }
    
    @Override
    public User getUser(final int id) {
        return entityManager.find(User.class, id);
    }
    
    @Override
    public void createUser(final User user) {
        entityManager.persist(user);
    }
    
    @Override
    public void deleteUser(final int id) {
        final User user = this.getUser(id);
        entityManager.remove(user);
    }
}
