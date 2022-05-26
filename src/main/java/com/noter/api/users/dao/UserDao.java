package com.noter.api.users.dao;

import com.noter.api.users.model.User;
import java.util.Optional;

public interface UserDao {

    Optional<User> getUserByName(String name);
}
